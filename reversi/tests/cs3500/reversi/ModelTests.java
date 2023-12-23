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

import cs3500.reversi.model.types.HexCell;
import cs3500.reversi.model.types.HexCellState;
import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests the model for a game of Reversi.
 */
public final class ModelTests {
  private ReversiModel model1;
  private ReversiModel model2;

  @Before
  public void init() {
    this.model1 = new HexagonalReversi(5);
    this.model2 = new HexagonalReversi(7);
  }

  @Test
  public void hexagonalReversiConstructorExceptions() {
    assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(4));
    assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(3));
    assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(0));
    assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(-2));
    assertThrows(IllegalArgumentException.class, () -> new HexagonalReversi(6));
  }

  @Test
  public void testValidMoves() {
    this.model1.playMove(0, 1, PieceColor.WHITE);
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(1, 3, PieceColor.WHITE);
    Assert.assertEquals(4, this.model1.getTurn());
  }

  @Test
  public void testValidMoveButInvalidPlayerColor() {
    Assert.assertThrows(
            IllegalStateException.class, () -> this.model1.playMove(0, 1, PieceColor.BLACK));
    this.model1.playMove(0, 1, PieceColor.WHITE);
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(1, 3, PieceColor.WHITE);
  }

  @Test
  public void testValidMoveFlippingOnlyOneCell() {
    assertTrue(this.model1.isValidMove(0, 1, PieceColor.WHITE));
    this.model1.playMove(0, 1, PieceColor.WHITE);
  }

  @Test
  public void testValidMoveFlippingInMultipleDirections() {
    this.model1.playMove(0, 1, PieceColor.WHITE);
    assertTrue(this.model1.isValidMove(3, 0, PieceColor.WHITE));
    this.model1.playMove(1, 0, PieceColor.BLACK);
  }

  @Test
  public void testIsValidMoveExceptions() {
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.isValidMove(-1, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.isValidMove(0, -1, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.isValidMove(5, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.isValidMove(0, 5, PieceColor.WHITE));
  }

  @Test
  public void testInvalidMoveWithNoNeighboringCells() {
    assertFalse(this.model2.isValidMove(0, 0, PieceColor.WHITE));
    assertFalse(this.model2.isValidMove(6, 3, PieceColor.WHITE));
    assertFalse(this.model2.isValidMove(3, 0, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(0, 0, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(6, 3, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(3, 0, PieceColor.WHITE));
  }

  @Test
  public void testInvalidMoveWithValidMoveButWrongColor() {
    assertFalse(this.model2.isValidMove(0, 1, PieceColor.BLACK));
  }

  @Test
  public void testInvalidMoveWithRowAndColumnOutOfBounds() {
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.isValidMove(-1, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.isValidMove(0, -1, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.isValidMove(7, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.isValidMove(0, 7, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.playMove(-1, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.playMove(0, -1, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.playMove(7, 0, PieceColor.WHITE));
    assertThrows(
            IllegalArgumentException.class, () -> this.model2.playMove(0, 7, PieceColor.WHITE));
  }

  @Test
  public void testPassing() {
    this.model1.pass(PieceColor.WHITE);
    this.model1.playMove(0, 1, PieceColor.BLACK);
    Assert.assertTrue(this.model1.isValidMove(3, 0, PieceColor.WHITE));
  }

  @Test
  public void testInvalidMoveWithNoOppositeNeighboringCellsBothColors() {
    assertFalse(this.model2.isValidMove(3, 1, PieceColor.WHITE));
    assertFalse(this.model2.isValidMove(1, 3, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(3, 1, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(1, 3, PieceColor.WHITE));
    this.model2.pass(PieceColor.WHITE);
    assertFalse(this.model2.isValidMove(5, 1, PieceColor.BLACK));
    assertFalse(this.model2.isValidMove(3, 5, PieceColor.BLACK));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(5, 1, PieceColor.BLACK));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(3, 5, PieceColor.BLACK));
  }

  @Test
  public void testInvalidMoveWithOppositeNeighborsButNoSameCellInSaidDirection() {
    assertFalse(this.model2.isValidMove(3, 3, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(3, 3, PieceColor.WHITE));
    this.model2.playMove(1, 2, PieceColor.WHITE);
    assertFalse(this.model2.isValidMove(4, 1, PieceColor.BLACK));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(4, 1, PieceColor.BLACK));
  }

  @Test
  public void testInvalidMoveWithCellAlreadyOccupied() {
    this.model2.playMove(1, 2, PieceColor.WHITE);
    assertFalse(this.model2.isValidMove(1, 2, PieceColor.BLACK));
    assertFalse(this.model2.isValidMove(3, 4, PieceColor.BLACK));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(1, 2, PieceColor.WHITE));
    assertThrows(IllegalStateException.class, () -> this.model2.playMove(3, 4, PieceColor.WHITE));
  }

  @Test
  public void testPassResetsOnValidMove() {
    this.model1.pass(PieceColor.WHITE);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.pass(PieceColor.WHITE);
    Assert.assertFalse(this.model1.isGameOver());
    this.model1.pass(PieceColor.BLACK);
    assertTrue(this.model1.isGameOver());
  }

  @Test
  public void testCannotPassSamePlayer() {
    this.model1.pass(PieceColor.WHITE);
    assertThrows(IllegalStateException.class, () -> this.model1.pass(PieceColor.WHITE));
  }

  @Test
  public void testIsGameOverWithNoMovesMadeYet() {
    assertFalse(this.model1.isGameOver());
  }

  @Test
  public void testIsGameOverWithTwoConsecutivePasses() {
    assertFalse(this.model1.isGameOver());
    this.model1.pass(PieceColor.WHITE);
    this.model1.pass(PieceColor.BLACK);
    assertTrue(this.model1.isGameOver());
  }

  @Test
  public void testIsGameOverWithOneValidMoveRemaining() {
    assertFalse(this.model1.isGameOver());
    this.model1.playMove(0, 1, PieceColor.WHITE);
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(3, 0, PieceColor.WHITE);
    this.model1.playMove(1, 3, PieceColor.BLACK);
    this.model1.playMove(3, 3, PieceColor.WHITE);
    assertFalse(this.model1.isGameOver());
  }

  @Test
  public void testIsGameOverWithNoMoreValidMoves() {
    assertFalse(this.model1.isGameOver());
    this.model1.playMove(0, 1, PieceColor.WHITE);
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(3, 0, PieceColor.WHITE);
    this.model1.playMove(1, 3, PieceColor.BLACK);
    this.model1.playMove(3, 3, PieceColor.WHITE);
    this.model1.playMove(4, 1, PieceColor.BLACK);
    assertTrue(this.model1.isGameOver());
  }

  @Test
  public void testGetTurn() {
    assertEquals(1, this.model1.getTurn());
    this.model1.playMove(0, 1, PieceColor.WHITE);
    assertEquals(2, this.model1.getTurn());
    this.model1.playMove(1, 0, PieceColor.BLACK);
    assertEquals(3, this.model1.getTurn());
    this.model1.pass(PieceColor.WHITE);
    assertEquals(4, this.model1.getTurn());
  }

  @Test
  public void testGetRadius() {
    assertEquals(2, this.model1.getRadius());
    assertEquals(3, this.model2.getRadius());
  }

  @Test
  public void testGetScoreOnDefaultBoard() {
    assertEquals(3, this.model1.getScore(PieceColor.BLACK));
    assertEquals(3, this.model1.getScore(PieceColor.WHITE));
  }

  @Test
  public void testGetScoreWhenMovesAreMade() {
    this.model1.playMove(0, 1, PieceColor.WHITE);
    assertEquals(5, this.model1.getScore(PieceColor.WHITE));
    assertEquals(2, this.model1.getScore(PieceColor.BLACK));
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(1, 3, PieceColor.WHITE);
    assertEquals(6, this.model1.getScore(PieceColor.WHITE));
    assertEquals(3, this.model1.getScore(PieceColor.BLACK));
  }

  @Test
  public void testGetCells() {
    assertEquals(5, this.model1.getBoardCopy().size());
    assertEquals(7, this.model2.getBoardCopy().size());
    assertEquals(3, this.model1.getBoardCopy().get(0).size());
    assertEquals(4, this.model2.getBoardCopy().get(0).size());
  }

  @Test
  public void testGetStateOf() {
    assertEquals("O", this.model1.getStateOf(new HexCell(-1, 0)));
    assertEquals("X", this.model1.getStateOf(new HexCell(0, -1)));
    assertEquals("O", this.model1.getStateOf(new HexCell(1, -1)));
    assertEquals("X", this.model1.getStateOf(new HexCell(1, 0)));
    assertEquals("O", this.model1.getStateOf(new HexCell(0, 1)));
    assertEquals("X", this.model1.getStateOf(new HexCell(-1, 1)));
  }

  @Test
  public void testGetStateOfInvalidCell() {
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.getStateOf(null));
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.getStateOf(new HexCell(2, -16)));
    assertThrows(
            IllegalArgumentException.class, () -> this.model1.getStateOf(new HexCell(2, 16)));
  }

  @Test
  public void testReversiPlayerToString() {
    assertEquals("X", PieceColor.BLACK.toString());
    assertEquals("O", PieceColor.WHITE.toString());
  }

  @Test
  public void testReversiCellStateToString() {
    assertEquals("X", HexCellState.BLACK.toString());
    assertEquals("O", HexCellState.WHITE.toString());
    assertEquals("_", HexCellState.EMPTY.toString());
  }

  @Test
  public void testReversiCellGetQ() {
    assertEquals(0, new HexCell(0, 0).getQ());
    assertEquals(1, new HexCell(1, 0).getQ());
    assertEquals(2, new HexCell(2, 0).getQ());
    assertEquals(0, new HexCell(0, 1).getQ());
    assertEquals(1, new HexCell(1, 1).getQ());
    assertEquals(2, new HexCell(2, 1).getQ());
    assertEquals(0, new HexCell(0, 2).getQ());
    assertEquals(1, new HexCell(1, 2).getQ());
    assertEquals(2, new HexCell(2, 2).getQ());
  }

  @Test
  public void testReversiCellGetR() {
    assertEquals(0, new HexCell(0, 0).getR());
    assertEquals(0, new HexCell(1, 0).getR());
    assertEquals(0, new HexCell(2, 0).getR());
    assertEquals(1, new HexCell(0, 1).getR());
    assertEquals(1, new HexCell(1, 1).getR());
    assertEquals(1, new HexCell(2, 1).getR());
    assertEquals(2, new HexCell(0, 2).getR());
    assertEquals(2, new HexCell(1, 2).getR());
    assertEquals(2, new HexCell(2, 2).getR());
  }

  @Test
  public void testReversiCellGetS() {
    assertEquals(0, new HexCell(0, 0).getS());
    assertEquals(-1, new HexCell(1, 0).getS());
    assertEquals(-2, new HexCell(2, 0).getS());
    assertEquals(-1, new HexCell(0, 1).getS());
    assertEquals(-2, new HexCell(1, 1).getS());
  }

  @Test
  public void testReversiCellEquals() {
    assertEquals(new HexCell(0, 0), new HexCell(0, 0));
    assertEquals(new HexCell(1, 0), new HexCell(1, 0));
    assertEquals(new HexCell(2, 0), new HexCell(2, 0));
    assertEquals(new HexCell(0, 1), new HexCell(0, 1));
    assertEquals(new HexCell(1, 1), new HexCell(1, 1));
    assertEquals(new HexCell(2, 1), new HexCell(2, 1));
    assertEquals(new HexCell(0, 2), new HexCell(0, 2));
    assertEquals(new HexCell(1, 2), new HexCell(1, 2));
    assertEquals(new HexCell(2, 2), new HexCell(2, 2));
  }

  @Test
  public void testReversiCellHashCode() {
    assertEquals(new HexCell(0, 0).hashCode(), new HexCell(0, 0).hashCode());
    assertEquals(new HexCell(1, 0).hashCode(), new HexCell(1, 0).hashCode());
    assertEquals(new HexCell(2, 0).hashCode(), new HexCell(2, 0).hashCode());
    assertEquals(new HexCell(0, 1).hashCode(), new HexCell(0, 1).hashCode());
    assertEquals(new HexCell(1, 1).hashCode(), new HexCell(1, 1).hashCode());
    assertEquals(new HexCell(2, 1).hashCode(), new HexCell(2, 1).hashCode());
    assertEquals(new HexCell(0, 2).hashCode(), new HexCell(0, 2).hashCode());
    assertEquals(new HexCell(1, 2).hashCode(), new HexCell(1, 2).hashCode());
    assertEquals(new HexCell(2, 2).hashCode(), new HexCell(2, 2).hashCode());
  }

  @Test
  public void testReversiCellToString() {
    assertEquals("(0, 0, 0)", new HexCell(0, 0).toString());
    assertEquals("(1, 0, -1)", new HexCell(1, 0).toString());
    assertEquals("(2, 0, -2)", new HexCell(2, 0).toString());
    assertEquals("(0, 1, -1)", new HexCell(0, 1).toString());
    assertEquals("(1, 1, -2)", new HexCell(1, 1).toString());
  }
}