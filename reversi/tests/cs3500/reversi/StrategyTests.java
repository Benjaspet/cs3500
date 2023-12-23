package cs3500.reversi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.strategy.AvoidCellsNextToCorners;
import cs3500.reversi.strategy.ChooseCornerCell;
import cs3500.reversi.strategy.FlipMostCells;
import cs3500.reversi.strategy.ReversiStrategy;
import cs3500.reversi.strategy.StrategyWithFallbacks;


/**
 * Tests for sample "AI" strategies in the Reversi game.
 */
public final class StrategyTests {
  private ReversiModel model;

  @Before
  public void init() {
    model = new HexagonalReversi(7);
  }

  @Test
  public void basicStratTestTopLeftmostMove() {
    ReversiStrategy strat = new FlipMostCells();
    Optional<Point> selected = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(selected.isPresent());
    Point p = new Point(1, 2);
    Assert.assertEquals(p, selected.get());
  }

  @Test
  public void testAvoidNextToCornerCells() {
    ReversiStrategy strat = new AvoidCellsNextToCorners();
    List<Point> neighbors = new ArrayList<>(List.of(
            new Point(0, 1),
            new Point(1, 0),
            new Point(1, 1),

            new Point(0, 2),
            new Point(1, 3),
            new Point(1, 2),

            new Point(6, 1),
            new Point(5, 0),
            new Point(5, 1),

            new Point(6, 5),
            new Point(5, 6),
            new Point(5, 5),

            new Point(3, 1),
            new Point(2, 0),
            new Point(2, 1),

            new Point(3, 2),
            new Point(2, 3),
            new Point(2, 2)
    ));

    model.playMove(2, 1, PieceColor.WHITE);

    Optional<Point> move = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertFalse(neighbors.contains(move.get()));
    Assert.assertEquals(new Point(4, 4), move.get());
    model.playMove(move.get().x, move.get().y, PieceColor.BLACK);

    model.playMove(5, 2, PieceColor.WHITE);

    move = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertFalse(neighbors.contains(move.get()));
    Assert.assertEquals(new Point(4, 1), move.get());
    model.playMove(move.get().x, move.get().y, PieceColor.BLACK);

    model.playMove(5, 4, PieceColor.WHITE);

    move = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(move.isPresent());
    Assert.assertFalse(neighbors.contains(move.get()));
    Assert.assertEquals(new Point(2, 4), move.get());
    model.playMove(move.get().x, move.get().y, PieceColor.BLACK);

    model.playMove(4, 0, PieceColor.WHITE);

    move = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertFalse(move.isPresent());
  }

  @Test
  public void testChooseCornerCell() {
    ReversiStrategy strat = new ChooseCornerCell();
    List<Point> neighbors = new ArrayList<>(List.of(
            new Point(0, 0),
            new Point(0, 3),
            new Point(6, 0),
            new Point(6, 3),
            new Point(2, 0),
            new Point(2, 6)
    ));

    model.playMove(2, 1, PieceColor.WHITE);
    model.playMove(1, 2, PieceColor.BLACK);

    Optional<Point> move = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertFalse(move.isPresent());

    model.playMove(0, 2, PieceColor.WHITE);
    model.playMove(0, 1, PieceColor.BLACK);

    move = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(move.isPresent());
    Assert.assertTrue(neighbors.contains(move.get()));
    Assert.assertEquals(new Point(0, 0), move.get());
    model.playMove(move.get().x, move.get().y, PieceColor.WHITE);
  }

  @Test
  public void testFlipMostCells() {
    ReversiStrategy strat = new FlipMostCells();

    model.playMove(2, 1, PieceColor.WHITE);
    model.playMove(1, 2, PieceColor.BLACK);

    int oldScore = model.getScore(PieceColor.WHITE);
    Optional<Point> move = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Point(2, 4), move.get());

    List<List<ReversiCellPair>> cells = model.getBoardCopy();
    int highestCount = 0;
    for (int x = 0; x < cells.size(); x++) {
      List<ReversiCellPair> row = cells.get(x);
      for (int y = 0; y < row.size(); y++) {
        if (!model.isValidMove(x, y, PieceColor.WHITE)) {
          continue;
        }
        int count = model.getCellsThatWillBeFlipped(row.get(y).getCell(), PieceColor.WHITE).size();
        if (count > highestCount) {
          highestCount = count;
        }
      }
    }
    model.playMove(move.get().x, move.get().y, PieceColor.WHITE);
    Assert.assertEquals(oldScore + highestCount + 1, model.getScore(PieceColor.WHITE));
  }

  @Test
  public void ensurePicksTopLeft() {
    ReversiStrategy strat = new FlipMostCells();
    // All possible cells white can do on turn 1
    List<Point> allPossible = List.of(new Point(1, 2), new Point(2, 1), new Point(2, 4),
            new Point(4, 1), new Point(4, 4), new Point(5, 2));
    List<Point> allValid = new ArrayList<>();

    int oldScore = model.getScore(PieceColor.WHITE);
    Optional<Point> move = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(move.isPresent());

    List<List<ReversiCellPair>> cells = model.getBoardCopy();
    int highestCount = 0;
    for (int x = 0; x < cells.size(); x++) {
      List<ReversiCellPair> row = cells.get(x);
      for (int y = 0; y < row.size(); y++) {
        if (!model.isValidMove(x, y, PieceColor.WHITE)) {
          continue;
        }
        int count = model.getCellsThatWillBeFlipped(row.get(y).getCell(), PieceColor.WHITE).size();
        // Ensure this point is in the possible move set
        Assert.assertTrue(allPossible.contains(new Point(x, y)));
        allValid.add(new Point(x, y));
        if (count > highestCount) {
          highestCount = count;
        }
      }
    }
    // Ensure that all determined moves were indeed all the possible moves to be made in the board
    Assert.assertTrue(allPossible.containsAll(allValid));
    model.playMove(move.get().x, move.get().y, PieceColor.WHITE);
    // this is the top leftmost cell
    Assert.assertEquals(new Point(1, 2), move.get());
    // Ensure that flip most cells' core logic still holds as well
    Assert.assertEquals(oldScore + highestCount + 1, model.getScore(PieceColor.WHITE));
  }

  @Test
  public void testStrategyWithFallbacks() {
    ReversiStrategy strat = new StrategyWithFallbacks(
            new FlipMostCells(), new AvoidCellsNextToCorners());

    model.playMove(2, 1, PieceColor.WHITE);
    model.playMove(1, 2, PieceColor.BLACK);

    int oldScore = model.getScore(PieceColor.WHITE);
    Optional<Point> move = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(move.isPresent());
    Assert.assertEquals(new Point(2, 4), move.get());

    List<List<ReversiCellPair>> cells = model.getBoardCopy();
    int highestCount = 0;
    for (int x = 0; x < cells.size(); x++) {
      List<ReversiCellPair> row = cells.get(x);
      for (int y = 0; y < row.size(); y++) {
        if (!model.isValidMove(x, y, PieceColor.WHITE)) {
          continue;
        }
        int count = model.getCellsThatWillBeFlipped(row.get(y).getCell(), PieceColor.WHITE).size();
        if (count > highestCount) {
          highestCount = count;
        }
      }
    }
    model.playMove(move.get().x, move.get().y, PieceColor.WHITE);
    Assert.assertEquals(oldScore + highestCount + 1, model.getScore(PieceColor.WHITE));
  }

  @Test
  public void testStrategyWithFallbacksTwo() {
    ReversiStrategy strat =
            new StrategyWithFallbacks(
                    new ChooseCornerCell(),
                    new FlipMostCells());

    model.playMove(1, 2, PieceColor.WHITE);

    Optional<Point> curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(0, 1), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.playMove(0, 2, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(0, 3), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.playMove(4, 4, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(5, 4), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    System.out.println(curStrat.get().x + ", " + curStrat.get().y);

    curStrat = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(2, 4), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.WHITE);


    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(4, 1), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    curStrat = strat.chooseMove(model, PieceColor.WHITE);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(4, 0), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(2, 5), curStrat.get());

    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

  }

  @Test
  public void testStrategyWithPassing() {

    ReversiStrategy strat =
            new StrategyWithFallbacks(
                    new ChooseCornerCell(),
                    new FlipMostCells());

    model.playMove(1, 2, PieceColor.WHITE);

    Optional<Point> curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(0, 1), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.playMove(0, 2, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(0, 3), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.playMove(4, 4, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(5, 4), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.playMove(4, 1, PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(4, 0), curStrat.get());
    model.playMove(curStrat.get().x, curStrat.get().y, PieceColor.BLACK);

    model.pass(PieceColor.WHITE);

    curStrat = strat.chooseMove(model, PieceColor.BLACK);
    Assert.assertTrue(curStrat.isPresent());
    Assert.assertEquals(new Point(2, 1), curStrat.get());
  }
}