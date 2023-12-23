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

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.Cell;
import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.ModelListener;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ProviderReversiModel;

import static cs3500.reversi.adapter.ViewAdapter.getPoint;

/**
 * Adapts a {@link ReversiModel} to a {@link ProviderReversiModel}. This class
 * implements {@link ProviderReversiModel} and extends {@link HexagonalReversi}.
 * It adapts the model by converting the model's {@link Cell} to a
 * {@link CellType} and converting the model's {@link PieceColor} to a
 * {@link Piece}. It overrides and updates the appropriate methods to make the provider's
 * model interface methods work with our model.
 */

public class ModelAdapter extends HexagonalReversi implements ProviderReversiModel, ModelListener {

  /**
   * Constructs a new {@link ModelAdapter} using the provided number of rows.
   * @param numRows the number of rows.
   */

  public ModelAdapter(int numRows) {
    super(numRows);
  }

  /**
   * Constructs a new {@link ModelAdapter} using the provided {@link ReversiModel}.
   * @param model the model to use.
   */

  public ModelAdapter(ReversiModel model) {
    super(model);
  }

  @Override
  public void startGame() {
    super.startGame();
  }

  @Override
  public void passTurn() {
    super.pass(super.getPlayerColor());
  }

  @Override
  public void placePiece(Piece piece, AxialCoordinate coord) {
    Point rowCol = this.adaptAxialCoordinate(coord);
    PieceColor color = this.adaptPieceToPieceColor(piece);
    super.playMove(rowCol.x, rowCol.y, color);
  }

  @Override
  public void notifyStatus() {
    super.notifyTurn();
  }

  @Override
  public void notifyObservers() {
    super.notifyTurn();
  }

  @Override
  public void addObserver(ModelListener listener) {
    // Do nothing here, as model feature listening is handled by our model implementation.
  }

  @Override
  public boolean isValidMove(Piece piece, AxialCoordinate coord) {
    PieceColor color = this.adaptPieceToPieceColor(piece);
    Point rowCol = this.adaptAxialCoordinate(coord);
    return super.isValidMove(rowCol.x, rowCol.y, color);
  }

  @Override
  public boolean anyValidMoves(Piece piece) {
    PieceColor color = this.adaptPieceToPieceColor(piece);
    for (int i = 0; i < super.getBoardCopy().size(); i++) {
      for (int j = 0; j < super.getBoardCopy().get(i).size(); j++) {
        if (super.isValidMove(i, j, color)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Map<AxialCoordinate, CellType> getProviderBoard() {
    Map<AxialCoordinate, CellType> board = new HashMap<>();
    List<List<ReversiCellPair>> boardCopy = super.getBoardCopy();
    for (List<ReversiCellPair> reversiCellPairs : boardCopy) {
      for (ReversiCellPair reversiCellPair : reversiCellPairs) {
        Cell currCell = reversiCellPair.getCell();
        AxialCoordinate coord = new AxialCoordinate(currCell.getQ(), currCell.getR());
        switch (reversiCellPair.getState()) {
          case "X":
            board.put(coord, new HexCellAdapter(Piece.BLACK));
            break;
          case "O":
            board.put(coord, new HexCellAdapter(Piece.WHITE));
            break;
          case "_":
            board.put(coord, new HexCellAdapter(Piece.EMPTY));
            break;
          default:
            throw new IllegalArgumentException("Invalid cell type.");
        }
      }
    }
    return board;
  }

  @Override
  public CellType getHexagonAt(AxialCoordinate hex) {
    return this.getProviderBoard().get(hex);
  }

  @Override
  public int getScore(Piece player) {
    return super.getScore(this.adaptPieceToPieceColor(player));
  }

  @Override
  public boolean isBlackTurn() {
    return super.getPlayerColor() == PieceColor.BLACK;
  }

  @Override
  public Piece getCurrentTurn() {
    PieceColor curr = super.getPlayerColor();
    if (curr == PieceColor.BLACK) {
      return Piece.BLACK;
    }
    return Piece.WHITE;
  }

  @Override
  public Optional<Piece> getGameWinner() {
    if (super.isGameOver()) {
      if (super.getWinner().isPresent()) {
        if (super.getWinner().get() == PieceColor.BLACK) {
          return Optional.of(Piece.BLACK);
        }
        return Optional.of(Piece.WHITE);
      }
    }
    return Optional.empty();
  }

  /**
   * Adapts a piece to a piece color.
   * @param piece the piece to adapt.
   * @return the piece color.
   */

  private PieceColor adaptPieceToPieceColor(Piece piece) {
    if (piece == Piece.BLACK) {
      return PieceColor.BLACK;
    }
    return PieceColor.WHITE;
  }

  /**
   * Adapts an axial coordinate to a row, column coordinate.
   * @param coord the axial coordinate to adapt.
   * @return the row, column coordinate.
   */

  private Point adaptAxialCoordinate(AxialCoordinate coord) {
    List<List<ReversiCellPair>> board = super.getBoardCopy();
    return getPoint(coord, board);
  }
}