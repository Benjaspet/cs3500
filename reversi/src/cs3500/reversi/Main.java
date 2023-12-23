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

package cs3500.reversi;

import cs3500.reversi.adapter.ModelAdapter;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.factory.PlayerFactory;
import cs3500.reversi.factory.ViewFactory;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;

/**
 * Represents the main class to initialize a game of Reversi.
 */

public class Main {

  /**
   * The main method to create a game of Reversi, using command line arguments. These
   * are outlined in our README.md.
   *
   * @param args the command line arguments.
   */

  public static void main(String[] args) {

    ModelAdapter model;

    Player player1;
    Player player2;

    if (args.length == 0) {
      throw new IllegalArgumentException("Usage: java -jar Reversi.jar <number-of-rows> <p1> <p2>");
    }
    try {
      model = new ModelAdapter(Integer.parseInt(args[0]));
      player1 = new PlayerFactory(model, args[1], PieceColor.WHITE).build();
      player2 = new PlayerFactory(model, args[2], PieceColor.BLACK).build();
    } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Must provide an odd integer >= 5.");
    }
    GraphicsView viewPlayer1 = new ViewFactory(model, player1, false).build();
    GraphicsView viewPlayer2 = new ViewFactory(model, player2, true).build();
    ReversiController controller1 = new ReversiController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiController(model, player2, viewPlayer2);
    model.startGame();
  }
}