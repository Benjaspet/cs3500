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

public final class MockGetCornerReversi extends HexagonalReversi implements ReversiModel {
  private final StringBuilder log;
  private final int rows;

  /**
   * Constructs a new {@link MockGetCornerReversi} with the given log and
   * number of rows.
   * @param log the log to use.
   * @param rows the number of rows to use.
   */

  public MockGetCornerReversi(StringBuilder log, int rows) {
    super(rows);
    this.rows = rows;
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;

  }

  @Override
  public void playMove(int row, int col, PieceColor pieceColor)
          throws IllegalArgumentException, IllegalStateException {
    String base = String.format("Checked move at (%d, %d) for %s.", row, col, pieceColor);
    this.log.append(base);
    super.playMove(row, col, pieceColor);
  }

  @Override
  public boolean isValidMove(int row, int col, PieceColor pieceColor) {
    String msg = "Checked if move at (%d, %d) for %s is valid.%n";
    String base = String.format(msg, row, col, pieceColor);
    List<List<ReversiCellPair>> cells = this.getBoardCopy();
    this.log.append(base);
    int size = cells.size();
    return row == 0 && col == 0 ||
            row == size - 1 && col == 0 ||
            row == 0 && col == cells.get(0).size() - 1 ||
            row == size - 1 && col == cells.get(size - 1).size() - 1 ||
            row == size / 2 && col == 0 ||
            row == size / 2 && col == cells.get(size / 2).size() - 1;
  }

  @Override
  public ReversiModel getModelCopy() {
    return new MockGetCornerReversi(this.log, this.rows);
  }
}