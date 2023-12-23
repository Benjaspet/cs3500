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

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.MockPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ReversiGraphicsView;

import static org.junit.Assert.assertTrue;

/**
 * Represents test to ensure the players are being properly notified of events
 * from the controller.
 */

public final class PlayerListenerTests {

  private ReversiModel model;
  private StringBuilder log;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.model = new HexagonalReversi(5);
    Player mockPlayer1 = new MockPlayer(this.model, PieceColor.WHITE, this.log);
    Player mockPlayer2 = new MockPlayer(this.model, PieceColor.BLACK, this.log);
    GraphicsView view1 = new ReversiGraphicsView(this.model, mockPlayer1);
    GraphicsView view2 = new ReversiGraphicsView(this.model, mockPlayer2);
    ReversiController controller1 = new ReversiController(this.model, mockPlayer1, view1);
    ReversiController controller2 = new ReversiController(this.model, mockPlayer2, view2);
  }

  @Test
  public void testPlayerListenerNotifiesPlayerTurn() {
    this.model.startGame();
    assertTrue(this.log.toString().contains("notifyTurn() was called for player O"));
    assertTrue(this.log.toString().contains("notifyTurn() was called for player X"));
    this.model.pass(PieceColor.WHITE);
  }
}
