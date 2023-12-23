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

package cs3500.reversi.model.mock;

import java.util.List;

import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;

/**
 * Represents a mock Reversi model that avoids choosing moves next to corners.
 */

public final class MockAvoidNextToCornerReversi extends HexagonalReversi implements ReversiModel {

  private final StringBuilder log;
  private final int rows;

  /**
   * Constructs a new {@link MockAvoidNextToCornerReversi} with the given log and
   * number of rows.
   * @param log the log to use.
   * @param rows the number of rows to use.
   */

  public MockAvoidNextToCornerReversi(StringBuilder log, int rows) {
    super(rows);
    this.rows = rows;
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;

  }

  @Override
  public boolean isValidMove(int row, int col, PieceColor pieceColor) {
    String msg = "Checked if move at (%d, %d) for %s is valid.%n";
    String base = String.format(msg, row, col, pieceColor);
    this.log.append(base);
    List<List<ReversiCellPair>> cells = this.getBoardCopy();
    int size = cells.size();
    int firstRowSize = cells.get(0).size();
    int middleRowSize = cells.get(size / 2).size();
    int lastRowSize = cells.get(size - 1).size();
    return row == 0 && col == 1
            || row == 1 && col == 0
            || row == 1 && col == 1
            || row == 0 && col == firstRowSize - 2
            || row == 1 && col == firstRowSize - 1
            || row == 1 && col == firstRowSize - 2
            || row == middleRowSize - 1 && col == 1
            || row == middleRowSize - 2 && col == 0
            || row == middleRowSize - 2 && col == 1
            || row == middleRowSize - 1 && col == middleRowSize - 2
            || row == middleRowSize - 2 && col == middleRowSize - 1
            || row == middleRowSize - 2 && col == middleRowSize - 2
            || row == lastRowSize - 1 && col == 1
            || row == lastRowSize - 2 && col == 0
            || row == lastRowSize - 2 && col == 1
            || row == lastRowSize - 1 && col == lastRowSize - 2
            || row == lastRowSize - 2 && col == lastRowSize - 1
            || row == lastRowSize - 2 && col == lastRowSize - 2;
  }

  @Override
  public void playMove(int row, int col, PieceColor pieceColor)
          throws IllegalArgumentException, IllegalStateException {
    String base = String.format("Checked move at (%d, %d) for %s.", row, col, pieceColor);
    this.log.append(base);
    super.playMove(row, col, pieceColor);
  }

  @Override
  public ReversiModel getModelCopy() {
    return new MockAvoidNextToCornerReversi(this.log, this.rows);
  }
}
