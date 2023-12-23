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

import java.awt.Point;
import java.util.Optional;

import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.mock.MockHexagonalReversi;
import cs3500.reversi.model.mock.MockMultiValidMoveReversi;
import cs3500.reversi.model.mock.MockSingleValidMoveReversi;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.strategy.AvoidCellsNextToCorners;
import cs3500.reversi.strategy.ChooseCornerCell;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.FlipMostCells;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for full functionality of a hexagonal Reversi game, with {@link ReversiStrategy}
 * implementations.
 */
public final class FullStrategyTests {
  private StringBuilder mockLog;
  private ReversiModel mockModel;
  private ReversiModel mockMoveValidityModel;
  private ReversiModel mockMultiMoveValidityModel;

  @Before
  public void init() {
    this.mockLog = new StringBuilder();
    StringBuilder mockMultiMoveValidityLog = new StringBuilder();
    this.mockModel = new MockHexagonalReversi(this.mockLog, 7);
    this.mockMoveValidityModel = new MockSingleValidMoveReversi(this.mockLog, 7);
    this.mockMultiMoveValidityModel = new MockMultiValidMoveReversi(
            mockMultiMoveValidityLog, 7);

  }

  @Test
  public void testFlipMostCellsStrategyChecksAllValidMovesForBlack() {
    ReversiStrategy strategy = new FlipMostCells();
    this.mockModel.pass(PieceColor.WHITE);
    strategy.chooseMove(this.mockModel, PieceColor.BLACK);
    String log = this.mockLog.toString();
    assertTrue(log.contains("Checked (1, 2) for X."));
    assertTrue(log.contains("Checked (2, 1) for X."));
    assertTrue(log.contains("Checked (2, 4) for X."));
    assertTrue(log.contains("Checked (4, 1) for X."));
    assertTrue(log.contains("Checked (4, 4) for X."));
    assertTrue(log.contains("Checked (5, 2) for X."));
  }

  @Test
  public void testFlipMostCellsStrategyChecksAllValidMovesForWhite() {
    ReversiStrategy strategy = new FlipMostCells();
    strategy.chooseMove(this.mockModel, PieceColor.WHITE);
    String log = this.mockLog.toString();
    assertTrue(log.contains("Checked (1, 2) for O."));
    assertTrue(log.contains("Checked (2, 1) for O."));
    assertTrue(log.contains("Checked (2, 4) for O."));
    assertTrue(log.contains("Checked (4, 1) for O."));
    assertTrue(log.contains("Checked (4, 4) for O."));
    assertTrue(log.contains("Checked (5, 2) for O."));
  }

  @Test
  public void testFlipMostCellsStrategyReturnsOnlyValidMoveWhenLyingForBlack() {
    ReversiStrategy strategy = new FlipMostCells();
    this.mockMoveValidityModel.pass(PieceColor.WHITE);
    assertEquals(Optional.of(new Point(0, 2)),
            strategy.chooseMove(this.mockMoveValidityModel, PieceColor.BLACK));
  }

  @Test
  public void testFlipMostCellsStrategyReturnsOnlyValidMoveWhenLyingForWhite() {
    ReversiStrategy strategy = new FlipMostCells();
    assertEquals(Optional.of(new Point(0, 2)),
            strategy.chooseMove(this.mockMoveValidityModel, PieceColor.WHITE));
  }

  @Test
  public void testFlipMostCellsReturnsTopLeftOfMultipleValidMovesWhenLyingBlack() {
    ReversiStrategy strategy = new FlipMostCells();
    this.mockMultiMoveValidityModel.pass(PieceColor.WHITE);
    assertEquals(Optional.of(new Point(1, 2)),
            strategy.chooseMove(this.mockMultiMoveValidityModel, PieceColor.BLACK));
  }

  @Test
  public void testFlipMostCellsReturnsTopLeftOfMultipleValidMovesWhenLyingWhite() {
    ReversiStrategy strategy = new FlipMostCells();
    assertEquals(Optional.of(new Point(1, 2)),
            strategy.chooseMove(this.mockMultiMoveValidityModel, PieceColor.WHITE));
  }

  @Test
  public void testChooseCornerCellStrategyChecksAllSixCornersForBlack() {
    ReversiStrategy strategy = new ChooseCornerCell();
    this.mockModel.pass(PieceColor.WHITE);
    strategy.chooseMove(this.mockModel, PieceColor.BLACK);
    String log = this.mockLog.toString();
    System.out.print(log);
    assertTrue(log.contains("Checked (0, 0) for X."));
    assertTrue(log.contains("Checked (0, 3) for X."));
    assertTrue(log.contains("Checked (3, 0) for X."));
    assertTrue(log.contains("Checked (3, 6) for X."));
    assertTrue(log.contains("Checked (6, 0) for X."));
    assertTrue(log.contains("Checked (6, 3) for X."));
  }

  @Test
  public void testChooseCornerCellStrategyChecksAllSixCornersForWhite() {
    ReversiStrategy strategy = new ChooseCornerCell();
    strategy.chooseMove(this.mockModel, PieceColor.WHITE);
    String log = this.mockLog.toString();
    System.out.print(log);
    assertTrue(log.contains("Checked (0, 0) for O."));
    assertTrue(log.contains("Checked (0, 3) for O."));
    assertTrue(log.contains("Checked (3, 0) for O."));
    assertTrue(log.contains("Checked (3, 6) for O."));
    assertTrue(log.contains("Checked (6, 0) for O."));
    assertTrue(log.contains("Checked (6, 3) for O."));
  }

  @Test
  public void testChooseCornerCellStrategyWhenOnlyOneForcedMoveIsValid() {
    ReversiStrategy strategy = new ChooseCornerCell();
    this.mockMoveValidityModel.pass(PieceColor.WHITE);
    assertEquals(Optional.empty(),
            strategy.chooseMove(this.mockMoveValidityModel, PieceColor.BLACK));
  }

  @Test
  public void testAvoidCellsNextToCornersStrategyChecksAllCellsNotNextToCorners() {
    ReversiStrategy strategy = new AvoidCellsNextToCorners();
    this.mockModel.pass(PieceColor.WHITE);
    strategy.chooseMove(this.mockModel, PieceColor.BLACK);
    String log = this.mockLog.toString();
    assertTrue(log.contains("Checked (0, 0) for X."));
    assertTrue(log.contains("Checked (0, 3) for X."));
    assertTrue(log.contains("Checked (1, 4) for X."));
    assertTrue(log.contains("Checked (2, 4) for X."));
    assertTrue(log.contains("Checked (2, 5) for X."));
    assertTrue(log.contains("Checked (3, 0) for X."));
    assertTrue(log.contains("Checked (3, 3) for X."));
    assertTrue(log.contains("Checked (3, 4) for X."));
    assertTrue(log.contains("Checked (3, 5) for X."));
    assertTrue(log.contains("Checked (3, 6) for X."));
    assertTrue(log.contains("Checked (4, 0) for X."));
    assertTrue(log.contains("Checked (4, 1) for X."));
    assertTrue(log.contains("Checked (4, 2) for X."));
    assertTrue(log.contains("Checked (4, 3) for X."));
    assertTrue(log.contains("Checked (4, 4) for X."));
    assertTrue(log.contains("Checked (4, 5) for X."));
    assertTrue(log.contains("Checked (5, 2) for X."));
    assertTrue(log.contains("Checked (5, 3) for X."));
    assertTrue(log.contains("Checked (5, 4) for X."));
    assertTrue(log.contains("Checked (6, 0) for X."));
    assertTrue(log.contains("Checked (6, 2) for X."));
    assertTrue(log.contains("Checked (6, 3) for X."));
  }


  @Test
  public void testAvoidCellsNextToCornersStrategyWhenOnlyOneForcedMoveIsValid() {
    ReversiStrategy strategy = new AvoidCellsNextToCorners();
    this.mockMoveValidityModel.pass(PieceColor.WHITE);
    assertEquals(Optional.empty(),
            strategy.chooseMove(this.mockMoveValidityModel, PieceColor.BLACK));
  }
}