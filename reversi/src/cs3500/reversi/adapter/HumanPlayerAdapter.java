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

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.player.IPlayer;

/**
 * Adapts a {@link HumanPlayer} to an {@link IPlayer}. This class implements
 * {@link IPlayer} and extends {@link HumanPlayer}. It adapts the player by
 * converting the player's {@link PieceColor} to a {@link Piece}. It overrides
 * and updates the appropriate methods to make the provider's player interface
 * methods work with our player.
 */

public class HumanPlayerAdapter extends HumanPlayer implements IPlayer {

  /**
   * Constructs a new {@link HumanPlayerAdapter} with the given {@link ReversiModel}.
   * @param model the model to use.
   * @param color the color to use.
   */

  public HumanPlayerAdapter(ReversiModel model, PieceColor color) {
    super(model, color);
  }

  @Override
  public Piece getColor() {
    switch (super.getPieceColor()) {
      case BLACK:
        return Piece.BLACK;
      case WHITE:
        return Piece.WHITE;
      default:
        return Piece.EMPTY;
    }
  }

  @Override
  public String getName() {
    return "Human Player";
  }
}
