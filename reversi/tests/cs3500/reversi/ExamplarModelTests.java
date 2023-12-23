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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;

/**
 * Represents a sample version of the functionality of a hexagonal Reversi game.
 */

public final class ExamplarModelTests {
  private ReversiModel model1;

  @Before
  public void init() {
    this.model1 = new HexagonalReversi(5);
  }

  @Test
  public void testValidMove() {
    this.model1.playMove(1, 0, PieceColor.WHITE);
    Assert.assertEquals(this.model1.getTurn(), 2);
  }

  @Test
  public void testInvalidMove() {
    Assert.assertThrows(
            IllegalStateException.class, () -> this.model1.playMove(0, 0, PieceColor.WHITE));
  }

  @Test
  public void testIsGameOver() {
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(1, 0, PieceColor.WHITE);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(0, 1, PieceColor.BLACK);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(1, 3, PieceColor.WHITE);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(3, 0, PieceColor.BLACK);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(4, 1, PieceColor.WHITE);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(3, 3, PieceColor.BLACK);
    Assert.assertTrue(this.model1.isGameOver());
  }

  @Test
  public void testConstructorExceptions() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(4));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(6));
  }
}
