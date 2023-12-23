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
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.view.ViewFeatures;

/**
 * Represents a generic player in a game of Reversi. A player can be notified
 * when it is their turn, and can be queried for information about itself.
 */

public interface Player extends ModelFeatures {

  /**
   * Returns whether it is this player's turn.
   * @return whether it is this player's turn
   */

  boolean isTurn();

  /**
   * Returns the color of this player's pieces.
   * @return the color of this player's pieces.
   */

  PieceColor getPieceColor();

  /**
   * Returns whether this player is an AI.
   * @return whether this player is an AI
   */

  boolean isAI();

  /**
   * Notifies this player that it is their turn. This method should be called
   * by the controller when the model notifies it that it is this player's turn.
   */

  void notifyTurn();

  /**
   * Adds the given view features to this player. This method should be called
   * by the controller when the view is created.
   *
   * @param features the view features to add and be listened to.
   */

  void addFeatures(ViewFeatures features);

  Optional<AIDifficulty> getDifficulty();

}
