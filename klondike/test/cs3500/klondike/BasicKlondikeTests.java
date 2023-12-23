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
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;
import cs3500.klondike.view.KlondikeTextualView;

/**
 * Represents tests for the model of the Klondike game.
 */

public class BasicKlondikeTests {

  KlondikeModel model;
  List<Card> deck1;
  List<Card> deck2;
  List<Card> deck3;
  List<Card> deck4;
  List<Card> invalidDeck1;
  List<Card> invalidDeck2;
  Card card1;
  Card card2;

  private void init() {
    this.model = new BasicKlondike();
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
    this.invalidDeck1 = new ArrayList<>();
    this.invalidDeck1.add(new KlondikeCard(CardValue.ACE, Suit.CLUBS));
    this.invalidDeck1.add(new KlondikeCard(CardValue.TWO, Suit.CLUBS));
    this.invalidDeck1.add(new KlondikeCard(CardValue.ACE, Suit.HEARTS));
    this.invalidDeck1.add(new KlondikeCard(CardValue.TWO, Suit.HEARTS));
    this.invalidDeck1.add(new KlondikeCard(CardValue.THREE, Suit.HEARTS));
    this.invalidDeck2 = new ArrayList<>();
    this.invalidDeck2.add(new KlondikeCard(CardValue.ACE, Suit.CLUBS));
    this.invalidDeck2.add(new KlondikeCard(CardValue.TWO, Suit.CLUBS));
    this.invalidDeck2.add(new KlondikeCard(CardValue.ACE, Suit.HEARTS));
    this.invalidDeck2.add(null);
    this.card1 = new KlondikeCard(CardValue.ACE, Suit.CLUBS);
    this.card2 = new KlondikeCard(CardValue.TEN, Suit.HEARTS);
  }

  @Test
  public void testGetDeck() {
    this.init();
    Assert.assertEquals(52, this.deck1.size());
    Assert.assertEquals(this.deck1.get(4).toString(), "2♣");
  }

  @Test
  public void testStartGame() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertEquals(52, this.model.getDeck().size());
    Assert.assertEquals(7, this.model.getNumRows());
    Assert.assertEquals(1, this.model.getNumDraw());
    Assert.assertEquals(4, this.model.getNumFoundations());
  }

  @Test
  public void testStartGameExceptions() {
    this.init();
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.invalidDeck1, false, 4, 1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.invalidDeck2, false, 4, 1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(null, false, 0, 1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.deck1, false, 0, 1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.deck1, false, -2, 1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.deck1, false, 4, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.deck1, false, 4, -1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.startGame(this.deck1, false, 12, 1));
  }

  @Test
  public void testMovePile() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 3);
    this.model.movePile(4, 1, 6);
    Assert.assertEquals(4, this.model.getPileHeight(4));
    Assert.assertEquals(8, this.model.getPileHeight(6));
    this.model.movePile(4, 1, 6);
    this.model.movePile(2, 1, 6);
    this.model.movePile(2, 1, 6);
    Assert.assertEquals("A♡", this.model.getCardAt(2, 0).toString());
    Assert.assertEquals("6♡", this.model.getCardAt(6, 7).toString());
  }

  @Test
  public void testMovePileExceptions() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.movePile(-1, 1, 6));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.movePile(4, 1, -1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.movePile(4, 0, 7));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.movePile(0, 1, 1));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.movePile(4, 2, 6));
    // Now, try to move a non-king to an empty pile.
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.movePile(1, 1, 0));
  }

  @Test
  public void testMoveDraw() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 1);
    Assert.assertEquals(1, this.model.getPileHeight(0));
    Assert.assertEquals("2♣", this.model.getCardAt(0, 0).toString());
    this.model.moveDraw(0);
    Assert.assertEquals(2, this.model.getPileHeight(0));
    Assert.assertEquals("A♢", this.model.getCardAt(0, 1).toString());
  }

  @Test
  public void testMoveDrawExceptions() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 3);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveDraw(-1));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveDraw(3));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveDraw(0));
    // Now, test moveDraw() to an empty pile.
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveDraw(0));
  }

  @Test
  public void testMoveToFoundation() {
    this.init();
    this.model.startGame(this.deck1, false, 3, 1);
    Assert.assertEquals(1, this.model.getPileHeight(0));
    this.model.moveToFoundation(0, 0);
    this.model.moveToFoundation(1, 1);
    Assert.assertEquals("A♣", this.model.getCardAt(0).toString());
    Assert.assertEquals("A♠", this.model.getCardAt(1).toString());
  }

  @Test
  public void testMoveToFoundationExceptions() {
    this.init();
    this.model.startGame(this.deck1, false, 3, 1);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveToFoundation(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveToFoundation(0, -1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveToFoundation(3, 0));
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveToFoundation(0, 0));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveToFoundation(1, 0));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveToFoundation(2, 2));
  }

  @Test
  public void testMoveDrawToFoundation() {
    this.init();
    this.model.startGame(this.deck3, false, 3, 1);
    this.model.moveDrawToFoundation(0);
    this.model.moveDrawToFoundation(1);
    Assert.assertEquals("A♢", this.model.getCardAt(0).toString());
    Assert.assertEquals("A♡", this.model.getCardAt(1).toString());
  }

  @Test
  public void testMoveDrawToFoundationExceptions() {
    this.init();
    this.model.startGame(this.deck1, false, 3, 1);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveDrawToFoundation(-1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.moveDrawToFoundation(55));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.moveDrawToFoundation(0));
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
    Assert.assertTrue(this.model.isGameOver());
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
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.isCardVisible(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.isCardVisible(7, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.isCardVisible(0, -1));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.isCardVisible(0, 1));
    Assert.assertTrue(this.model.isCardVisible(0, 0));
    Assert.assertTrue(this.model.isCardVisible(1, 1));
    Assert.assertTrue(this.model.isCardVisible(2, 2));
    Assert.assertTrue(this.model.isCardVisible(3, 3));
    Assert.assertTrue(this.model.isCardVisible(4, 4));
    Assert.assertTrue(this.model.isCardVisible(5, 5));
    Assert.assertTrue(this.model.isCardVisible(6, 6));
    Assert.assertFalse(this.model.isCardVisible(1, 0));
    Assert.assertFalse(this.model.isCardVisible(2, 1));
    Assert.assertFalse(this.model.isCardVisible(3, 2));
    Assert.assertFalse(this.model.isCardVisible(4, 3));
  }

  @Test
  public void testGetCardAtForCascadePiles() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getCardAt(0, 0));
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.getCardAt(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.getCardAt(2, 0));
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

  @Test
  public void testGetNumFoundations() {
    this.init();
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.getNumFoundations());
    this.model.startGame(this.deck1, false, 7, 1);
    Assert.assertEquals(4, this.model.getNumFoundations());
  }

  @Test
  public void testCardToString() {
    this.init();
    Assert.assertEquals("A♣", this.card1.toString());
    Assert.assertEquals("10♡", this.card2.toString());
  }

  @Test
  public void testCardEquals() {
    this.init();
    Assert.assertEquals(this.card1, this.card1);
    Assert.assertNotEquals(this.card1, this.card2);
    Assert.assertNotEquals(null, this.card1);
    Assert.assertNotEquals("A♣", this.card1);
    Assert.assertNotEquals(this.card1, new KlondikeCard(CardValue.ACE, Suit.SPADES));
    Assert.assertNotEquals(this.card1, new KlondikeCard(CardValue.TWO, Suit.CLUBS));
    Assert.assertNotEquals(this.card1, new KlondikeCard(CardValue.ACE, Suit.HEARTS));
    Assert.assertNotEquals(this.card1, new KlondikeCard(CardValue.ACE, Suit.DIAMONDS));
    Assert.assertEquals(this.card1, new KlondikeCard(CardValue.ACE, Suit.CLUBS));
  }

  @Test
  public void testCardGetSuit() {
    this.init();
    Assert.assertEquals("♣", this.card1.getSuit());
    Assert.assertEquals("♡", this.card2.getSuit());
  }

  @Test
  public void testCardGetValue() {
    this.init();
    Assert.assertEquals(1, this.card1.getValue());
    Assert.assertEquals(10, this.card2.getValue());
  }

  @Test
  public void testCardIsRed() {
    this.init();
    Assert.assertFalse(this.card1.isRed());
    Assert.assertTrue(this.card2.isRed());
  }

  @Test
  public void testKlondikeTextualViewToString() {
    this.init();
    this.model.startGame(this.deck1, false, 7, 4);
    Assert.assertEquals("Draw: 8♣, 8♢, 8♡, 8♠\n"
            + "Foundation: <none>, <none>, <none>, <none>\n"
            + " A♣  ?  ?  ?  ?  ?  ?\n"
            + "    2♠  ?  ?  ?  ?  ?\n"
            + "       4♢  ?  ?  ?  ?\n"
            + "          5♡  ?  ?  ?\n"
            + "             6♡  ?  ?\n"
            + "                7♢  ?\n"
            + "                   7♠",
            new KlondikeTextualView(this.model).toString());
  }
}