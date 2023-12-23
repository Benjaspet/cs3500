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

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.controller.MockController;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ReversiGraphicsView;

import static org.junit.Assert.assertEquals;

/**
 * Represents test to ensure the controller is being properly notified of events
 * from the model.
 */

public class NotifyControllerTests {

  private ReversiModel model;
  private StringBuilder log;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.model = new HexagonalReversi(5);
    Player player1 = new HumanPlayer(this.model, PieceColor.WHITE);
    Player player2 = new HumanPlayer(this.model, PieceColor.BLACK);
    GraphicsView view1 = new ReversiGraphicsView(this.model, player1);
    GraphicsView view2 = new ReversiGraphicsView(this.model, player2);
    ReversiController controller1 = new MockController(this.model, player1, view1, log);
    ReversiController controller2 = new MockController(this.model, player2, view2, log);
  }

  @Test
  public void testControllerIsNotifiedFromModelIndicatingNewTurns() {
    this.model.startGame();
    this.model.pass(PieceColor.WHITE);
    this.model.pass(PieceColor.BLACK);
    String[] lines = this.log.toString().split("\n");
    assertEquals(lines[0], "notifyTurn() was called in controller.");
    assertEquals(lines[1], "notifyTurn() was called in controller.");
    assertEquals(lines[2], "notifyTurn() was called in controller.");
  }
}
