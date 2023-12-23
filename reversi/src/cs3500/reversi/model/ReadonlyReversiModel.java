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

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.types.HexCellState;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.view.ReversiGraphicsView;

/**
 * Represents a read-only version of a Reversi model.
 */

public interface ReadonlyReversiModel {

  /**
   * Determines whether the game is over. The game is over in two cases:
   *
   * <li>Both players have passed consecutively (i.e. 2 passes in a row).</li>
   * <li>There are no valid moves for either player to make.</li>
   *
   * @return true if the game is over, false otherwise.
   */

  boolean isGameOver();

  /**
   * Determines if the provided move is valid. A move is valid if the following
   * conditions are met:
   *
   * <li>The cell at the given location is empty.</li>
   * <li>The cell at the given location is adjacent to a cell of the opposite color.</li>
   * <li>There exists a cell of the same color as the player's color in the same direction
   * as the adjacent cell, with no empty cells in between.</li>
   *
   * @param row the row of the move.
   * @param col the column of the move.
   * @return true if the move is valid, false otherwise.
   * @throws IllegalArgumentException if the row or column is out of bounds.
   */

  boolean isValidMove(int row, int col, PieceColor pieceColor) throws IllegalArgumentException;

  /**
   * Get the current turn in the game. Turns are 1-indexed, i.e. the first
   * turn is turn 1. The second turn will be black, and so on. If a player
   * has no valid moves to make, then they must pass their turn.
   * Player white will always start first. If the turn number is odd, it is
   * white's turn. Otherwise, it is black's turn.
   *
   * @return the current turn number.
   * @see HexCellState
   */

  int getTurn();

  /**
   * Get the radius of the game board.
   * We have defined the radius as the number of spaces directly to the right of the
   * center of the board. For example, a board with a radius of 2 would have a max of
   * five columns, and five rows.
   *
   * @return the radius of the game board.
   */

  int getRadius();

  /**
   * Gets the diameter of the board. The diameter is defined as the number of cells
   * in the longest row of the board. This method is used in our {@link ReversiGraphicsView}
   * to automatically adjust the size of the window to accommodate for the size of the board.
   *
   * @return the diameter of the board.
   */

  int getDiameter();

  /**
   * Calculates the score of the provided {@link PieceColor}.
   * The score of a player represents the amount of {@link Cell}s that player has
   * on the game board.
   *
   * @param pieceColor the player to calculate the score of.
   * @return the score of the provided {@link PieceColor}.
   */

  int getScore(PieceColor pieceColor);

  /**
   * Get all the cells in the game board.
   * The game board is stored as a 2D list of cells, with the origin <code>(0, 0)</code>
   * being the top leftmost cell. The first dimension represents the rows, and the second
   * dimension represents the columns. Our reasoning for using <code>(row, col)</code>
   * coordinates is outlined in our README.md. This method is used in our strategies to
   * determine the best move to make, and it is used in our view to render the board.
   *
   * @return the cells in the game board, each mapped to its {@link HexCellState}.
   * @apiNote the list of cells returned <i>should not</i> be modified.
   * @see ReversiCellPair
   */

  List<List<ReversiCellPair>> getBoardCopy();

  /**
   * Get the {@link HexCellState} of the provided cell.
   * This is used in our view to determine which piece is at a given cell.
   *
   * @param cell the cell to get the state of.
   * @return the {@link HexCellState} of the provided cell, rendered as a string
   *                                  to avoid exposing classes to the interface.
   * @throws IllegalArgumentException if the cell is non-existent on the board.
   *
   * @see HexCellState
   * @see ReadonlyReversiModel#getStateOf(int, int)
   */

  String getStateOf(Cell cell) throws IllegalArgumentException;

  /**
   * Get the {@link HexCellState} of the cell at the provided row and column.
   * This is used in our view to determine which piece is at a given coordinate.
   *
   * @param row the row of the cell to get the state of.
   * @param col the column of the cell to get the state of.
   * @return the {@link HexCellState} of the cell at the provided row and
   *         column, rendered as a string to avoid exposing classes to the interface.
   * @throws IllegalArgumentException if the cell is non-existent on the board,
   *                                  or the row or column is out of bounds.
   *
   * @see HexCellState
   * @see ReadonlyReversiModel#getStateOf(Cell)
   */

  String getStateOf(int row, int col) throws IllegalArgumentException;

  /**
   * Returns a list of cells that would be flipped to the current player's color if the provided
   * player were to move at the provided cell. If the returned list is empty, the provided cell is
   * not a valid move for the current {@link PieceColor}.
   *
   * @param cell the cell to check for valid moves via its neighbors.
   * @return a list of cells that would be flipped to the current player's color if the provided.
   * @see Cell
   */

  List<Cell> getCellsThatWillBeFlipped(Cell cell, PieceColor pieceColor);

  /**
   * Get the {@link PieceColor} whose turn it is. This method is used in our view
   * to determine which player's turn it is, and to see if a move is valid for the
   * current player when a cell is hovered over by the mouse.
   *
   * @return the {@link PieceColor} whose turn it is.
   * @see ReversiGraphicsView
   */

  PieceColor getPlayerColor();

  /**
   * Gets the number of passes that have occurred in a row. This is used in our
   * mock models and strategies to handle mock moves.
   *
   * @return the number of passes that have occurred in a row.
   */

  int getPasses();

  /**
   * Gets a copy of the model. This is used in our mock models and strategies to
   * handle mock moves.
   *
   * @return a copy of the model.
   */

  ReversiModel getModelCopy();

  /**
   * Gets the winner of this game.
   *
   * @return the piece color of the game winner.
   * @throws IllegalStateException if the game is not over.
   */

  Optional<PieceColor> getWinner() throws IllegalStateException;

}