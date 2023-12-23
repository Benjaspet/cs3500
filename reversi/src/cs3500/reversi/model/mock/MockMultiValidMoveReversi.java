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

import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;

/**
 * Represents a mock version of a {@link HexagonalReversi} game that logs all moves made to it.
 * This model only returns true for the move (1, 2) and (1, 3) for testing.
 */

public final class MockMultiValidMoveReversi extends HexagonalReversi implements ReversiModel {

  private final StringBuilder log;
  private final int rows;

  /**
   * Constructs a {@link MockMultiValidMoveReversi} with the given log and number of rows.
   * @param log the log to append to.
   * @param rows the number of rows in the board.
   */

  public MockMultiValidMoveReversi(StringBuilder log, int rows) {
    super(rows);
    this.rows = rows;
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;

  }

  /**
   * Constructs a {@link MockMultiValidMoveReversi} with the given log, number of rows,
   * and model to copy off of.
   *
   * @param log the log to append to.
   * @param rows the number of rows in the board.
   * @param model the model to copy.
   */

  public MockMultiValidMoveReversi(StringBuilder log, int rows, ReversiModel model) {
    super(model);
    this.rows = rows;
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;

  }

  @Override
  public void playMove(int row, int col, PieceColor pieceColor)
          throws IllegalArgumentException, IllegalStateException {
    String base = String.format("Played move at (%d, %d) for %s.", row, col, pieceColor);
    this.log.append(base);
    super.playMove(row, col, pieceColor);
  }

  @Override
  public boolean isValidMove(int row, int col, PieceColor pieceColor) {
    return row == 1 && col == 2 || row == 1 && col == 3;
  }

  @Override
  public ReversiModel getModelCopy() {
    return new MockMultiValidMoveReversi(log, rows, this);
  }
}