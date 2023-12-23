/*
 * Copyright © 2023 Ben Petrillo. All rights reserved.
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

package cs3500.klondike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.MockKlondike;

/**
 * Represents all tests for the KlondikeTextualController, to make sure
 * that it handles input correctly using a mock model.
 */

public class ControllerInputTests {

  KlondikeModel mockModel;
  List<Card> mockDeck;
  StringBuilder gameLog;
  StringBuilder mockLog;

  @Before
  public void init() {
    this.gameLog = new StringBuilder();
    this.mockLog = new StringBuilder();
    this.mockModel = new MockKlondike(this.mockLog);
    this.mockDeck = new BasicKlondike().getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠")
                    || card.toString().equals("A♣")
                    || card.toString().equals("2♢")
                    || card.toString().equals("2♡")
                    || card.toString().equals("2♠")
                    || card.toString().equals("2♣"))
            .collect(Collectors.toList());
    this.mockDeck.sort(Comparator.comparing(Card::toString));
  }

  // SINGULAR ACTION INPUTS

  @Test
  public void testStartGameSingularInput() {
    Readable input = new StringReader("q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Started game with deckSize = 8, shuffle = false, numPiles = 7, numDraw = 1.";
    assertEquals(msg, lines[0]);
  }

  @Test
  public void testMovePileSingularInput() {
    Readable input = new StringReader("mpp 1 1 2 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Moved 1 card(s) from pile 0 to pile 1.";
    assertEquals(msg, lines[1]);
  }

  @Test
  public void testMoveDrawSingularInput() {
    Readable input = new StringReader("md 1 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Moved 1 draw card to pile 0.";
    assertEquals(msg, lines[1]);
  }

  @Test
  public void testMoveToFoundationSingularInput() {
    Readable input = new StringReader("mpf 1 1 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Moved 1 card from cascade pile 0 to foundation pile 0.";
    assertEquals(msg, lines[1]);
  }

  @Test
  public void testMoveDrawToFoundationSingularInput() {
    Readable input = new StringReader("mdf 1 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Moved 1 draw card to foundation pile 0.";
    assertEquals(msg, lines[1]);
  }

  @Test
  public void testDiscardDrawSingularInput() {
    Readable input = new StringReader("dd q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 7, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String msg = "Discarded 1 draw card.";
    assertEquals(msg, lines[1]);
  }

  @Test
  public void testMockModelConstructorWithInvalidArgs() {
    assertThrows(IllegalArgumentException.class, () -> new MockKlondike(null));
  }

  // MULTIPLE ACTION INPUTS

  @Test
  public void testStartGameMultipleInputs() {
    Readable input = new StringReader("mpp 1 1 2 dd mdf 1 mpf 1 2 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = false, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card(s) from pile 0 to pile 1.";
    String line2 = "Discarded 1 draw card.";
    String line3 = "Moved 1 draw card to foundation pile 0.";
    String line4 = "Moved 1 card from cascade pile 0 to foundation pile 1.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
    assertEquals(line4, lines[4]);
  }

  @Test
  public void testMovePileMultipleInputs() {
    Readable input = new StringReader("mpp 1 1 2 mpp 2 1 3 mpp 3 1 1 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, false, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = false, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card(s) from pile 0 to pile 1.";
    String line2 = "Moved 1 card(s) from pile 1 to pile 2.";
    String line3 = "Moved 1 card(s) from pile 2 to pile 0.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
  }

  @Test
  public void testMoveDrawMultipleInputs() {
    Readable input = new StringReader("md 1 md 2 md 3 md 4 md 5 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 draw card to pile 0.";
    String line2 = "Moved 1 draw card to pile 1.";
    String line3 = "Moved 1 draw card to pile 2.";
    String line4 = "Moved 1 draw card to pile 3.";
    String line5 = "Moved 1 draw card to pile 4.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
    assertEquals(line4, lines[4]);
    assertEquals(line5, lines[5]);
  }

  @Test
  public void testMoveToFoundationMultipleInputs() {
    Readable input = new StringReader("mpf 1 1 mpf 2 2 mpf 3 3 mpf 4 4 mpf 5 1 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card from cascade pile 0 to foundation pile 0.";
    String line2 = "Moved 1 card from cascade pile 1 to foundation pile 1.";
    String line3 = "Moved 1 card from cascade pile 2 to foundation pile 2.";
    String line4 = "Moved 1 card from cascade pile 3 to foundation pile 3.";
    String line5 = "Moved 1 card from cascade pile 4 to foundation pile 0.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
    assertEquals(line4, lines[4]);
    assertEquals(line5, lines[5]);
  }

  @Test
  public void testMoveDrawToFoundationMultipleInputs() {
    Readable input = new StringReader("mdf 1 mdf 2 mdf 3 mdf 4 mdf 5 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 draw card to foundation pile 0.";
    String line2 = "Moved 1 draw card to foundation pile 1.";
    String line3 = "Moved 1 draw card to foundation pile 2.";
    String line4 = "Moved 1 draw card to foundation pile 3.";
    String line5 = "Moved 1 draw card to foundation pile 4.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
    assertEquals(line4, lines[4]);
    assertEquals(line5, lines[5]);
  }

  @Test
  public void testDiscardDrawMultipleInputs() {
    Readable input = new StringReader("dd dd mdf 2 dd dd mpf 1 1 dd dd dd dd q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 1);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 1.";
    String line1 = "Discarded 1 draw card.";
    String line2 = "Discarded 1 draw card.";
    String line3 = "Moved 1 draw card to foundation pile 1.";
    String line4 = "Discarded 1 draw card.";
    String line5 = "Discarded 1 draw card.";
    String line6 = "Moved 1 card from cascade pile 0 to foundation pile 0.";
    String line7 = "Discarded 1 draw card.";
    String line8 = "Discarded 1 draw card.";
    String line9 = "Discarded 1 draw card.";
    String line10 = "Discarded 1 draw card.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
    assertEquals(line4, lines[4]);
    assertEquals(line5, lines[5]);
    assertEquals(line6, lines[6]);
    assertEquals(line7, lines[7]);
    assertEquals(line8, lines[8]);
    assertEquals(line9, lines[9]);
    assertEquals(line10, lines[10]);
  }

  // BOGUS INPUTS

  @Test
  public void testStartGameBogusInput() {
    Readable input = new StringReader("mpp 1 @&$% 1 2 @$** dd mdf & 1 ) q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card(s) from pile 0 to pile 1.";
    String line2 = "Discarded 1 draw card.";
    String line3 = "Moved 1 draw card to foundation pile 0.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
  }

  @Test
  public void testMovePileBogusInput() {
    Readable input = new StringReader("mpp & &! )@* 1 1 2 3 4 mpp *() 2 1 4 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card(s) from pile 0 to pile 1.";
    String line2 = "Moved 1 card(s) from pile 1 to pile 3.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
  }

  @Test
  public void testMoveDrawBogusInputs() {
    Readable input = new StringReader("@*3 md 2 3$m md 1 0 10 @(&# md 3 q @9");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 draw card to pile 1.";
    String line2 = "Moved 1 draw card to pile 0.";
    String line3 = "Moved 1 draw card to pile 2.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
    assertEquals(line3, lines[3]);
  }

  @Test
  public void testMoveToFoundationBogusInputs() {
    Readable input = new StringReader("mpf mpf 1 2 2 (@#& mpf 2 3 q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 card from cascade pile 0 to foundation pile 1.";
    String line2 = "Moved 1 card from cascade pile 1 to foundation pile 2.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
    assertEquals(line2, lines[2]);
  }

  @Test
  public void testMoveDrawToFoundationBogusInputs() {
    Readable input = new StringReader("mdf (!& !) * 1 2 mdf q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Moved 1 draw card to foundation pile 0.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
  }

  @Test
  public void testDiscardDrawBogusInputs() {
    Readable input = new StringReader("dd !(# d !)' mpf dd q");
    KlondikeController controller = new KlondikeTextualController(input, this.gameLog);
    controller.playGame(this.mockModel, this.mockDeck, true, 3, 2);
    String[] lines = this.mockLog.toString().split("\n");
    String line0 = "Started game with deckSize = 8, shuffle = true, numPiles = 3, numDraw = 2.";
    String line1 = "Discarded 1 draw card.";
    assertEquals(line0, lines[0]);
    assertEquals(line1, lines[1]);
  }
}