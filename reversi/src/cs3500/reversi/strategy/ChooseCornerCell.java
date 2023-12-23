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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;

/**
 * Represents a strategy that chooses a corner cell if possible.
 */

public final class ChooseCornerCell implements ReversiStrategy {
  @Override
  public Optional<Point> chooseMove(ReadonlyReversiModel model, PieceColor pieceColor) {
    int maxScore = 0;
    Point maxPoint = null;
    List<Point> cornerPoints = this.getCornerPoints(model);
    for (Point p : cornerPoints) {
      ReversiModel modelCopy = model.getModelCopy();
      try {
        modelCopy.playMove(p.x, p.y, pieceColor);
      } catch (IllegalStateException e) {
        continue;
      }
      int score = modelCopy.getScore(pieceColor);
      if (score > maxScore) {
        maxScore = score;
        maxPoint = new Point(p.x, p.y);
      }
    }
    if (maxPoint == null) {
      System.out.println("[ChooseCornerCells AI] No valid moves found for " + pieceColor + ".");
      return Optional.empty();
    }
    int x = maxPoint.x;
    int y = maxPoint.y;
    System.out.printf("[ChooseCornerCells AI] Found move for %s at (%d, %d).%n", pieceColor, x, y);
    return Optional.of(maxPoint);
  }

  /**
   * Gets all corner points.
   *
   * @param model the model to get the corner points from.
   * @return a list of all corner points.
   */

  private List<Point> getCornerPoints(ReadonlyReversiModel model) {
    List<List<ReversiCellPair>> cells = model.getBoardCopy();
    return new ArrayList<>(List.of(
            new Point(0, 0),
            new Point(0, model.getRadius()),
            new Point(model.getRadius(), 0),
            new Point(model.getRadius(), 2 * model.getRadius()),
            new Point(cells.size() - 1, 0),
            new Point(cells.size() - 1, model.getRadius())));
  }
}