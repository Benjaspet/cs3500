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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cs3500.reversi.model.types.Direction;
import cs3500.reversi.model.types.HexCell;
import cs3500.reversi.model.types.HexCellState;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;

/**
 * Represents a game of Reversi on a hexagonal board. The board is represented as a 2D array
 * of {@link Cell}s. The board is initialized with a number of rows, which determines the size
 * of the board. The board is always a hexagon, so the number of columns is determined by the
 * number of rows.
 *
 * <p>Fields:
 * <ul>
 *   <li> {@code radius}: the radius of the board, which is the number of rows divided by 2.</li>
 *   <li> {@code cells}: the 2D array of cells that represents the board.</li>
 *   <li> {@code states}: a map of cells to their states.</li>
 *   <li> {@code turn}: the current turn number.</li>
 *   <li> {@code passes}: the number of consecutive passes.</li>
 * </ul>
 *
 * <p>Class Invariants:
 * <ul>
 *   <li>The number of cells with pieces placed on them is equal to the score of white plus
 *   the score of black.</li>
 *   <li>The turn number is always greater than or equal to 1.</li>
 *   <li>The number of rows is always greater than or equal to 5, and is always odd.</li>
 *   <li>The number of empty cells is equal to the number of total cells minus the number
 *   of black and white cells.</li>
 *   <li>White always plays on on odd turns, and black always plays on even turns.</li>
 *   <li>When the number of turns passed is equal to 2, the game is immediately over.</li>
 * </ul>
 */

public class HexagonalReversi implements ReversiModel, ModelFeatures {

  private final List<ModelFeatures> features = new ArrayList<>();

  private List<List<ReversiCellPair>> cells = new ArrayList<>();
  private final int radius;
  private final int diameter;
  private int turn = 1;
  private int passes = 0;

  /**
   * Create a new hexagonal Reversi game.
   * @param rows the number of rows in the game.
   */

  public HexagonalReversi(int rows) {
    if (rows < 5 || rows % 2 == 0) {
      throw new IllegalArgumentException("Rows must be at least 5 and odd.");
    }
    this.radius = (int) Math.ceil((double) rows / 2) - 1;
    this.diameter = rows;
    this.initCells();
    this.initStartingColors();
  }

  /**
   * Create a copy of the provided {@link ReversiModel}.
   * @param model the model to copy.
   */

  public HexagonalReversi(ReversiModel model) {
    this.radius = model.getRadius();
    this.diameter = model.getDiameter();
    this.cells = model.getBoardCopy();
    this.turn = model.getTurn();
    this.passes = model.getPasses();
  }

  /**
   * Create a new hexagonal Reversi game with the default number of rows.
   * The default number of rows is 5, and it calls the other constructor with 5 as the argument.
   */

  public HexagonalReversi() {
    this(5);
  }

  @Override
  public void startGame() {
    this.notifyTurn();
  }

  @Override
  public void notifyTurn() {
    for (ModelFeatures listener : this.features) {
      listener.notifyTurn();
    }
  }

  @Override
  public void addFeatureListener(ModelFeatures features) {
    this.features.add(Objects.requireNonNull(features));
  }

  @Override
  public void playMove(int row, int col, PieceColor pieceColor)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.getPlayerColor().equals(pieceColor)) {
      throw new IllegalStateException("It is not " + pieceColor + "'s turn.");
    } else if (!this.isValidMove(row, col, pieceColor)) {
      String msg = "The move at (%s, %s) is invalid for %s.";
      throw new IllegalStateException(String.format(msg, row, col, pieceColor));
    } else if (this.isGameOver()) {
      throw new IllegalStateException("The game is over.");
    }
    for (Cell cell : this.getCellsThatWillBeFlipped(this.getCellAt(row, col), pieceColor)) {
      this.setState(cell, pieceColor);
    }
    this.setState(this.getCellAt(row, col), pieceColor);
    this.turn++;
    this.passes = 0;
    this.notifyTurn();
  }

  @Override
  public void pass(PieceColor pieceColor) throws IllegalStateException {
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over.");
    } else if (!this.getPlayerColor().equals(pieceColor)) {
      throw new IllegalStateException("It is not " + pieceColor + "'s turn.");
    }
    this.passes++;
    this.turn++;
    this.notifyTurn();
  }

  @Override
  public boolean isGameOver() {
    if (this.passes == 2) {
      return true;
    }
    for (int row = 0; row < cells.size(); row++) {
      for (int col = 0; col < cells.get(row).size(); col++) {
        if (this.isValidMove(row, col, PieceColor.BLACK)
                || this.isValidMove(row, col, PieceColor.WHITE)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isValidMove(int row, int col, PieceColor pieceColor)
          throws IllegalArgumentException {
    if (row < 0 || row >= this.cells.size() || col < 0 || col >= this.cells.get(row).size()) {
      throw new IllegalArgumentException("The provided location is not on the board.");
    }
    Cell cell = this.getCellAt(row, col);
    if (!this.getStateOf(cell).equals("_")) {
      return false;
    }
    return !this.getCellsThatWillBeFlipped(cell, pieceColor).isEmpty();
  }

  @Override
  public int getTurn() {
    return this.turn;
  }

  @Override
  public int getRadius() {
    return this.radius;
  }

  @Override
  public int getDiameter() {
    return this.diameter;
  }

  @Override
  public int getScore(PieceColor color) {
    int score = 0;
    for (int row = 0; row < cells.size(); row++) {
      for (int col = 0; col < cells.get(row).size(); col++) {
        String state = this.getStateOf(this.getCellAt(row, col));
        if (state.equals(color.toString())) {
          score++;
        }
      }
    }
    return score;
  }

  @Override
  public List<List<ReversiCellPair>> getBoardCopy() {
    List<List<ReversiCellPair>> copy = new ArrayList<>();
    for (List<ReversiCellPair> cell : this.cells) {
      copy.add(new ArrayList<>(cell));
    }
    return Collections.unmodifiableList(copy);
  }

  @Override
  public String getStateOf(Cell cell) throws IllegalArgumentException {
    for (List<ReversiCellPair> row : this.cells) {
      for (ReversiCellPair pair : row) {
        if (pair.getCell().equals(cell)) {
          return pair.getState();
        }
      }
    }
    throw new IllegalArgumentException("The provided cell is not on the board.");
  }

  @Override
  public String getStateOf(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.cells.size() || col < 0 || col >= this.cells.get(row).size()) {
      throw new IllegalArgumentException("The provided location is not on the board.");
    }
    return this.cells.get(row).get(col).getState();
  }

  @Override
  public List<Cell> getCellsThatWillBeFlipped(Cell cell, PieceColor pieceColor) {
    List<Cell> flipped = new ArrayList<>();
    for (int dir = 0; dir < 6; dir++) {
      Direction[] directions = Direction.values();
      List<Cell> cells = this.getCellsInDirection(cell, directions[dir]);
      for (int j = 0; j < cells.size(); j++) {
        String state = getStateOf(cells.get(j));
        if (state.equals("_")) {
          break;
        } else if (state.equals(pieceColor.toString()) && j == 0) {
          break;
        } else if (state.equals(pieceColor.toString())) {
          flipped.addAll(cells.subList(0, j));
          break;
        }
      }
    }
    return flipped;
  }

  @Override
  public PieceColor getPlayerColor() {
    return (turn % 2) == 1 ? PieceColor.WHITE : PieceColor.BLACK;
  }

  @Override
  public int getPasses() {
    return this.passes;
  }

  @Override
  public ReversiModel getModelCopy() {
    return new HexagonalReversi(this);
  }

  @Override
  public Optional<PieceColor> getWinner() throws IllegalStateException {
    if (!this.isGameOver()) {
      throw new IllegalStateException("The game is not over.");
    }
    int whiteScore = this.getScore(PieceColor.WHITE);
    int blackScore = this.getScore(PieceColor.BLACK);
    if (whiteScore > blackScore) {
      return Optional.of(PieceColor.WHITE);
    } else if (blackScore > whiteScore) {
      return Optional.of(PieceColor.BLACK);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Returns the cell at the provided row and column.
   *
   * @param row the row of the cell to get.
   * @param col the column of the cell to get.
   * @return the cell at the provided row and column.
   * @throws IllegalArgumentException if the provided row or column is out of bounds.
   */

  private Cell getCellAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.cells.size() || col < 0 || col >= this.cells.get(row).size()) {
      throw new IllegalArgumentException("The provided location is not on the board.");
    }
    return this.cells.get(row).get(col).getCell();
  }

  /**
   * Initializes the cells of the game board. This fills the internal list of
   * cells with {@link HexCell}s.
   */

  private void initCells() {
    for (int r = -radius; r <= radius; r++) {
      int q1 = Math.max(-radius, -r - radius);
      int q2 = Math.min(radius, -r + radius);
      List<ReversiCellPair> row = new ArrayList<>();
      for (int q = q1; q <= q2; q++) {
        ReversiCellPair cell = new ReversiCellPair(new HexCell(q, r), HexCellState.EMPTY);
        row.add(cell);
      }
      this.cells.add(row);
    }
  }

  /**
   * Initializes the starting colors of the game board. This sets the initial {@link HexCellState}
   * of the cells in the center of the board to the alternating colors of white and black, starting
   * with white in the top right corner.
   */

  private void initStartingColors() {
    int r = this.radius;
    this.setState(this.getCellAt(r, r - 1), PieceColor.WHITE);
    this.setState(this.getCellAt(r + 1, r - 1), PieceColor.BLACK);
    this.setState(this.getCellAt(r + 1, r), PieceColor.WHITE);
    this.setState(this.getCellAt(r, r + 1), PieceColor.BLACK);
    this.setState(this.getCellAt(r - 1, r), PieceColor.WHITE);
    this.setState(this.getCellAt(r - 1, r - 1), PieceColor.BLACK);
  }

  /**
   * Sets the state of the provided cell to the provided color.
   *
   * @param cell  the cell to set the state of.
   * @param color the color to set the state of the cell to.
   */

  private void setState(Cell cell, PieceColor color) {
    for (List<ReversiCellPair> row : this.cells) {
      for (int i = 0; i < row.size(); i++) {
        if (row.get(i).getCell().equals(cell)) {
          row.set(i, new ReversiCellPair(cell, color));
        }
      }
    }
  }

  /**
   * Returns the next cell in the provided direction. If the next cell is not on the board, null is
   * returned.
   *
   * @param cell      the cell to get the next cell of.
   * @param direction the direction to get the next cell in.
   * @return the next cell in the provided direction, or null if the next cell is not on the board.
   * @throws IllegalArgumentException if the provided cell is null
   */

  private Cell getNext(Cell cell, Direction direction) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    }
    Cell next = this.getDirections(cell).get(direction.ordinal());
    for (List<ReversiCellPair> row : this.cells) {
      for (ReversiCellPair pair : row) {
        if (pair.getCell().equals(next)) {
          return next;
        }
      }
    }
    return null;
  }

  /**
   * Fetch all the cells in the {@link Direction} of the provided {@link Cell}.
   *
   * @param cell      the cell for which the traversal begins.
   * @param direction the direction to retrieve cells.
   * @return a list of cells in the specified {@link Direction} of the provided {@link Cell}.
   * @throws IllegalArgumentException if the provided cell is not on the board, the
   *                                  direction is null, or the provided cell is null.
   */

  private List<Cell> getCellsInDirection(Cell cell, Direction direction)
          throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null.");
    }
    List<Cell> result = new ArrayList<>();
    Cell next = this.getNext(cell, direction);
    while (next != null) {
      result.add(next);
      next = this.getNext(next, direction);
    }
    return result;
  }

  /**
   * Returns a list of the neighbors of the provided cell.
   *
   * @param cell the cell to get the neighbors of.
   * @return the cells that neighbor the provided one.
   * @throws IllegalArgumentException if the provided cell is null.
   */

  private List<Cell> getDirections(Cell cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null.");
    }
    return new ArrayList<>(Arrays.asList(
            new HexCell(cell.getQ() + 1, cell.getR()), // East
            new HexCell(cell.getQ() + 1, cell.getR() - 1), // Northeast
            new HexCell(cell.getQ(), cell.getR() - 1), // Northwest
            new HexCell(cell.getQ() - 1, cell.getR()), // West
            new HexCell(cell.getQ() - 1, cell.getR() + 1), // Southwest
            new HexCell(cell.getQ(), cell.getR() + 1) // Southeast
    ));
  }
}