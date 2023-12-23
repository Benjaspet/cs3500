/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.player;

import cs3500.reversi.provider.model.Piece;

/**
 * Represents a player in a game of Reversi.
 */

public interface IPlayer {

  Piece getColor();

  String getName();
}
