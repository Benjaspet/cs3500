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

package cs3500.klondike.model.hw02;

import java.util.List;

/**
 * Represents a mock game of Klondike. This is used to test the controller
 * and make sure it passes in inputs correctly, as each method appends
 * to the log.
 */

public class MockKlondike implements KlondikeModel {

  StringBuilder log;

  /**
   * Create a new instance of a mock game of Klondike. This exists
   * solely to ensure the inputs are being passed through the controller
   * correctly.
   * @param log the log to append to.
   */

  public MockKlondike(StringBuilder log) {
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    return List.of();
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw) {
    String base = "Started game with deckSize = %s, shuffle = %s, numPiles = %s, numDraw = %s.\n";
    this.log.append(String.format(base, deck.size(), shuffle, numPiles, numDraw));

  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) {
    String base = "Moved %d card(s) from pile %d to pile %d.\n";
    this.log.append(String.format(base, numCards, srcPile, destPile));
  }

  @Override
  public void moveDraw(int destPile) throws IllegalArgumentException {
    this.log.append(String.format("Moved 1 draw card to pile %d.\n", destPile));
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile) {
    String base = "Moved 1 card from cascade pile %d to foundation pile %d.\n";
    this.log.append(String.format(base, srcPile, foundationPile));
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) throws IllegalStateException {
    String base = "Moved 1 draw card to foundation pile %d.\n";
    this.log.append(String.format(base, foundationPile));
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    this.log.append("Discarded 1 draw card.\n");
  }

  @Override
  public int getNumRows() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumPiles() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumDraw() throws IllegalStateException {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalStateException {
    return 0;
  }

  @Override
  public boolean isCardVisible(int pileNum, int cardIndex) throws IllegalStateException {
    return false;
  }

  @Override
  public Card getCardAt(int pileNum, int cardIndex) throws IllegalStateException {
    return null;
  }

  @Override
  public Card getCardAt(int foundationPile) throws IllegalStateException {
    return null;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return List.of();
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    return 0;
  }
}
