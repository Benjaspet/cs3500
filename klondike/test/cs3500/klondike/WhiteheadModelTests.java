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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the WhiteheadKlondike implementation.
 */

public class WhiteheadModelTests {

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
  public void testValidMovePileWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 6, 3);
    this.model.movePile(2, 1, 3);
    assertEquals("3♠", this.model.getCardAt(3, 4).toString());
  }

  @Test
  public void testRightNumberWrongColorMovePileWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 6, 3);
    assertThrows(IllegalStateException.class, () -> this.model.movePile(1, 1, 2));
  }

  @Test
  public void testWrongNumberRightColorMovePileWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 6, 3);
    assertThrows(IllegalStateException.class, () -> this.model.movePile(4, 1, 3));
  }

  @Test
  public void testInvalidMovePileMultipleCardsNotAllSameSuitWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 3);
    this.model.movePile(2, 1, 3);
    assertThrows(IllegalStateException.class, () -> this.model.movePile(3, 2, 4));
  }

  @Test
  public void testValidMovePileWithMultipleCardsAllSameSuitWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 8, 3);
    this.model.movePile(4, 1, 5);
    this.model.movePile(5, 2, 6);
    assertEquals(9, this.model.getPileHeight(6));
  }

  @Test
  public void testValidMoveDrawWhitehead() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 3);
    this.model.discardDraw();
    this.model.moveDraw(1);
    assertEquals("A♡", this.model.getCardAt(1, 2).toString());
  }

  @Test
  public void testValidMoveDrawDifferentSuitsSameColorWhitehead() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 3);
    this.model.moveDraw(1);
    assertEquals("A♢", this.model.getCardAt(1, 2).toString());
  }

  @Test
  public void testInvalidMoveDrawRightNumberWrongColorWhitehead() {
    this.init();
    this.model.startGame(this.deck3, false, 2, 3);
    this.model.discardDraw();
    assertThrows(IllegalStateException.class, () -> this.model.moveDraw(1));
  }

  @Test
  public void testInvalidMoveDrawWrongNumberRightColorWhitehead() {
    this.init();
    this.model.startGame(this.deck3, false, 2, 3);
    assertThrows(IllegalStateException.class, () -> this.model.moveDraw(1));
  }

  @Test
  public void testValidMoveNonKingToEmptyCascadePileWhitehead() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 3);
    this.model.moveToFoundation(2, 0);
    this.model.moveToFoundation(2, 1);
    this.model.moveToFoundation(0, 1);
    this.model.movePile(2, 1, 0);
    assertEquals(1, this.model.getPileHeight(0));
  }

  @Test
  public void testValidMoveMultipleCardsToEmptyCascadePileWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 6, 3);
    this.model.movePile(2, 1, 3);
    this.model.moveToFoundation(0, 1);
    this.model.movePile(3, 2, 0);
    assertEquals(2, this.model.getPileHeight(0));
  }

  @Test
  public void testInvalidMoveMultipleCardsToEmptyCascadePileWhitehead() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 3);
    this.model.moveToFoundation(0, 0);
    this.model.movePile(2, 1, 3);
    assertThrows(IllegalStateException.class, () -> this.model.movePile(3, 2, 0));
  }

  @Test
  public void testDiscardDraw() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertEquals("8♣", this.model.getDrawCards().get(0).toString());
    this.model.discardDraw();
    Assert.assertEquals("8♢", this.model.getDrawCards().get(0).toString());
    this.model.discardDraw();
    Assert.assertEquals("8♡", this.model.getDrawCards().get(0).toString());
  }

  @Test
  public void testDiscardDrawExceptions() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 1);
    this.model.moveDrawToFoundation(0);
    this.model.moveDrawToFoundation(1);
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.discardDraw());
  }

  @Test
  public void testGetNumRows() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getNumRows());
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertEquals(7, this.model.getNumRows());
  }

  @Test
  public void testGetNumPiles() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getNumPiles());
    this.model.startGame(this.deck2, false, 12, 1);
    Assert.assertEquals(12, this.model.getNumPiles());
  }

  @Test
  public void testGetNumDraw() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getNumDraw());
    this.model.startGame(this.deck1, false, 7, 3);
    Assert.assertEquals(3, this.model.getNumDraw());
  }

  @Test
  public void testIsGameOver() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.isGameOver());
    this.model.startGame(this.deck4, false, 2, 1);
    Assert.assertFalse(this.model.isGameOver());
    this.model.moveDrawToFoundation(0);
    this.model.moveToFoundation(0, 1);
    this.model.moveToFoundation(1, 2);
    this.model.moveToFoundation(1, 3);
    this.model.moveDrawToFoundation(1);
    this.model.moveDrawToFoundation(3);
    this.model.moveDrawToFoundation(2);
    this.model.moveDrawToFoundation(0);
    assertTrue(this.model.isGameOver());
  }

  @Test
  public void testGetScore() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getScore());
    this.model.startGame(this.deck4, false, 2, 1);
    Assert.assertEquals(0, this.model.getScore());
    this.model.moveDrawToFoundation(0);
    this.model.moveToFoundation(0, 1);
    this.model.moveToFoundation(1, 2);
    this.model.moveToFoundation(1, 3);
    this.model.moveDrawToFoundation(1);
    this.model.moveDrawToFoundation(3);
    this.model.moveDrawToFoundation(2);
    this.model.moveDrawToFoundation(0);
    Assert.assertEquals(8, this.model.getScore());
  }

  @Test
  public void testGetPileHeight() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getPileHeight(0));
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.getPileHeight(-1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.getPileHeight(7));
    Assert.assertEquals(1, this.model.getPileHeight(0));
    Assert.assertEquals(2, this.model.getPileHeight(1));
    Assert.assertEquals(3, this.model.getPileHeight(2));
    Assert.assertEquals(4, this.model.getPileHeight(3));
    Assert.assertEquals(5, this.model.getPileHeight(4));
    Assert.assertEquals(6, this.model.getPileHeight(5));
    Assert.assertEquals(7, this.model.getPileHeight(6));
  }

  @Test
  public void testIsCardVisible() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.isCardVisible(0, 0));
    this.model.startGame(this.deck1, false, 7, 1);
    assertTrue(this.model.isCardVisible(0, 0));
  }

  @Test
  public void testGetCardAtForCascadePiles() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getCardAt(0, 0));
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.getCardAt(-1, 0));
    Assert.assertEquals("4♢", this.model.getCardAt(2, 2).toString());
  }

  @Test
  public void testGetCardAtForFoundationPiles() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getCardAt(0));
    this.model.startGame(this.deck1, false, 7, 1);
    this.model.moveToFoundation(0, 0);
    Assert.assertEquals("A♣", this.model.getCardAt(0).toString());
  }

  @Test
  public void testGetDrawCards() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getDrawCards());
    this.model.startGame(this.deck1, false, 7, 3);
    Assert.assertEquals("8♣", this.model.getDrawCards().get(0).toString());
    Assert.assertEquals("8♢", this.model.getDrawCards().get(1).toString());
    Assert.assertEquals("8♡", this.model.getDrawCards().get(2).toString());
  }
}
