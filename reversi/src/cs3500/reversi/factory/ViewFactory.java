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

package cs3500.reversi.factory;

import cs3500.reversi.adapter.AIPlayerAdapter;
import cs3500.reversi.adapter.HumanPlayerAdapter;
import cs3500.reversi.adapter.ModelAdapter;
import cs3500.reversi.adapter.ViewAdapter;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ReversiGraphicsView;

/**
 * Represents a factory class for building {@link GraphicsView}s.
 * Provide a {@link ReversiModel} and {@link Player} to build a
 * {@link GraphicsView}.
 */

public final class ViewFactory {

  private final ModelAdapter model;
  private final Player player;
  private final boolean isProviderView;

  /**
   * Create a new instance of this view builder.
   * @param model the model to use.
   * @param player the player to use.
   */

  public ViewFactory(ModelAdapter model, Player player, boolean isProviderView) {
    this.model = model;
    this.player = player;
    this.isProviderView = isProviderView;
  }

  /**
   * Build a {@link GraphicsView} based on the provided constructor arguments.
   * @return the built {@link GraphicsView}.
   */

  public GraphicsView build() {
    GraphicsView view;
    if (this.isProviderView) {
      if (player.isAI() && player.getDifficulty().isPresent()) {
        view = new ViewAdapter(model, new AIPlayerAdapter(model, player.getPieceColor(),
                player.getDifficulty().get()));
      } else {
        view = new ViewAdapter(model, new HumanPlayerAdapter(model, player.getPieceColor()));
      }
    } else {
      view = new ReversiGraphicsView(model, player);
    }
    view.setVisible(true);
    return view;
  }
}
