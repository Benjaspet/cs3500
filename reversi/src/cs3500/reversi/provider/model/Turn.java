/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

/**
 * Enumeration representing the turns in a Hexagonal Reversi game.
 * This enum helps in managing the turn state of the game, indicating whether it is BLACK's turn or
 * WHITE's turn.
 */
public enum Turn {
  BLACK, WHITE;

  /**
   * Switches the current turn to the next player.
   * If the current turn is BLACK, it switches to WHITE, and vice versa.
   *
   * @return the next player's turn.
   */
  public Turn next() {
    return this == BLACK ? WHITE : BLACK;
  }
}