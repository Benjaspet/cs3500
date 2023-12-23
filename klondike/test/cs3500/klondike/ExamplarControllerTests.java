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

import cs3500.klondike.controller.KlondikeController;
import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.Card;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents tests for the controller of Klondike. These are used
 * to test and search for possible bugs in the controller.
 */

public class ExamplarControllerTests {

  KlondikeModel model = new BasicKlondike();

  List<Card> deck = new ArrayList<>();

  @Before
  public void initAces() {
    this.deck = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠")
                    || card.toString().equals("A♣"))
            .collect(Collectors.toList());
    this.deck.sort(Comparator.comparing(Card::toString));
  }

  @Before
  public void initTwos() {
    List<Card> twos = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♢")
                    || card.toString().equals("2♡")
                    || card.toString().equals("2♠")
                    || card.toString().equals("2♣"))
            .collect(Collectors.toList());
    this.deck.addAll(twos);
  }

  @Test
  public void testQuitGameStateWhenQuit() {
    this.initAces();
    this.initTwos();
    Readable in = new StringReader("q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 2, 1);
    Assert.assertTrue(log.toString().contains("State of game when quit:\n"));
  }

  @Test
  public void testGameOver() {
    this.initAces();
    this.initTwos();
    Readable in = new StringReader("q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 2, 1);
    Assert.assertTrue(log.toString().contains("Game quit!\n" + "State of game when quit:\n")
            && log.toString().contains("Score: 0"));
  }

  @Test
  public void testInvalidMovePile() {
    this.initAces();
    this.initTwos();
    Readable in = new StringReader("mpp 3 1 1 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 3, 1);
    Assert.assertTrue(log.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMoveDrawToFoundation() {
    this.initAces();
    this.initTwos();
    Readable in = new StringReader("mdf 1 1 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 3, 1);
    Assert.assertTrue(log.toString().contains("Invalid move."));
  }

  @Test
  public void testInvalidMoveDraw() {
    this.initAces();
    Readable in = new StringReader("md 1 1 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 2, 1);
    Assert.assertTrue(log.toString().contains("Invalid move."));
  }

  @Test
  public void testBogusInput() {
    this.initAces();
    Readable in = new StringReader("#!$&* mpf 2 mpf 1 2 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 2, 1);
    Assert.assertTrue(log.toString().contains("Invalid move."));
  }

  @Test
  public void testQuitGameImmediatelyAfterStart() {
    this.initAces();
    Readable in = new StringReader("q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 2, 1);
    String[] lines = log.toString().split("\n");
    Assert.assertEquals(12, lines.length);
  }

  @Test
  public void testValidMovePile() {
    this.initAces();
    this.initTwos();
    Readable in = new StringReader("mpp 1 1 3 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, this.deck, false, 3, 1);
    Assert.assertFalse(log.toString().contains("Invalid move."));
  }

  @Test
  public void testValidMoveDraw() {
    List<Card> threeAces = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("A♢")
                    || card.toString().equals("A♡")
                    || card.toString().equals("A♠"))
            .collect(Collectors.toList());
    List<Card> threeTwos = this.model.getDeck()
            .stream().filter(card -> card.toString().equals("2♢")
                    || card.toString().equals("2♡")
                    || card.toString().equals("2♠"))
            .collect(Collectors.toList());
    threeTwos.addAll(threeAces);
    threeTwos.sort(Comparator.comparing(Card::toString));
    Readable in = new StringReader("md 2 q");
    StringBuilder log = new StringBuilder();
    KlondikeController controller = new KlondikeTextualController(in, log);
    controller.playGame(this.model, threeTwos, false, 2, 1);
    Assert.assertFalse(log.toString().contains("Invalid move."));
  }
}