/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

import java.util.Map;
import java.util.Optional;

/**
 * A read-only interface for querying the state of a Reversi game.
 * This interface provides methods to get information about the game without modifying its state.
 */
public interface ReadOnlyReversiInterface extends ObservableModel {


  /**
   * Retrieves the current state of the board.
   *
   * @return The current board.
   */
  Map<AxialCoordinate, CellType> getProviderBoard();

  /**
   * Retrieves the Hexagon at the specified axial coordinate on the board.
   *
   * @param hex The axial coordinate identifying the location on the board.
   * @return The Hexagon at the specified axial coordinate.
   * @throws IllegalStateException if the given coordinate does not exist on the board
   */
  CellType getHexagonAt(AxialCoordinate hex);

  /**
   * Checks if it's Black's turn to play.
   *
   * @return true if it's Black's turn, false otherwise.
   */
  boolean isBlackTurn();

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Retrieves the radius of the board.
   *
   * @return The radius of the board.
   */
  int getRadius();

  /**
   * Determines the winner of the game.
   *
   * @return The winning Piece, or an empty optional if the game is still in play or it's a draw.
   */
  Optional<Piece> getGameWinner();

  /**
   * Checks if a move is valid for a specified piece at a given coordinate.
   *
   * @param piece The piece to be placed.
   * @param coord The axial coordinate for the move.
   * @return true if the move is valid, false otherwise.
   */
  boolean isValidMove(Piece piece, AxialCoordinate coord);

  /**
   * Tells us if this player has any valid moves left.
   * @param piece the player we are checking this for.
   * @return true if there are any valid moves left, false if not.
   */
  boolean anyValidMoves(Piece piece);

  /**
   * Tells us the score of the given player at this point in the game. The score is defined as the
   * total number of that player's pieces are currently on the board.
   * @param player the player we want to know the score of, represented by a Piece on the board.
   * @return the score of the player as an integer.
   */
  int getScore(Piece player);

  Piece getCurrentTurn();
}