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

package cs3500.reversi.view;

import java.util.List;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;

/**
 * Represents a textual view of a {@link ReadonlyReversiModel}. This view
 * displays the board as a grid of characters, with each character representing
 * the state of the cell at that position. The characters used are:
 * <ul>
 *   <li>{@code B} for a black piece</li>
 *   <li>{@code W} for a white piece</li>
 *   <li>{@code _} for an empty cell</li>
 * </ul>
 */

public final class TextualReversi implements TextualView {

  private final ReadonlyReversiModel model;

  /**
   * Constructs a new {@code TextualReversi} view with the given model.
   * The model is not copied, so any changes to the model will be reflected
   * in this view.
   *
   * @param model the read-only version of a model to display.
   */

  public TextualReversi(ReadonlyReversiModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    List<List<ReversiCellPair>> storedCells = model.getBoardCopy();
    for (int i = 0; i < storedCells.size(); i++) {
      int buffer = Math.abs(model.getRadius() - i);
      result.append(" ".repeat(buffer));
      for (int j = 0; j < storedCells.get(i).size(); j++) {
        result.append(model.getStateOf(i, j));
        if (j != storedCells.get(i).size() - 1) {
          result.append(" ");
        }
      }
      if (i != storedCells.size() - 1) {
        result.append("\n");
      }
    }
    String turn = model.getTurn() % 2 == 0 ? "Black" : "White";
    result.append("\n".repeat(2));
    result.append(String.format("Turn #%s (%s)\n", model.getTurn(), turn));
    result.append(String.format("Black Score: %s\n", model.getScore(PieceColor.BLACK)));
    result.append(String.format("White Score: %s\n", model.getScore(PieceColor.WHITE)));
    return result.toString();
  }

  @Override
  public void render() {
    System.out.print(this);
  }
}