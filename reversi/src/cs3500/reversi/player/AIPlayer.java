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

package cs3500.reversi.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.adapter.ModelAdapter;
import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadOnlyReversiInterface;
import cs3500.reversi.provider.strategy.MaxCaptureStrategy;
import cs3500.reversi.strategy.AvoidCellsNextToCorners;
import cs3500.reversi.strategy.ChooseCornerCell;
import cs3500.reversi.strategy.FlipMostCells;
import cs3500.reversi.strategy.StrategyWithFallbacks;
import cs3500.reversi.view.ViewFeatures;

import static cs3500.reversi.adapter.ViewAdapter.getPoint;

/**
 * Represents an AI player in a game of Reversi.
 */

public class AIPlayer implements Player, ModelFeatures {

  private final ReversiModel model;
  private final PieceColor color;
  private final AIDifficulty difficulty;
  private final ArrayList<ViewFeatures> features = new ArrayList<>();

  /**
   * Construct an AI player.
   * @param model the model to use.
   * @param color the color of the player.
   * @param difficulty the difficulty of the AI.
   */

  public AIPlayer(ReversiModel model, PieceColor color, AIDifficulty difficulty) {
    this.model = model;
    this.color = color;
    this.difficulty = difficulty;
    this.model.addFeatureListener(this);
  }

  @Override
  public boolean isTurn() {
    return this.model.getPlayerColor() == this.getPieceColor();
  }

  @Override
  public PieceColor getPieceColor() {
    return this.color;
  }

  @Override
  public void notifyTurn() {
    if (!this.isTurn()) {
      return;
    }
    Optional<Point> move = this.getMove(this.model);
    for (ViewFeatures feature : this.features) {
      if (move.isPresent()) {
        feature.makeMove(move.get().x, move.get().y);
      } else {
        feature.passTurn();
      }
    }
  }

  @Override
  public void addFeatures(ViewFeatures feature) {
    this.features.add(feature);
  }

  @Override
  public boolean isAI() {
    return true;
  }

  /**
   * Returns the move that the AI should make. This is based on the
   * provided difficulty level when the player is created.
   *
   * @param model the model to get the move for.
   * @return the move that the AI should make.
   */

  private Optional<Point> getMove(ReversiModel model) {
    try {
      switch (this.difficulty) {
        case EASY:
          return new FlipMostCells().chooseMove(model, this.color);
        case MEDIUM:
          return new StrategyWithFallbacks(
                  new ChooseCornerCell(),
                  new FlipMostCells()
          ).chooseMove(model, this.color);
        case HARD:
          return new StrategyWithFallbacks(
                  new AvoidCellsNextToCorners(),
                  new FlipMostCells()
          ).chooseMove(model, this.color);
        case PROVIDER:
          ReadOnlyReversiInterface modelAdapter = new ModelAdapter(model);
          Piece adapted = this.adaptPieceColorToPiece(this.color);
          Optional<AxialCoordinate> move = new MaxCaptureStrategy().
                  chooseMove(modelAdapter, adapted);
          if (move.isPresent()) {
            return Optional.of(this.adaptAxialCoordinate(move.get()));
          }
          return Optional.empty();
        default:
          throw new IllegalArgumentException("Invalid difficulty.");
      }
    } catch (IllegalStateException e) {
      return Optional.empty();
    }
  }

  private Piece adaptPieceColorToPiece(PieceColor piece) {
    if (piece == PieceColor.BLACK) {
      return Piece.BLACK;
    }
    return Piece.WHITE;
  }

  @Override
  public Optional<AIDifficulty> getDifficulty() {
    return Optional.ofNullable(this.difficulty);
  }

  private Point adaptAxialCoordinate(AxialCoordinate coord) {
    List<List<ReversiCellPair>> board = model.getBoardCopy();
    return getPoint(coord, board);
  }
}
