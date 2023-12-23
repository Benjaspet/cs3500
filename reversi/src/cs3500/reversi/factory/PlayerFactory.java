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
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.AIDifficulty;
import cs3500.reversi.player.Player;

/**
 * Represents a factor class for building {@link Player}s. Provide a
 * {@link ReversiModel}, {@link PieceColor}, and {@link String} to build a
 * {@link Player}.
 */

public final class PlayerFactory {

  private final ReversiModel model;
  private final PieceColor color;
  private final String type;

  /**
   * Create a new instance of this player builder.
   * @param model the model to use.
   * @param type the type of player to build. Can be one of "human",
   *             "easyai", "mediumai", or "hardai".
   * @param color the color of the player to build.
   */

  public PlayerFactory(ReversiModel model, String type, PieceColor color) {
    this.model = model;
    this.type = type;
    this.color = color;
  }

  /**
   * Build a {@link Player} based on the provided constructor arguments.
   * @return the built {@link Player}.
   */

  public Player build() {
    switch (this.type.toLowerCase()) {
      case "human":
        return new HumanPlayerAdapter(this.model, this.color);
      case "easyai":
        return new AIPlayerAdapter(this.model, this.color, AIDifficulty.EASY);
      case "hardai":
        return new AIPlayerAdapter(this.model, this.color, AIDifficulty.HARD);
      case "providerai":
        return new AIPlayerAdapter(this.model, this.color, AIDifficulty.PROVIDER);
      default:
        return new AIPlayerAdapter(this.model, this.color, AIDifficulty.MEDIUM);
    }
  }
}