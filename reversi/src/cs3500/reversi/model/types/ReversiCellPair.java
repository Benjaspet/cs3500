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

package cs3500.reversi.model.types;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.HexagonalReversi;

/**
 * Represents a mapping of a {@link HexCell} to a {@link HexCellState}.
 * This is used to represent the state of a {@link HexCell} in a
 * {@link HexagonalReversi}.
 */

public final class ReversiCellPair {

  private final Cell cell;
  private final HexCellState state;

  /**
   * Constructs a new {@link ReversiCellPair} with the given {@link Cell}
   * and {@link HexCellState}.
   *
   * @param cell the {@link Cell} of this pair.
   * @param state the state this {@link Cell} is mapped to.
   */

  public ReversiCellPair(Cell cell, HexCellState state) {
    this.cell = cell;
    this.state = state;
  }

  /**
   * Constructs a new {@link ReversiCellPair} with the given {@link Cell}
   * and {@link PieceColor}.
   *
   * @param cell the {@link Cell} of this pair.
   * @param state the state this {@link Cell} is mapped to.
   */

  public ReversiCellPair(Cell cell, PieceColor state) {
    this.cell = cell;
    this.state = state.equals(PieceColor.BLACK) ? HexCellState.BLACK : HexCellState.WHITE;
  }

  /**
   * Get the {@link Cell} of this pair.
   *
   * @return the {@link Cell} of this pair.
   */

  public Cell getCell() {
    return this.cell;
  }

  /**
   * Get the state this {@link Cell} is mapped to.
   *
   * @return the state this {@link Cell} is mapped to.
   */

  public String getState() {
    return this.state.toString();
  }
}
