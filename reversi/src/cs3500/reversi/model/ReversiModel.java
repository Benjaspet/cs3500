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

package cs3500.reversi.model;

import cs3500.reversi.model.types.PieceColor;

/**
 * Represents a mutable Reversi game model. This model allows a player to make moves
 * and pass their turn. The game state is updated accordingly, and the game will
 * end when both players pass in a row.
 */

public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Plays a move at the specified row and column. This method allows a player to make a
   * move in the game by specifying the row and column of the cell where they want to place
   * their piece (per our coordinate system, see {@link HexagonalReversi}). The game
   * rules will be applied to check the validity of the move, and the game state
   * will be updated accordingly.
   *
   * @param row the row where the player wants to place their piece. Rows are
   *            numbered from 0 to n-1, where n is the number of rows in the game board.
   * @param col the column where the player wants to place their piece. Columns are
   *            numbered from 0 to n-1, where n is the number of columns in the game board.
   * @throws IllegalArgumentException if the row or column is out of bounds.
   * @throws IllegalStateException if the move is not allowed, if the cell at the given
   *                               coordinates is occupied, or it is not the provided
   *                               player's turn.
   * @see HexagonalReversi
   */

  void playMove(int row, int col, PieceColor pieceColor) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Proceed to the next turn. A {@link PieceColor} may pass if they have no valid moves to make,
   * or if they would simply prefer to not move. If both players pass in a row, the game is over.
   *
   * @param pieceColor the player who is passing.
   * @throws IllegalStateException if it is not the provided player's turn, or if the game is over.
   * @see HexagonalReversi
   */

  void pass(PieceColor pieceColor) throws IllegalStateException;

  /**
   * Adds a {@link ModelFeatures} to this model. This allows the model to notify the
   * features when the game state changes.
   *
   * @param features the features to add.
   */

  void addFeatureListener(ModelFeatures features);

  /**
   * Starts the game with the given players. This method will initialize the game state
   * and notify the features that the game has started.
   */

  void startGame();
}