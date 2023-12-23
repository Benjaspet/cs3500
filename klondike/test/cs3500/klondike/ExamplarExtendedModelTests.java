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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * Tests for the extended model implementations:
 * LimitedDrawKlondike and WhiteheadKlondike.
 */

public class ExamplarExtendedModelTests {

  KlondikeModel model;

  @Test
  public void testLimitedDrawOfZero() {
    this.model = new LimitedDrawKlondike(0);
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.model.startGame(deck, false, 2, 1);
    this.model.discardDraw();
    assertEquals(0, this.model.getDrawCards().size());
  }

  @Test
  public void testRightNumberWrongColorMovePile() {
    this.model = new WhiteheadKlondike();
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    List<Card> twos = baseDeck
            .stream().filter(card -> card.toString().contains("2"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    deck.add(2, twos.get(1));
    deck.add(5, twos.get(0));
    deck.add(6, twos.get(2));
    deck.add(7, twos.get(3));
    this.model.startGame(deck, false, 2, 1);
    Assert.assertThrows(IllegalStateException.class, () -> this.model.movePile(0, 1, 1));
  }

  @Test
  public void testValidMovePile() {
    this.model = new WhiteheadKlondike();
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    List<Card> twos = baseDeck
            .stream().filter(card -> card.toString().contains("2"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    deck.add(2, twos.get(0));
    deck.add(5, twos.get(1));
    deck.add(6, twos.get(2));
    deck.add(7, twos.get(3));
    this.model.startGame(deck, false, 2, 1);
    this.model.movePile(0, 1, 1);
    assertEquals(3, this.model.getPileHeight(1));
  }

  @Test
  public void testDiscardDraw() {
    this.model = new LimitedDrawKlondike(2);
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.model.startGame(deck, false, 2, 1);
    assertEquals(1, this.model.getDrawCards().size());
  }

  @Test
  public void testIsolate() {
    this.model = new LimitedDrawKlondike(2);
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.model.startGame(deck, false, 2, 1);
    this.model.discardDraw();
    assertEquals(1, this.model.getDrawCards().size());
  }

  // [A♠, A♡, A♢, A♣]

  @Test
  public void testMovePileMultipleCardsNotSameSuit() {
    this.model = new WhiteheadKlondike();
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    List<Card> twos = baseDeck
            .stream().filter(card -> card.toString().contains("2"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    List<Card> threes = baseDeck
            .stream().filter(card -> card.toString().contains("3"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    List<Card> deckToBuild = new ArrayList<>();
    deckToBuild.add(threes.get(0));
    deckToBuild.add(threes.get(3));
    deckToBuild.add(deck.get(3));
    deckToBuild.add(twos.get(3));
    deckToBuild.add(deck.get(0));
    deckToBuild.add(threes.get(1));
    deckToBuild.add(threes.get(2));
    deckToBuild.add(twos.get(0));
    deckToBuild.add(twos.get(1));
    deckToBuild.add(twos.get(2));
    deckToBuild.add(deck.get(1));
    deckToBuild.add(deck.get(2));
    this.model.startGame(deckToBuild, false, 2, 1);
    this.model.moveToFoundation(1, 0);
    this.model.moveDraw(1);
    this.model.moveDraw(1);
    assertThrows(IllegalStateException.class, () -> this.model.movePile(1, 2, 0));
  }

  @Test
  public void testValidMoveNonKingToEmptyCascadePile() {
    this.model = new WhiteheadKlondike();
    List<Card> deck = model.getDeck();
    List<Card> aces = deck.stream().filter(c -> c.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    aces.sort(Comparator.comparing(Card::toString));
    model.startGame(aces, false, 2, 1);
    model.moveToFoundation(0, 0);
    model.movePile(1, 1, 0);
    assertEquals(1, model.getPileHeight(0));
  }
}