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

import cs3500.reversi.model.HexagonalReversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.view.TextualView;
import cs3500.reversi.view.TextualReversi;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for the textual view of a Reversi game.
 */
public final class TextualViewTests {

  private ReversiModel model1;

  @Before
  public void init() {
    this.model1 = new HexagonalReversi(5);
  }

  @Test
  public void testTextualViewForGameJustStarted() {
    TextualView view = new TextualReversi(model1);
    assertEquals("  _ _ _\n" +
            " _ X O _\n" +
            "_ O _ X _\n" +
            " _ X O _\n" +
            "  _ _ _\n" +
            "\n" +
            "Turn #1 (White)\n" +
            "Black Score: 3\n" +
            "White Score: 3\n", view.toString());
  }

  @Test
  public void testTextualViewAfterSingularMove() {
    TextualView view = new TextualReversi(model1);
    model1.playMove(0, 1, PieceColor.WHITE);
    assertEquals("  _ O _\n" +
            " _ O O _\n" +
            "_ O _ X _\n" +
            " _ X O _\n" +
            "  _ _ _\n" +
            "\n" +
            "Turn #2 (Black)\n" +
            "Black Score: 2\n" +
            "White Score: 5\n", view.toString());
  }

  @Test
  public void testTextualViewForGameOver() {
    TextualView view = new TextualReversi(model1);
    this.model1.playMove(0, 1, PieceColor.WHITE);
    this.model1.playMove(1, 0, PieceColor.BLACK);
    this.model1.playMove(3, 0, PieceColor.WHITE);
    this.model1.playMove(1, 3, PieceColor.BLACK);
    this.model1.playMove(3, 3, PieceColor.WHITE);
    this.model1.playMove(4, 1, PieceColor.BLACK);
    assertEquals("  _ O _\n" +
            " X X O X\n" +
            "_ X _ X _\n" +
            " O X X O\n" +
            "  _ X _\n" +
            "\n" +
            "Turn #7 (White)\n" +
            "Black Score: 8\n" +
            "White Score: 4\n", view.toString());
  }

}
