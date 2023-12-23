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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.MockKlondike;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

/**
 * Tests for the WhiteheadKlondike controller implementation.
 */

public class LimitedDrawControllerTests {

  KlondikeModel model;
  List<Card> deck;

  @Before
  public void init() {
    this.model = new LimitedDrawKlondike(1);
    this.deck = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠")
                    || card.toString().equals("A♣"))
            .collect(Collectors.toList());
    List<Card> twos = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♢")
                    || card.toString().equals("2♡")
                    || card.toString().equals("2♠")
                    || card.toString().equals("2♣"))
            .collect(Collectors.toList());
    this.deck.addAll(twos);
  }

  @Test
  public void testDiscardDrawUntilDrawPileEmptyInFullGame() {
    Readable input = new StringReader("dd dd dd dd q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    System.out.println(output);
    assertTrue(output.toString().contains("Draw: \n"));
  }

  @Test
  public void testMovePileValidInFullGame() {
    Readable input = new StringReader("mpp 2 1 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveDrawValidInFullGame() {
    this.deck = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠")
                    || card.toString().equals("A♣"))
            .collect(Collectors.toList());
    List<Card> twos = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♡")
                    || card.toString().equals("2♢")
                    || card.toString().equals("2♣")
                    || card.toString().equals("2♠"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.deck.add(2, twos.get(0));
    this.deck.add(twos.get(1));
    this.deck.add(twos.get(2));
    this.deck.add(twos.get(3));
    Readable input = new StringReader("md 2 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 2, 1);
    System.out.print(output);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveToFoundationValidInFullGame() {
    Readable input = new StringReader("mpf 1 1 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    System.out.print(output);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveDrawToFoundationValidInFullGame() {
    Readable input = new StringReader("mpf 1 1 mpf 2 2 mpf 2 3 mpf 3 3 mpf 3 1 mpf 3 4 mdf 4 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testDiscardDrawValidInFullGame() {
    Readable input = new StringReader("dd q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertFalse(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMovePileInvalidMoveInFullGame() {
    Readable input = new StringReader("mpf 1 1 mp 2 1 2 mpf 2 3 mpf 3 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveDrawInvalidMoveInFullGame() {
    Readable input = new StringReader("mpf 1 1 md 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveToFoundationInvalidMoveInFullGame() {
    Readable input = new StringReader("mpf 1 1 mpf 2 5 mpf 2 3 mpf 3 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testMoveDrawToFoundationInvalidMoveInFullGame() {
    Readable input = new StringReader("mpf 1 1 mdf 1 mpf 2 5 mpf 2 3 mpf 3 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testGameWinInFullGame() {
    Readable in = new StringReader("mpf 1 1 mpf 2 2 mdf 1 mpf 2 3 mpf 3 3 mpf 3 1 "
            + "mpf 3 4 mdf 4 mdf 2 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
    assertTrue(output.toString().contains("You win!"));
  }

  @Test
  public void testGameOverInFullGame() {
    this.deck = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠")
                    || card.toString().equals("A♣"))
            .collect(Collectors.toList());
    List<Card> twos = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♡")
                    || card.toString().equals("2♢")
                    || card.toString().equals("2♣")
                    || card.toString().equals("2♠"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.deck.add(2, twos.get(0));
    this.deck.add(twos.get(1));
    this.deck.add(twos.get(2));
    this.deck.add(twos.get(3));
    Readable input = new StringReader("mpf 1 1 mpf 2 2 mpf 3 2 mpf 2 3 mdf 3 mdf 1 dd q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Game over."));
  }

  @Test
  public void testHandleBogusInput() {
    Readable input = new StringReader("@(& mpp 2 *@% 1 3 q");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(this.model, this.deck, false, 3, 1);
    assertTrue(output.toString().contains("Invalid move."));
  }

  @Test
  public void testQuitAsFirstArgumentForCommand() {
    Readable input = new StringReader("mpp q 1 1 3");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck, true, 3, 2);
    assertTrue(output.toString().contains("Game quit!"));
  }

  @Test
  public void testQuitAsSecondArgumentForCommand() {
    Readable input = new StringReader("mpp 1 q 1 2");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck, true, 3, 2);
    assertTrue(output.toString().contains("Game quit!"));
  }

  @Test
  public void testQuitAsThirdArgumentForCommand() {
    Readable input = new StringReader("mpp 1 1 q 2");
    StringBuilder output = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(input, output);
    controller.playGame(new BasicKlondike(), this.deck, true, 3, 2);
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
        () -> controller.playGame(null, this.deck, false, 7, 1));
    assertThrows(IllegalArgumentException.class,
        () -> controller.playGame(this.model, null, false, 6, 1));
    assertThrows(IllegalStateException.class,
        () -> controller.playGame(new BasicKlondike(), this.deck, false, 99, 1));
  }

  @Test
  public void testBadReadable() {
    Readable badInput = new StringReader("mpp 1 1 2");
    StringBuilder output = new StringBuilder();
    KlondikeController badReadable = new KlondikeTextualController(badInput, output);
    assertThrows(IllegalStateException.class,
        () -> badReadable.playGame(new BasicKlondike(), this.deck, false, 3, 1));
    assertThrows(IllegalStateException.class,
        () -> badReadable.playGame(new MockKlondike(output), this.deck, false, 3, 1));
  }

  @Test
  public void testBadMockAppendable() {
    assertThrows(IllegalArgumentException.class, () -> new MockKlondike(null));
  }
}