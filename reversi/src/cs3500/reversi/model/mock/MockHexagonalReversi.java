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
 * A mock implementation of {@link HexagonalReversi} that logs all calls to {@link
 * HexagonalReversi#playMove(int, int, PieceColor)}.
 */

public final class MockHexagonalReversi extends HexagonalReversi implements ReversiModel {

  private final StringBuilder log;
  private final int rows;

  /**
   * Constructs a new {@link MockHexagonalReversi} with the given log and number of rows.
   * @param log the log to append to
   * @param rows the number of rows
   */

  public MockHexagonalReversi(StringBuilder log, int rows) {
    super(rows);
    this.rows = rows;
    if (log == null) {
      throw new IllegalArgumentException("Log cannot be null.");
    }
    this.log = log;
  }

  /**
   * Constructs a new {@link MockHexagonalReversi} with the given log, number of rows,
   * and model. The model is copied.
   *
   * @param log the log to append to
   * @param rows the number of rows
   * @param model the model to copy
   */

  public MockHexagonalReversi(StringBuilder log, int rows, ReversiModel model) {
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
    String base = String.format("Checked (%d, %d) for %s.%n", row, col, pieceColor);
    this.log.append(base);
  }

  @Override
  public void pass(PieceColor color) throws IllegalStateException {
    this.log.append(String.format("Passed for %s.%n", color));
  }

  @Override
  public ReversiModel getModelCopy() {
    return new MockHexagonalReversi(log, rows, this);
  }
}
