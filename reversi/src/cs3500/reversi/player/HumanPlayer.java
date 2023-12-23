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

import java.util.Optional;

import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.view.ViewFeatures;

/**
 * Represents a human player in a game of Reversi.
 */

public class HumanPlayer implements Player, ModelFeatures {

  private final PieceColor color;
  private final ReversiModel model;

  /**
   * Constructs a new {@link HumanPlayer} with the given model and piece color.
   * @param model the model to use.
   * @param pieceColor the color of the player.
   */

  public HumanPlayer(ReversiModel model, PieceColor pieceColor) {
    this.model = model;
    this.color = pieceColor;
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
  public boolean isAI() {
    return false;
  }

  @Override
  public void notifyTurn() {
     // Do nothing as this is a human player and does not need to be notified.
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    // Do nothing as this is a human player and does not need to be notified.
  }

  @Override
  public Optional<AIDifficulty> getDifficulty() {
    return Optional.empty();
  }
}
