/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

/**
 * Defines the model for a game of Reversi. This interface specifies the essential actions and
 * queries that can be performed in a Reversi game, regardless of the board shape or size.
 */
public interface ProviderReversiModel extends ReadOnlyReversiInterface {

  void startGame();

  /**
   * Passes the turn to the other player.
   * @throws IllegalStateException if the game is not in play.
   */
  void passTurn();

  /**
   * Places a piece on the board at the specified axial coordinate.
   *
   * @param piece The piece to be placed.
   * @param coord The axial coordinate where the piece is to be placed.
   * @throws IllegalArgumentException if an empty piece is attempted to be placed.
   * @throws IllegalStateException if the game is not in play, or if it is not the turn of the
   *                               given piece, or if the move is not playable.
   */
  void placePiece(Piece piece, AxialCoordinate coord);

}
