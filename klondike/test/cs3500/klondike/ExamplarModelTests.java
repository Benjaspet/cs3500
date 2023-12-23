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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * Represents Examplar tests for the KlondikeModel.
 */

public class ExamplarModelTests {

  KlondikeModel model = new BasicKlondike();
  List<Card> deck = Arrays.asList(new Card[52]);
  List<Card> deck2 = Arrays.asList(new Card[8]);

  private void initAces() {
    this.deck = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♡")
                    || card.toString().equals("A♢")
                    || card.toString().equals("A♣")
                    || card.toString().equals("A♠"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.deck.sort(Comparator.comparing(Card::toString));
  }

  private void initTwos() {
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
  }

  private void initTwosThenAces() {
    this.deck2 = this.model.getDeck()
            .stream().filter(card -> card.toString().startsWith("2"))
            .collect(Collectors.toList());
    List<Card> aces = this.model.getDeck()
            .stream().filter(card -> card.toString().startsWith("A"))
            .collect(Collectors.toList());
    this.deck2.addAll(aces);
  }

  @Test
  public void testScoreAfterValidMoveDrawToFoundation() {
    this.initAces();
    this.model.startGame(this.deck, false, 1, 1);
    this.model.moveDrawToFoundation(0);
    Assert.assertEquals(1, this.model.getScore());
  }

  @Test
  public void testDeckSizeAfterValidMoveDrawToFoundation() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 2);
    int amt = this.model.getDrawCards().size();
    this.model.moveDrawToFoundation(0);
    Assert.assertEquals(amt, this.model.getDrawCards().size());
  }

  @Test
  public void testDeckSizeAfterValidMoveDraw() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    int amt = this.model.getDrawCards().size();
    this.model.moveToFoundation(0, 0);
    this.model.moveDrawToFoundation(1);
    Assert.assertEquals(amt, this.model.getDrawCards().size());
  }

  @Test
  public void testMutateGetDrawCardsOmgIGotThisOne() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    try {
      this.model.getDrawCards().remove(0);
      Assert.assertEquals(3, this.model.getDrawCards().size());
    } catch (Exception e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testMoveNonAceToEmptyFoundation() {
    this.initTwosThenAces();
    this.model.startGame(this.deck2, false, 1, 1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveDrawToFoundation(0));
  }

  @Test
  public void testIsGameOver() {
    List<Card> aceOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♡"))
            .collect(Collectors.toList());
    List<Card> threeOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♠"))
            .collect(Collectors.toList());
    List<Card> twoOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♡"))
            .collect(Collectors.toList());
    List<Card> aceOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♠"))
            .collect(Collectors.toList());
    List<Card> twoOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♠"))
            .collect(Collectors.toList());
    List<Card> threeOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♡"))
            .collect(Collectors.toList());
    aceOfHearts.add(threeOfSpades.get(0));
    aceOfHearts.add(twoOfHearts.get(0));
    aceOfHearts.add(aceOfSpades.get(0));
    aceOfHearts.add(twoOfSpades.get(0));
    aceOfHearts.add(threeOfHearts.get(0));
    this.model.startGame(aceOfHearts, false, 3, 1);
    model.moveToFoundation(0, 0);
    model.moveToFoundation(1,1);
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testPileHight() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    this.model.moveToFoundation(0, 1);
    Assert.assertEquals(0, this.model.getPileHeight(0));
  }

  @Test
  public void testMoveEmptyPileToFoundation() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 1, 1);
    this.model.moveToFoundation(0, 1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveToFoundation(0, 0));
  }

  @Test
  public void testMoveMoreCardThanInPileToInvalidPlace() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    Assert.assertThrows(IllegalArgumentException.class, () -> this.model.movePile(0, 2, 1));
  }

  @Test
  public void testMoveAceToFoundationWithAceInItAlready() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    this.model.moveDrawToFoundation(0);
    this.model.moveDrawToFoundation(1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveDrawToFoundation(1));
  }

  @Test
  public void testMoveNonKingIntoEmptyCascadePile() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.movePile(1, 1, 0));
  }

  @Test
  public void testDrawCardsPileSizeAfterDiscardDraw() {
    KlondikeModel model = new BasicKlondike();
    model.startGame(model.getDeck(), false, 7, 3);
    int amt = model.getDrawCards().size();
    for (int i = 0; i < 150; i++) {
      model.discardDraw();
    }
    Assert.assertEquals(amt, model.getDrawCards().size());
  }

  @Test
  public void testInvalidPileMove() {
    this.model.startGame(model.getDeck(), false, 3, 3);
    Assert.assertThrows(IllegalStateException.class, () -> model.movePile(2, 1, 0));
  }

  @Test
  public void testInvalidRestrictedPileMove() {
    this.initAces();
    this.initTwos();
    this.model.startGame(this.deck, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.movePile(0 , 1, 1));
  }

  @Test
  public void testInvalidMoveDraw() {
    this.initTwosThenAces();
    this.model.startGame(this.deck2, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveDraw(0));
  }

  @Test
  public void testMoveCardToFoundationRightSuitWrongNumber() {
    List<Card> aceOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♠"))
            .collect(Collectors.toList());
    List<Card> aceOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♡"))
            .collect(Collectors.toList());
    List<Card> aceOfClubs = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♣"))
            .collect(Collectors.toList());
    List<Card> aceOfDiamonds = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢"))
            .collect(Collectors.toList());
    List<Card> twos = this.model.getDeck()
            .stream().filter(card -> card.toString().startsWith("2"))
            .collect(Collectors.toList());
    List<Card> threeOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♡"))
            .collect(Collectors.toList());
    List<Card> threeOfDiamonds = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♢"))
            .collect(Collectors.toList());
    List<Card> threeOfClubs = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♣"))
            .collect(Collectors.toList());
    List<Card> threeOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("3♠"))
            .collect(Collectors.toList());
    aceOfHearts.add(aceOfSpades.get(0));
    aceOfHearts.add(threeOfHearts.get(0));
    aceOfHearts.add(aceOfClubs.get(0));
    aceOfHearts.add(aceOfDiamonds.get(0));
    aceOfHearts.addAll(twos);
    aceOfHearts.add(threeOfDiamonds.get(0));
    aceOfHearts.add(threeOfClubs.get(0));
    aceOfHearts.add(threeOfSpades.get(0));
    this.model.startGame(aceOfHearts, false, 2, 1);
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveToFoundation(1, 0));
  }

  @Test
  public void testMoveCardToFoundationWrongSuitRightNumber() {
    List<Card> aceOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♠"))
            .collect(Collectors.toList());
    List<Card> aceOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♡"))
            .collect(Collectors.toList());
    List<Card> aceOfClubs = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♣"))
            .collect(Collectors.toList());
    List<Card> aceOfDiamonds = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢"))
            .collect(Collectors.toList());
    List<Card> threes = this.model.getDeck()
            .stream().filter(card -> card.toString().startsWith("3"))
            .collect(Collectors.toList());
    List<Card> twoOfHearts = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♡"))
            .collect(Collectors.toList());
    List<Card> twoOfDiamonds = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♢"))
            .collect(Collectors.toList());
    List<Card> twoOfClubs = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♣"))
            .collect(Collectors.toList());
    List<Card> twoOfSpades = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♠"))
            .collect(Collectors.toList());
    aceOfHearts.add(aceOfSpades.get(0));
    aceOfHearts.add(twoOfDiamonds.get(0));
    aceOfHearts.add(aceOfClubs.get(0));
    aceOfHearts.add(aceOfDiamonds.get(0));
    aceOfHearts.addAll(threes);
    aceOfHearts.add(twoOfHearts.get(0));
    aceOfHearts.add(twoOfClubs.get(0));
    aceOfHearts.add(twoOfSpades.get(0));
    this.model.startGame(aceOfHearts, false, 2, 1);
    this.model.moveToFoundation(0, 0);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.moveToFoundation(1, 0));
  }
}