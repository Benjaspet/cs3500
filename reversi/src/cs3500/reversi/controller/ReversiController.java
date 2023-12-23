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

package cs3500.reversi.controller;

import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ViewFeatures;

/**
 * Represents a controller for a Reversi game.
 */

public class ReversiController implements ViewFeatures, ModelFeatures {

  private final ReversiModel model;
  private final Player player;
  private final GraphicsView view;

  /**
   * Constructs a new {@link ReversiController} using the provided model,
   * player, and view. It adds the controller as a feature listener to the
   * model, view, and players.
   *
   * @param model the model to use.
   * @param player the player to use.
   * @param view the view to use.
   */

  public ReversiController(ReversiModel model, Player player, GraphicsView view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
    this.model = model;
    this.player = player;
    this.view = view;
    this.view.addFeatureListener(this);
    this.model.addFeatureListener(this);
    this.player.addFeatures(this);
  }

  @Override
  public void passTurn() {
    try {
      this.model.pass(this.player.getPieceColor());
    } catch (IllegalStateException e) {
      this.view.notifyMessageDialog(e.getMessage());
    }
    this.view.refresh();
  }

  @Override
  public void makeMove(int row, int col) {
    try {
      this.model.playMove(row, col, this.player.getPieceColor());
    } catch (IllegalStateException | IllegalArgumentException e) {
      this.view.notifyMessageDialog(e.getMessage());
    }
    this.view.refresh();
  }

  @Override
  public void notifyTurn() {
    if (this.model.isGameOver()) {
      if (this.model.getWinner().isPresent()) {
        this.view.notifyMessageDialog(this.model.getWinner().get() + " won the game!");
      } else {
        this.view.notifyMessageDialog("The game ended in a tie!");
      }
      return;
    }
    this.view.notifyTurn(this.player);
    this.view.refresh();
  }
}