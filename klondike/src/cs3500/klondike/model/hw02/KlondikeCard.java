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

/**
 * Represents a card in a game of Klondike solitaire.
 */

public class KlondikeCard implements Card {

  private final CardValue value;
  private final Suit suit;

  /**
   * Create a new KlondikeCard.
   * @param value the value of this card. 1 is an ace, 2-10 are their
   *              corresponding numbers, 11 is a jack, 12 is a queen,
   *              and 13 is a king. The actual value of the card is handled
   *              by the toString() method in this class.
   * @param suit  the suit of this card.
   */

  public KlondikeCard(CardValue value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }

  /**
   * Render this KlondikeCard as a string.
   * @return the string representation of this card.
   */

  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  /**
   * Determines if this card is equal to another card, by fields.
   * @param other the card to compare this card to.
   * @return true if this card is equal to the other card, false otherwise.
   */

  @Override
  public boolean equals(Object other) {
    if (other instanceof KlondikeCard) {
      KlondikeCard o = (KlondikeCard) other;
      return this.value == o.value && this.suit.equals(o.suit);
    }
    return false;
  }

  /**
   * Gets the hashcode of this card.
   * @return the hashcode of this card.
   */

  @Override
  public int hashCode() {
    return this.value.hashCode() * 100 + this.suit.hashCode();
  }

  /**
   * Gets the suit of this card.
   *
   * @return the suit of this card.
   */

  @Override
  public String getSuit() {
    return this.suit.toString();
  }

  /**
   * Gets the value of this card.
   * @return the value of this card.
   */

  @Override
  public int getValue() {
    return this.value.ordinal() + 1;
  }

  /**
   * Determines if this card is red or not.
   * @return true if this card is red, false otherwise.
   */

  public boolean isRed() {
    return this.suit.equals(Suit.HEARTS) || this.suit.equals(Suit.DIAMONDS);
  }
}