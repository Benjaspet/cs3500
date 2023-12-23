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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.CardValue;
import cs3500.klondike.model.hw02.KlondikeCard;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.Suit;

/**
 * Represents an abstract default game of Klondike solitaire.
 * @field foundationPiles represents the list of foundation piles for this game.
 * @field cascadePiles represents the list of cascade piles for this game.
 * @field drawPile represents the draw pile for this game.
 * @field numPiles represents the number of cascade piles for this game.
 * @field numDraw represents the number of cards to be drawn each time for this game.
 * @field hasStarted represents whether this game has started.
 */

public abstract class AKlondike implements KlondikeModel {

  protected List<Stack<Card>> foundationPiles = new ArrayList<>();
  protected List<Stack<Card>> cascadePiles = new ArrayList<>();
  protected List<Stack<Boolean>> visibilities = new ArrayList<>();

  protected List<Card> drawPile = new ArrayList<>();

  private int numPiles;
  private int numDraw;

  private boolean hasStarted = false;

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>(List.of());
    for (CardValue value : CardValue.values()) {
      for (Suit suit : Suit.values()) {
        deck.add(new KlondikeCard(value, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPiles, int numDraw)
          throws IllegalArgumentException, IllegalStateException {
    this.runHasGameStartedCheck(true);
    if (!this.isValidDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck provided.");
    }
    if (numPiles <= 0 || numDraw <= 0) {
      throw new IllegalArgumentException("Number of piles and/or draw must be at least 1.");
    }
    if (deck.size() < (numPiles * (numPiles + 1)) / 2) {
      throw new IllegalArgumentException("Deck is too small for the given number of piles.");
    }
    if (shuffle) {
      Collections.shuffle(deck);
    }
    this.numPiles = numPiles;
    this.foundationPiles = this.generateFoundationPiles(deck);
    this.numDraw = numDraw;
    this.generateAllPiles(deck, numPiles);
    this.initVisibility();
    this.hasStarted = true;
  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile) throws IllegalStateException {
    this.runMovePileChecks(srcPile, numCards, destPile);
    if (this.cascadePiles.get(destPile).isEmpty()) {
      if (this.cascadePiles.get(srcPile).peek().getValue() == 13) {
        for (int i = 0; i < numCards; i++) {
          this.cascadePiles.get(destPile).push(this.cascadePiles.get(srcPile).pop());
        }
      } else {
        throw new IllegalStateException("Only kings can be moved to empty cascade piles.");
      }
    } else {
      Card srcTopCard = this.cascadePiles.get(srcPile).get(this.getPileHeight(srcPile) - numCards);
      if (!(this.isPileMoveValid(srcTopCard, this.cascadePiles.get(destPile).peek()))) {
        throw new IllegalStateException("This move is not allowed.");
      } else {
        Stack<Card> tempStack = new Stack<>();
        for (int i = 0; i < numCards; i++) {
          Card toPush = this.cascadePiles.get(srcPile).pop();
          tempStack.push(toPush);
        }
        if (!this.cascadePiles.get(srcPile).isEmpty()) {
          this.visibilities.get(srcPile).remove(0);
          this.visibilities.get(srcPile).push(true);
        }
        for (int i = 0; i < numCards; i++) {
          this.cascadePiles.get(destPile).push(tempStack.pop());
          this.visibilities.get(destPile).push(true);
        }
      }
    }
  }

  @Override
  public void moveDraw(int destPile) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(destPile);
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("The draw pile is empty.");
    }
    if (this.cascadePiles.get(destPile).isEmpty()) {
      if (this.drawPile.get(0).getValue() == 13) {
        Card drawn = this.drawPile.remove(0);
        this.cascadePiles.get(destPile).push(drawn);
        this.visibilities.get(destPile).push(true);
      } else {
        throw new IllegalStateException("Only kings can be moved to empty cascade piles.");
      }
    } else {
      if (!(this.isPileMoveValid(this.drawPile.get(0), this.cascadePiles.get(destPile).peek()))) {
        throw new IllegalStateException("This move is not allowed.");
      } else {
        Card drawn = this.drawPile.remove(0);
        this.cascadePiles.get(destPile).push(drawn);
        this.visibilities.get(destPile).push(true);
      }
    }
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runFoundationPileCheck(foundationPile);
    this.runPileNumCheck(srcPile);
    if (this.cascadePiles.get(srcPile).isEmpty()) {
      throw new IllegalStateException("The source pile is empty.");
    }
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      if (this.cascadePiles.get(srcPile).peek().getValue() == 1) {
        this.foundationPiles.get(foundationPile).push(this.cascadePiles.get(srcPile).pop());
      } else {
        throw new IllegalStateException("Only aces can be moved to empty foundation piles.");
      }
    } else {
      if (!(this.isFoundationMoveValid(this.cascadePiles.get(srcPile).peek(),
              this.foundationPiles.get(foundationPile).peek()))) {
        throw new IllegalStateException("This move is not allowed.");
      }
      this.foundationPiles.get(foundationPile).push(this.cascadePiles.get(srcPile).pop());
    }
    if (!this.cascadePiles.get(srcPile).isEmpty()) {
      this.visibilities.get(srcPile).remove(0);
      this.visibilities.get(srcPile).push(true);
    }
  }

  @Override
  public void moveDrawToFoundation(int foundationPile) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runFoundationPileCheck(foundationPile);
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("The draw pile is empty; this move is not allowed.");
    }
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      if (this.drawPile.get(0).getValue() == 1) {
        this.foundationPiles.get(foundationPile).push(this.drawPile.remove(0));
      } else {
        throw new IllegalStateException("Only aces can be moved to empty foundation piles.");
      }
    } else {
      Card drawn = this.drawPile.get(0);
      Card foundationPileTop = this.foundationPiles.get(foundationPile).peek();
      if (!this.isFoundationMoveValid(drawn, foundationPileTop)) {
        throw new IllegalStateException("This move is not allowed.");
      } else {
        this.foundationPiles.get(foundationPile).push(this.drawPile.remove(0));
      }
    }
  }

  @Override
  public void discardDraw() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("The draw pile is empty.");
    }
    this.drawPile.add(this.drawPile.remove(0));
  }

  @Override
  public int getNumRows() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    int maxPileHeight = 0;
    for (int pileIndex = 0; pileIndex < this.getNumPiles(); pileIndex++) {
      if (this.getPileHeight(pileIndex) > maxPileHeight) {
        maxPileHeight = this.getPileHeight(pileIndex);
      }
    }
    return maxPileHeight;
  }

  @Override
  public int getNumPiles() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    return this.numPiles;
  }

  @Override
  public int getNumDraw() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    return this.numDraw;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    return this.drawPile.isEmpty();
  }

  @Override
  public int getScore() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    int score = 0;
    for (Stack<Card> pile : this.foundationPiles) {
      if (!pile.isEmpty()) {
        score += pile.peek().getValue();
      }
    }
    return score;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(pileNum);
    return this.cascadePiles.get(pileNum).size();
  }

  @Override
  public boolean isCardVisible(int pileNum, int card) throws IllegalStateException {
    this.runIsCardVisibleChecks(pileNum, card);
    return this.visibilities.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int pileNum, int card) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(pileNum);
    if (this.cascadePiles.get(pileNum).isEmpty()) {
      return null;
    }
    this.runCardIndexValidCheck(card, pileNum);
    if (!this.isCardVisible(pileNum, card)) {
      throw new IllegalArgumentException("The provided card is not visible.");
    }
    return this.cascadePiles.get(pileNum).get(card);
  }

  @Override
  public Card getCardAt(int foundationPile) throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    this.runFoundationPileCheck(foundationPile);
    if (this.foundationPiles.get(foundationPile).isEmpty()) {
      return null;
    }
    return this.foundationPiles.get(foundationPile).peek();
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    List<Card> drawCards = new ArrayList<>();
    if (drawPile.size() < numDraw) {
      drawCards.addAll(drawPile);
    } else {
      for (int i = 0; i < numDraw; i++) {
        drawCards.add(drawPile.get(i));
      }
    }
    return Collections.unmodifiableList(drawCards);
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    this.runHasGameStartedCheck(false);
    return this.foundationPiles.size();
  }

  private List<Stack<Card>> generateFoundationPiles(List<Card> deck) {
    List<Stack<Card>> foundationPiles = new ArrayList<>(List.of());
    List<Card> aces = deck.stream().filter(c -> c.getValue() == 1).collect(Collectors.toList());
    for (int i = 0; i < aces.size(); i++) {
      foundationPiles.add(new Stack<>());
    }
    return foundationPiles;
  }

  protected boolean isPileMoveValid(Card source, Card dest) {
    return source.isRed() != dest.isRed() && source.getValue() == dest.getValue() - 1;
  }

  private boolean isFoundationMoveValid(Card src, Card dest) {
    String srcSuit = src.toString().substring(src.toString().length() - 1);
    String destSuit = dest.toString().substring(dest.toString().length() - 1);
    return srcSuit.equals(destSuit) && src.getValue() == dest.getValue() + 1;
  }

  protected void runHasGameStartedCheck(boolean started) throws IllegalStateException {
    if (started) {
      if (this.hasStarted) {
        throw new IllegalStateException("The game has already started.");
      }
    } else {
      if (!this.hasStarted) {
        throw new IllegalStateException("The game has not yet started.");
      }
    }
  }

  private void runFoundationPileCheck(int foundationPile) throws IllegalArgumentException {
    if (foundationPile < 0 || foundationPile >= this.getNumFoundations()) {
      throw new IllegalArgumentException("Invalid foundation pile number provided.");
    }
  }

  protected void runPileNumCheck(int pileNum) throws IllegalArgumentException {
    if (pileNum < 0 || pileNum >= this.getNumPiles()) {
      throw new IllegalArgumentException("Invalid pile number provided.");
    }
  }

  private void runCardIndexValidCheck(int card, int pileNum) throws IllegalArgumentException {
    if (card < 0 || card >= this.getPileHeight(pileNum)) {
      throw new IllegalArgumentException("Invalid card index provided.");
    }
  }

  protected void runIsDrawPileEmptyCheck() throws IllegalStateException {
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("The draw pile is empty.");
    }
  }

  /**
   * Determines if the provided deck is valid or not. A deck is valid
   * if it contains equal runs of cards of the same suit, starting with an ace
   * and increasing in order.
   * @param deck the deck to check.
   * @return true if the deck is valid, false otherwise.
   */

  private boolean isValidDeck(List<Card> deck) {
    if (deck == null || deck.isEmpty()) {
      return false;
    }
    for (Card card : deck) {
      if (card == null) {
        return false;
      }
    }
    List<Card> tempDeck = new ArrayList<>(deck).stream()
            .sorted(Comparator.comparing(Card::getValue))
            .collect(Collectors.toList());
    List<Stack<Card>> runs = new ArrayList<>();
    for (int i = 0; i < tempDeck.size(); i++) {
      if (tempDeck.get(i).getValue() == 1) {
        runs.add(new Stack<>());
        runs.get(runs.size() - 1).push(tempDeck.get(i));
        tempDeck.remove(i);
        i--;
      }
    }
    for (int i = 0; i < tempDeck.size(); i++) {
      for (Stack<Card> run : runs) {
        int currVal = tempDeck.get(i).getValue();
        int peekVal = run.peek().getValue();
        Card curr = tempDeck.get(i);
        Card peek = run.peek();
        String currSuit = curr.toString().substring(curr.toString().length() - 1);
        String peekSuit = peek.toString().substring(peek.toString().length() - 1);
        if (currSuit.equals(peekSuit) && currVal == peekVal + 1) {
          run.push(tempDeck.get(i));
          tempDeck.remove(i);
          i--;
          break;
        }
      }
    }
    int firstStackLength = runs.get(0).size();
    for (int i = 1; i < runs.size(); i++) {
      if (runs.get(i).size() != firstStackLength) {
        return false;
      }
    }
    return tempDeck.isEmpty();
  }

  private void generateAllPiles(List<Card> deck, int numPiles) {
    List<Stack<Card>> cascadePiles = new ArrayList<>(List.of());
    List<Card> tempDeck = new ArrayList<>(deck);
    for (int i = 0; i < numPiles; i++) {
      cascadePiles.add(new Stack<>());
    }
    int currPile = 0;
    int currCard = 0;
    int startingPile = 0;
    while (currCard < (numPiles * (numPiles + 1)) / 2) {
      if (currPile == numPiles) {
        startingPile++;
        currPile = startingPile;
      }
      cascadePiles.get(currPile).push(deck.get(currCard));
      currPile++;
      currCard++;
    }
    List<Card> nowInCascade = new ArrayList<>();
    for (Stack<Card> pile : cascadePiles) {
      nowInCascade.addAll(pile);
    }
    tempDeck.removeAll(nowInCascade);
    this.drawPile.addAll(tempDeck);
    this.cascadePiles = cascadePiles;
  }

  protected void initVisibility() {
    for (int i = 0; i < this.numPiles; i++) {
      this.visibilities.add(new Stack<>());
      for (int j = 0; j < i + 1; j++) {
        if (j == i) {
          visibilities.get(i).push(true);
        } else {
          visibilities.get(i).push(false);
        }
      }
    }
  }

  protected void runIsCardVisibleChecks(int pileNum, int card) {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(pileNum);
    this.runCardIndexValidCheck(card, pileNum);
  }

  protected void runMoveDrawChecks(int destPile) {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(destPile);
    if (this.drawPile.isEmpty()) {
      throw new IllegalStateException("The draw pile is empty.");
    }
  }

  protected void runMovePileChecks(int srcPile, int numCards, int destPile) {
    this.runHasGameStartedCheck(false);
    this.runPileNumCheck(srcPile);
    this.runPileNumCheck(destPile);
    if (srcPile == destPile) {
      throw new IllegalArgumentException("Source and destination piles cannot be the same.");
    }
    if (numCards < 0 || numCards > this.cascadePiles.get(srcPile).size()) {
      throw new IllegalArgumentException("An invalid number of cards to move was provided.");
    }
  }
}
