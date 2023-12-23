/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.Optional;

import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadOnlyReversiInterface;

/**
 * Interface representing a strategy for playing Reversi.
 */
public interface ReversiStrategy {

  /**
   * <p>
   * Chooses the best move to make in a game of Reversi based on this strategy. If multiple best
   * moves are identified, the method selects the topmost, leftmost coordinate as the preferred move
   * </p>
   * <p>
   * The topmost, leftmost coordinate is classified by the smallest row number (topmost) and, in
   * case of a tie in rows, the smallest column number (leftmost).
   * </p>
   *
   * @param model The current state of the Reversi game.
   * @param player The player for whom the move is being chosen.
   * @return An Optional containing the chosen AxialCoordinate. If no valid moves are available,
   *         returns Optional.empty().
   */

  Optional<AxialCoordinate> chooseMove(ReadOnlyReversiInterface model, Piece player);

  /**
   * Gives a list of the best possible next moves to make in a game of Reversi.
   *
   * @param model The current state of the Reversi game.
   * @param player The player for whom the move is being chosen.
   * @return a list of potential moves, each with a score. If no moves are available, returns an
   *         empty list.
   */

  ArrayList<Move> getBestMoves(ReadOnlyReversiInterface model, Piece player);
}
