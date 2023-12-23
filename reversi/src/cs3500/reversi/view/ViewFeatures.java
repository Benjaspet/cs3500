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

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;

/**
 * Represents all the features that the {@link GraphicsView} can provide to the
 * {@link ReversiController}. This interface is used to decouple the view from the controller.
 */

public interface ViewFeatures {

  /**
   * Indicates that the current player wants to make a move. This feature is listened
   * to by the {@link ReversiController} and is called when the player presses the "enter"
   * or "return" key.
   *
   * @see ReversiController
   * @see ReversiModel#playMove(int, int, PieceColor)
   */

  void makeMove(int row, int col);

  /**
   * Indicates that the current player wants to pass their turn. This feature is listened
   * to by the {@link ReversiController} and is called when the player presses the "p"
   * key.
   *
   * @see ReversiController
   * @see ReversiModel#pass(PieceColor)
   */

  void passTurn();

}