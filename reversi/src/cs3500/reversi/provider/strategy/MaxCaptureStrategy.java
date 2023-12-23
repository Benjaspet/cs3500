/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadOnlyReversiInterface;

/**
 * Represents the strategy where the player attempts to capture the max number of the opponents'
 * tokens.
 */
public class MaxCaptureStrategy implements ReversiStrategy {

  private ReadOnlyReversiInterface model;
  private Piece player;

  @Override
  public Optional<AxialCoordinate> chooseMove(ReadOnlyReversiInterface model, Piece piece) {
    ArrayList<Move> bestMoves = this.getBestMoves(model, piece);

    if (bestMoves.isEmpty()) {
      return Optional.empty();
    } else if (bestMoves.size() == 1) {
      return Optional.of(bestMoves.get(0).getCoord());
    } else {
      return Optional.of(bestMoves.stream()
              .min((m1, m2) -> {
                if (m1.getCoord().getR() != m2.getCoord().getR()) {
                  return Integer.compare(m1.getCoord().getR(), m2.getCoord().getR());
                } else {
                  return Integer.compare(m1.getCoord().getQ(), m2.getCoord().getQ());
                }
              })
              .get()
              .getCoord());
    }
  }


  @Override
  public ArrayList<Move> getBestMoves(ReadOnlyReversiInterface model, Piece piece) {
    if (model == null || piece == null) {
      throw new IllegalArgumentException("Cannot provide null argument");
    }

    this.model = model;
    this.player = piece;
    ArrayList<Move> moves = new ArrayList<>();
    int maxCaptures = 0;

    Map<AxialCoordinate, CellType> board = model.getProviderBoard();
    for (AxialCoordinate coord : board.keySet()) {
      if (model.isValidMove(player, coord)) {
        int captures = calculateCaptures(coord);
        if (captures > maxCaptures) {
          maxCaptures = captures;
          moves.clear();
          moves.add(new Move(coord, captures));
        } else if (captures == maxCaptures) {
          moves.add(new Move(coord, captures));
        }
      }
    }
    return moves;
  }

  /**
   * Calculates the number of opponent pieces that would be flipped if the player places a piece
   * at the specified coordinate. This method examines all six directions from the given coordinate
   * and counts the number of flippable opponent pieces in each direction, based on the standard
   * Reversi rules.
   *
   * @param coord  The axial coordinate where the player intends to place the piece.
   * @return The total number of opponent pieces that would be flipped by placing a piece at the
   *         given coordinate.
   */
  private int calculateCaptures(AxialCoordinate coord) {
    int numberOfCapturedPieces = 0;
    Piece opposite = (player == Piece.BLACK) ? Piece.WHITE : Piece.BLACK;

    for (AxialCoordinate direction : AxialCoordinate.getNeighborDirections()) {
      List<AxialCoordinate> potentialFlips = new ArrayList<>();
      AxialCoordinate current = new AxialCoordinate(coord.getQ() + direction.getQ(),
              coord.getR() + direction.getR());

      while (model.getProviderBoard().containsKey(current)) {
        Piece currentPiece = model.getProviderBoard().get(current).getPiece();

        if (currentPiece == Piece.EMPTY) {
          break;
        } else if (currentPiece == opposite) {
          potentialFlips.add(current);
        } else if (currentPiece == player) {
          numberOfCapturedPieces += potentialFlips.size();
          break;
        }

        current = new AxialCoordinate(current.getQ() + direction.getQ(),
                current.getR() + direction.getR());
      }
    }

    return numberOfCapturedPieces;
  }
}
