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

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;

/**
 * Represents a mock player to test that it is being notified by the model.
 */

public class MockPlayer extends HumanPlayer {

  private StringBuilder log;

  /**
   * Constructs a new {@link MockPlayer} with the given model, piece color, and log.
   * @param model the model to use.
   * @param pieceColor the color of the player.
   * @param log the log to use.
   */

  public MockPlayer(ReversiModel model, PieceColor pieceColor, StringBuilder log) {
    super(model, pieceColor);
    this.log = log;
  }

  @Override
  public void notifyTurn() {
    this.log.append("notifyTurn() was called for player " + this.getPieceColor() + "\n");
  }
}
