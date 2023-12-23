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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for the LimitedDrawKlondike implementation.
 */

public class LimitedDrawModelTests {

  KlondikeModel model;
  List<Card> deck1;
  List<Card> invalidDeck1;
  List<Card> invalidDeck2;

  @Before
  public void init() {
    this.model = new LimitedDrawKlondike(2);
  }

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
  public void testDiscardDrawUntilDeckIsEmpty() {
    this.model = new LimitedDrawKlondike(2);
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.model.startGame(deck, false, 2, 1);
    assertEquals(1, this.model.getDrawCards().size());
    this.model.discardDraw();
    assertEquals(1, this.model.getDrawCards().size());
    this.model.discardDraw();
    this.model.discardDraw();
    assertEquals(0, this.model.getDrawCards().size());
  }

  @Test
  public void testDiscardDrawWhenDrawPileIsEmpty() {
    this.model = new LimitedDrawKlondike(2);
    List<Card> baseDeck = this.model.getDeck();
    List<Card> deck = baseDeck
            .stream().filter(card -> card.toString().contains("A"))
            .sorted(Comparator.comparing(Card::toString))
            .collect(Collectors.toList());
    this.model.startGame(deck, false, 2, 1);
    this.model.discardDraw();
    this.model.discardDraw();
    this.model.discardDraw();
    assertThrows(IllegalStateException.class, () -> this.model.discardDraw());
  }

  @Test
  public void testConstructorExceptions() {
    assertThrows(IllegalArgumentException.class, () -> new LimitedDrawKlondike(-1));
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
}