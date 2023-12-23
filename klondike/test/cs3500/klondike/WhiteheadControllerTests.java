/*
 * Copyright © 2023 Ben Petrillo. All rights reserved.
 *
 * Project licensed under the MIT License: https://www.mit.edu/~amini/LICENSE.md
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * All portions of this software are available for public use, provided that
 * credit is given to the original author(s).
 */

package cs3500.klondike;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.MockKlondike;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the WhiteheadKlondike controller implementation.
 */

public class WhiteheadControllerTests {

  KlondikeModel model;
  List<Card> deck1;
  List<Card> deck2;
  List<Card> deck3;
  List<Card> deck4;

  @Before
  public void init() {
    this.model = new WhiteheadKlondike();
    this.deck1 = this.model.getDeck();
    this.deck2 = this.model.getDeck();
    this.deck2.addAll(this.model.getDeck());
    this.deck3 = new ArrayList<>();
    this.deck4 = new ArrayList<>();
    this.deck3.add(new KlondikeCard(CardValue.TWO, Suit.CLUBS));
    this.deck3.add(new KlondikeCard(CardValue.TWO, Suit.SPADES));
    this.deck3.add(new KlondikeCard(CardValue.TWO, Suit.DIAMONDS));
    this.deck3.add(new KlondikeCard(CardValue.TWO, Suit.HEARTS));
    this.deck3.add(new KlondikeCard(CardValue.ACE, Suit.CLUBS));
    this.deck3.add(new KlondikeCard(CardValue.ACE, Suit.SPADES));
    this.deck3.add(new KlondikeCard(CardValue.ACE, Suit.DIAMONDS));
    this.deck3.add(new KlondikeCard(CardValue.ACE, Suit.HEARTS));
    this.deck4.add(new KlondikeCard(CardValue.ACE, Suit.CLUBS));
    this.deck4.add(new KlondikeCard(CardValue.ACE, Suit.HEARTS));
    this.deck4.add(new KlondikeCard(CardValue.ACE, Suit.DIAMONDS));
    this.deck4.add(new KlondikeCard(CardValue.ACE, Suit.SPADES));
    this.deck4.add(new KlondikeCard(CardValue.TWO, Suit.CLUBS));
    this.deck4.add(new KlondikeCard(CardValue.TWO, Suit.HEARTS));
    this.deck4.add(new KlondikeCard(CardValue.TWO, Suit.DIAMONDS));
    this.deck4.add(new KlondikeCard(CardValue.TWO, Suit.SPADES));
  }

  @Test
  public void testValidMovePileWhiteheadController() {
    Readable input = new StringReader("mpp 3 1 4 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 6, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testRightNumberWrongColorMovePileWhiteheadController() {
    Readable input = new StringReader("mpp 2 1 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 6, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testWrongNumberRightColorMovePileWhiteheadController() {
    Readable input = new StringReader("mpp 5 1 4 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 6, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMovePileMultipleCardsNotAllSameSuitWhiteheadController() {
    Readable input = new StringReader("mpp 4 2 5 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 7, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMovePileWithMultipleCardsAllSameSuitWhiteheadController() {
    Readable input = new StringReader("mpp 5 1 6 mpp 6 2 7 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 8, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMoveDrawWhiteheadController() {
    Readable input = new StringReader("dd mdf 1 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck3, false, 3, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMoveDrawDifferentSuitsSameColorWhiteheadController() {
    Readable input = new StringReader("md 2 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck3, false, 3, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMoveDrawRightNumberWrongColorWhiteheadController() {
    Readable input = new StringReader("dd md 2 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck3, false, 2, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMoveDrawWrongNumberRightColorWhiteheadController() {
    Readable input = new StringReader("md 2 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck3, false, 2, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMoveNonKingToEmptyCascadePileWhiteheadController() {
    Readable input = new StringReader("mpf 3 1 mpf 3 2 mpf 1 2 mpp 3 1 1 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck3, false, 3, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMoveMultipleCardsToEmptyCascadePileWhiteheadController() {
    Readable input = new StringReader("mpp 3 1 4 mpf 1 2 mpp 4 2 1 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 6, 3);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMoveMultipleCardsToEmptyCascadePileWhiteheadController() {
    Readable input = new StringReader("mpf 1 1 mpp 3 1 4 mpp 4 2 1 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 7, 3);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testHandleBogusInput() {
    Readable input = new StringReader("@(& mpp 2 *@% 1 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck1, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testQuitAsFirstArgumentForCommand() {
    Readable input = new StringReader("mpp q 1 1 3");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck1, true, 3, 2);
    assertTrue(output.toString().contains("Game quit!"));
  }

  @Test
  public void testQuitAsSecondArgumentForCommand() {
    Readable input = new StringReader("mpp 1 q 1 2");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck1, true, 3, 2);
    assertTrue(output.toString().contains("Game quit!"));
  }

  @Test
  public void testQuitAsThirdArgumentForCommand() {
    Readable input = new StringReader("mpp 1 1 q 2");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck1, true, 3, 2);
    assertTrue(output.toString().contains("Game quit!"));
  }

  // EXCEPTION TESTS

  @Test
  public void testNullContructorParametersException() {
    Readable input = new StringReader("q");
    StringBuilder output = new StringBuilder();
    assertThrows(IllegalArgumentException.class,
        () -> new KlondikeTextualController(null, output));
    assertThrows(IllegalArgumentException.class,
        () -> new KlondikeTextualController(input, null));
  }

  @Test
  public void testPlayGameExceptions() {
    Readable input = new StringReader("q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    assertThrows(IllegalArgumentException.class,
        () -> controller.playGame(null, this.deck1, false, 7, 1));
    assertThrows(IllegalArgumentException.class,
        () -> controller.playGame(this.model, null, false, 6, 1));
    assertThrows(IllegalStateException.class,
        () -> controller.playGame(new BasicKlondike(), this.deck1, false, 99, 1));
  }

  @Test
  public void testBadReadable() {
    Readable badInput = new StringReader("mpp 1 1 2");
    StringBuilder output = new StringBuilder();
    KlondikeController badReadable = new KlondikeTextualController(badInput, output);
    assertThrows(IllegalStateException.class,
        () -> badReadable.playGame(new BasicKlondike(), this.deck1, false, 3, 1));
    assertThrows(IllegalStateException.class,
        () -> badReadable.playGame(new MockKlondike(output), this.deck1, false, 3, 1));
  }

  @Test
  public void testBadMockAppendable() {
    assertThrows(IllegalArgumentException.class, () -> new MockKlondike(null));
  }
}