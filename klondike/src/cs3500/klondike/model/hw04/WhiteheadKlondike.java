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

import java.util.Stack;

/**
 * Create a new Whitehead Klondike game.
 * Cascade piles are built down by the same color, and empty piles can be filled
 * with any card. When moving a chunk of cards, the entire chunk must be of the
 * same suit.
 */

public class WhiteheadKlondike extends AKlondike {

  @Override
  public void movePile(int srcPile, int numCards, int destPile) throws IllegalStateException {
    this.runMovePileChecks(srcPile, numCards, destPile);
    Stack<Card> source = this.cascadePiles.get(srcPile);
    Stack<Card> dest = this.cascadePiles.get(destPile);
    if (source.isEmpty()) {
      throw new IllegalStateException("The source pile is empty.");
    }
    this.checkAllSameSuit(source, numCards);
    if (!dest.isEmpty()) {
      if (!this.isPileMoveValid(source.get(source.size() - numCards), dest.peek())) {
        throw new IllegalStateException("This move is not allowed.");
      }
    }
    Stack<Card> tempStack = new Stack<>();
    for (int i = 0; i < numCards; i++) {
      tempStack.push(source.pop());
    }
    for (int i = 0; i < numCards; i++) {
      dest.push(tempStack.pop());
    }
  }

  @Override
  public void moveDraw(int destPile) throws IllegalStateException {
    this.runMoveDrawChecks(destPile);
    if (this.cascadePiles.get(destPile).isEmpty()) {
      Card drawn = this.drawPile.remove(0);
      this.cascadePiles.get(destPile).push(drawn);
    } else {
      Card destTopCard = this.cascadePiles.get(destPile).peek();
      if (!(this.isPileMoveValid(this.drawPile.get(0), destTopCard))) {
        throw new IllegalStateException("This move is not allowed.");
      } else {
        Card drawn = this.drawPile.remove(0);
        this.cascadePiles.get(destPile).push(drawn);
      }
    }
  }

  @Override
  protected boolean isPileMoveValid(Card source, Card dest) {
    return source.isRed() == dest.isRed() && source.getValue() == dest.getValue() - 1;
  }

  @Override
  public boolean isCardVisible(int pileNum, int cardNum) throws IllegalStateException {
    super.runIsCardVisibleChecks(pileNum, cardNum);
    return true;
  }

  private void checkAllSameSuit(Stack<Card> chunk, int numToMove) {
    String firstCard = chunk.get(chunk.size() - 1).toString();
    String firstSuit = firstCard.substring(firstCard.length() - 1);
    for (int i = chunk.size() - 1; i > chunk.size() - 1 - numToMove; i--) {
      String currCard = chunk.get(i).toString();
      String currSuit = currCard.substring(currCard.length() - 1);
      if (!currSuit.equals(firstSuit)) {
        throw new IllegalStateException("All cards being moved must be the same suit.");
      }
    }
  }
}