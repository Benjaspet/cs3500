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

import cs3500.reversi.player.Player;

/**
 * Represents a generic view for a game of Reversi. A view can be notified when
 * it is a player's turn, and can be queried for information about itself. A
 * view can also be refreshed, which resets the selected cell.
 *
 * @see Player
 * @see ViewFeatures
 */

public interface GraphicsView {

  /**
   * Adds the given features to this view.
   *
   * @param features the features to add.
   * @see ViewFeatures
   */

  void addFeatureListener(ViewFeatures features);

  /**
   * Sets the visibility of this view.
   *
   * @param visible whether this view should be visible or not.
   */

  void setVisible(boolean visible);

  /**
   * Refreshes the view, resetting the selected cell and repainting
   * the updated game state onto the panel.
   */

  void refresh();

  /**
   * The method called when the controller indicates it is the provided
   * player's turn.
   *
   * @param player the player whose turn it is.
   */

  void notifyTurn(Player player);

  /**
   * Send a popup message to the user of this view. The controller calls this method
   * if a player makes an invalid move, or if the game is over, or if the player attempts
   * to move when it is not their turn.
   *
   * @param message the message to display.
   */

  void notifyMessageDialog(String message);

}