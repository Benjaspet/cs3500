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
import cs3500.reversi.model.mock.MockAvoidNextToCornerReversi;
import cs3500.reversi.model.mock.MockGetCornerReversi;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.strategy.AvoidCellsNextToCorners;
import cs3500.reversi.strategy.ChooseCornerCell;
import cs3500.reversi.strategy.ReversiStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Represents a sample version of a controller test suite.
 */

public final class MockStrategyTests {
  private StringBuilder mockLog;
  private ReversiModel mockModel;

  @Before
  public void init() {
    this.mockLog = new StringBuilder();
  }

  private void initCorner() {
    this.mockModel = new MockGetCornerReversi(this.mockLog, 7);
  }

  private void initAvoidCorners() {
    this.mockModel = new MockAvoidNextToCornerReversi(this.mockLog, 7);
  }

  @Test
  public void testMockCornerCells() {
    initCorner();
    ReversiStrategy strat = new ChooseCornerCell();
    Optional<Point> move = strat.chooseMove(this.mockModel, PieceColor.WHITE);
    assertTrue(move.isPresent());
    Point topLeft = new Point(0, 0);
    assertEquals(move.get(), topLeft);
  }

  @Test
  public void testMockAvoidCorners() {
    initAvoidCorners();
    ReversiStrategy strat = new AvoidCellsNextToCorners();
    Optional<Point> move = strat.chooseMove(this.mockModel, PieceColor.WHITE);
    assertFalse(move.isPresent());
  }
}
