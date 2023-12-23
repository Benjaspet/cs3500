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

/**
 * Represents a strategy that avoids choosing moves next to corners.
 */

public final class AvoidCellsNextToCorners implements ReversiStrategy {
  @Override
  public Optional<Point> chooseMove(ReadonlyReversiModel model, PieceColor pieceColor) {
    int maxScore = 0;
    Point maxPoint = null;
    for (Point p : this.getPointsNotNextToCorners(model)) {
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
      System.out.println("[AvoidNextToCorners AI] No valid moves found for " + pieceColor + ".");
      return Optional.empty();
    }
    int x = maxPoint.x;
    int y = maxPoint.y;
    System.out.printf("[AvoidNextToCorners AI] Found move for %s at (%d, %d).%n", pieceColor, x, y);
    return Optional.of(maxPoint);
  }

  /**
   * Gets all points next to corners.
   *
   * @param model the model to get the points from.
   * @return a list of all points next to corners.
   */

  private List<Point> getPointsNotNextToCorners(ReadonlyReversiModel model) {
    List<Point> pointsNotNextToCorners = new ArrayList<>();
    List<Point> pointsNextToCorners = this.getPointsNextToCorners(model);
    for (int i = 0; i < model.getBoardCopy().size(); i++) {
      for (int j = 0; j < model.getBoardCopy().get(i).size(); j++) {
        if (pointsNextToCorners.contains(new Point(i, j))) {
          pointsNextToCorners.remove(new Point(i, j));
        } else {
          pointsNotNextToCorners.add(new Point(i, j));
        }
      }
    }
    return pointsNotNextToCorners;
  }

  /**
   * Gets all points next to corners.
   * @param model the model to get the points from.
   * @return a list of all points next to corners.
   */

  private List<Point> getPointsNextToCorners(ReadonlyReversiModel model) {
    int firstRowSize = model.getBoardCopy().get(0).size();
    int middleRowSize = model.getBoardCopy().get(model.getBoardCopy().size() / 2).size();
    int lastRowSize = model.getBoardCopy().get(model.getBoardCopy().size() - 1).size();
    return new ArrayList<>(List.of(
            new Point(0, 1),
            new Point(1, 0),
            new Point(1, 1),
            new Point(0, firstRowSize - 2),
            new Point(1, firstRowSize - 1),
            new Point(1, firstRowSize - 2),
            new Point(middleRowSize - 1, 1),
            new Point(middleRowSize - 2, 0),
            new Point(middleRowSize - 2, 1),
            new Point(middleRowSize - 1, middleRowSize - 2),
            new Point(middleRowSize - 2, middleRowSize - 1),
            new Point(middleRowSize - 2, middleRowSize - 2),
            new Point(lastRowSize - 1, 1),
            new Point(lastRowSize - 2, 0),
            new Point(lastRowSize - 2, 1),
            new Point(lastRowSize - 1, lastRowSize - 2),
            new Point(lastRowSize - 2, lastRowSize - 1),
            new Point(lastRowSize - 2, lastRowSize - 2)
    ));
  }
}