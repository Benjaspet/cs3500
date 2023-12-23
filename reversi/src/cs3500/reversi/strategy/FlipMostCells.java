/*
 * Copyright © 2023 Ben Petrillo, Hunter Pong. All rights reserved.
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

package cs3500.reversi.strategy;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;

/**
 * A strategy that chooses the move that will flip the most pieces.
 * If there are multiple moves that will flip the same number of pieces, it will choose the
 * top leftmost move.
 * If there are no valid moves, it will return an empty optional.
 * If there are no moves that will flip any pieces, it will return an empty optional.
 *
 * @see ReversiStrategy
 */

public class FlipMostCells implements ReversiStrategy {

  @Override
  public Optional<Point> chooseMove(ReadonlyReversiModel model, PieceColor pieceColor) {
    List<List<ReversiCellPair>> copy = model.getBoardCopy();
    int numRows = copy.size();
    int maxScore = 0;
    Point maxPoint = null;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < copy.get(i).size(); j++) {
        ReversiModel modelCopy = model.getModelCopy();
        if (modelCopy.isValidMove(i, j, pieceColor)) {
          modelCopy.playMove(i, j, pieceColor);
          int score = modelCopy.getScore(pieceColor);
          if (score > maxScore) {
            maxScore = score;
            maxPoint = new Point(i, j);
          }
        }
      }
    }
    if (maxPoint == null) {
      System.out.println("[FlipMostCells AI] No valid moves found for " + pieceColor + ".");
      return Optional.empty();
    }
    int x = maxPoint.x;
    int y = maxPoint.y;
    System.out.printf("[FlipMostCells AI] Found move for %s at (%d, %d).%n", pieceColor, x, y);
    return Optional.of(maxPoint);
  }
}
