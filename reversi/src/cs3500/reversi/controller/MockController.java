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

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;

/**
 * Represents a mock Reversi controller to test that it is being notified
 * by the view and model listeners correctly.
 */

public class MockController extends ReversiController {

  private final StringBuilder log;

  /**
   * Constructs a new {@link MockController} with the given {@link ReversiModel}.
   *
   * @param model the model to use.
   * @param player the player to use.
   * @param view the view to use.
   * @param log the log to use.
   */

  public MockController(ReversiModel model, Player player, GraphicsView view, StringBuilder log) {
    super(model, player, view);
    this.log = log;
  }

  @Override
  public void notifyTurn() {
    this.log.append("notifyTurn() was called in controller.\n");
  }
}