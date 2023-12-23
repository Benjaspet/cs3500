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

package cs3500.reversi.adapter;

import cs3500.reversi.model.types.HexCell;
import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.Piece;

/**
 * Adapts a {@link HexCell} to a {@link CellType}.
 */

public class HexCellAdapter implements CellType {

  private Piece piece;

  /**
   * Constructs a new {@link HexCellAdapter} with the given {@link Piece}.
   * @param piece the piece to use.
   */

  public HexCellAdapter(Piece piece) {
    this.piece = piece;
  }

  @Override
  public Piece getPiece() {
    return this.piece;
  }

  @Override
  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  @Override
  public void flipPiece() throws IllegalStateException {
    this.piece = this.piece.equals(Piece.BLACK) ? Piece.WHITE : Piece.BLACK;
  }

  @Override
  public String toString() {
    return this.piece.toString();
  }
}