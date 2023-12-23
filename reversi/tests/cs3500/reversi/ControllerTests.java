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
import cs3500.reversi.model.mock.MockHexagonalReversi;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ReversiGraphicsView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represents test to ensure the controller is properly updating the model.
 */

public final class ControllerTests {

  private ReversiModel model;
  private StringBuilder log;
  private ReversiController controller1;
  private ReversiController controller2;
  private ReversiController controller3;
  private ReversiController controller4;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.model = new HexagonalReversi(7);
    ReversiModel mockModel = new MockHexagonalReversi(this.log, 5);
    Player player1 = new HumanPlayer(mockModel, PieceColor.WHITE);
    Player player2 = new HumanPlayer(mockModel, PieceColor.BLACK);
    Player player3 = new HumanPlayer(this.model, PieceColor.WHITE);
    Player player4 = new HumanPlayer(this.model, PieceColor.BLACK);
    GraphicsView view1 = new ReversiGraphicsView(mockModel, player1);
    GraphicsView view2 = new ReversiGraphicsView(mockModel, player2);
    GraphicsView view3 = new ReversiGraphicsView(this.model, player3);
    GraphicsView view4 = new ReversiGraphicsView(this.model, player4);
    this.controller1 = new ReversiController(mockModel, player1, view1);
    this.controller2 = new ReversiController(mockModel, player2, view2);
    this.controller3 = new ReversiController(this.model, player3, view3);
    this.controller4 = new ReversiController(this.model, player4, view4);
  }

  @Test
  public void testMultipleControllerMovesUpdatesGameState() {
    assertEquals(this.model.getBoardCopy().get(1).get(2).getState(), "_");
    assertEquals(this.model.getTurn(), 1);
    assertEquals(this.model.getScore(PieceColor.WHITE), 3);
    this.controller3.makeMove(1, 2);
    assertEquals(this.model.getBoardCopy().get(1).get(2).getState(), "O");
    assertEquals(this.model.getTurn(), 2);
    assertEquals(this.model.getScore(PieceColor.WHITE), 5);
    this.controller4.makeMove(4, 4);
    assertEquals(this.model.getBoardCopy().get(4).get(4).getState(), "X");
    assertEquals(this.model.getTurn(), 3);
    assertEquals(this.model.getScore(PieceColor.WHITE), 4);
  }

  @Test
  public void testSimpleControllerMovesWithMock() {
    this.controller1.makeMove(2, 1);
    assertTrue(this.log.toString().contains("(2, 1) for O."));
    this.controller2.makeMove(1, 3);
    assertTrue(this.log.toString().contains("(1, 3) for X."));
  }

  @Test
  public void testSimpleControllerPassesWithMock() {
    this.controller1.passTurn();
    assertTrue(this.log.toString().contains("Passed for O."));
    this.controller2.passTurn();
    assertTrue(this.log.toString().contains("Passed for X."));
  }
}
