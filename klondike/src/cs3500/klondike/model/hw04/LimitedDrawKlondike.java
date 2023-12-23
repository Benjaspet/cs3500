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

package cs3500.klondike.model.hw04;

import cs3500.klondike.model.hw02.Card;

import java.util.HashMap;

/**
 * Represents a version of Klondike where each card in the draw pile can only be drawn a certain
 * number of times. For example, a LimitedDrawKlondike with a numRedraw of 2 will allow each card
 * to be at the leftmost side of the draw cards twice. After that, the card will be permanantly
 * discarded.
 */

public class LimitedDrawKlondike extends AKlondike {

  private final HashMap<Card, Integer> redrawCounts = new HashMap<>();
  private final int numRedraw;

  /**
   * Create a new LimitedDrawKlondike.
   * @param numRedraw the number of times each card can be drawn.
   */

  public LimitedDrawKlondike(int numRedraw) {
    super();
    if (numRedraw < 0) {
      throw new IllegalArgumentException("The redraw number must be positive.");
    }
    this.numRedraw = numRedraw;
  }

  @Override
  public void startGame(java.util.List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    super.startGame(deck, shuffle, numPiles, numDraw);
    for (Card c : this.drawPile) {
      this.redrawCounts.put(c, this.numRedraw);
    }
  }

  @Override
  public void discardDraw() {
    this.runHasGameStartedCheck(false);
    this.runIsDrawPileEmptyCheck();
    Card key = this.drawPile.remove(0);
    int value = this.redrawCounts.get(key);
    if (value > 0) {
      this.redrawCounts.put(key, value - 1);
      this.drawPile.add(key);
    }
  }
}