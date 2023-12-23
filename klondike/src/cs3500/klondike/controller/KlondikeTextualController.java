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

package cs3500.klondike.controller;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.view.KlondikeTextualView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the text-based controller for a game of Klondike.
 * This controller takes in a Readable and Appendable, and uses them to
 * interact with the user.
 * The controller takes in input from the user, and uses it to play the game.
 * The controller will display the game state to the user, and will display
 * error messages if the user enters invalid input.
 *
 * @field appendable the Appendable to write output to.
 * @field scanner the Scanner to parse input from the Readable.
 * @field view the view to display the game state to the user.
 * @field model the model to play the game with.
 */

public class KlondikeTextualController implements KlondikeController {

  private final Appendable appendable;
  private final Scanner sc;
  private KlondikeTextualView view;
  private KlondikeModel model;

  /**
   * Constructs a KlondikeTextualController to play Klondike with.
   * @param readable the Readable to parse input from.
   * @param appendable the Appendable to write output to.
   */

  public KlondikeTextualController(Readable readable, Appendable appendable) {
    if (readable == null || appendable == null) {
      throw new IllegalArgumentException("Readable and appendable cannot be null.");
    }
    this.appendable = appendable;
    this.sc = new Scanner(readable);
  }

  @Override
  public void playGame(KlondikeModel model, List<Card> deck, boolean shuff, int numP, int numD) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Deck cannot be empty.");
    }
    this.model = model;
    try {
      this.model.startGame(deck, shuff, numP, numD);
    } catch (IllegalStateException | IllegalArgumentException isex) {
      throw new IllegalStateException("Game cannot be started.");
    }
    this.view = new KlondikeTextualView(this.model, this.appendable);
    this.displayBoard();
    while (!this.model.isGameOver()) {
      if (!this.sc.hasNext()) {
        throw new IllegalStateException("Readable has no more input.");
      }
      try {
        String cmd = this.sc.next().toLowerCase();
        switch (cmd) {
          case "mpp":
            this.model.movePile(getNextInt() - 1, getNextInt(), getNextInt() - 1);
            break;
          case "md":
            this.model.moveDraw(getNextInt() - 1);
            break;
          case "mpf":
            this.model.moveToFoundation(getNextInt() - 1, getNextInt() - 1);
            break;
          case "mdf":
            this.model.moveDrawToFoundation(getNextInt() - 1);
            break;
          case "dd":
            this.model.discardDraw();
            break;
          case "q":
            this.appendMessage("Game quit!" + "\n" + "State of game when quit:");
            this.displayBoard();
            return;
          default:
            this.appendMessage("Invalid move. Play again.");
            break;
        }
        this.displayBoard();
      } catch (IllegalStateException | IllegalArgumentException iae) {
        String msg = iae.getMessage();
        this.appendMessage("Invalid move. Play again. " + msg);
        this.displayBoard();
      } catch (QuitGameException immex) {
        this.appendMessage("Game quit!" + "\n" + "State of game when quit:");
        this.displayBoard();
        break;
      }
    }
    this.handleGameEnding(deck);
  }

  private void appendMessage(String msg) {
    try {
      this.appendable.append(msg).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable cannot be written to.");
    }
  }

  private void displayBoard() {
    try {
      this.view.render();
      this.appendMessage("Score: " + this.model.getScore());
    } catch (IOException e) {
      throw new IllegalStateException("Appendable cannot be written to.");
    }
  }

  private int getNextInt() throws QuitGameException {
    if (!this.sc.hasNextInt()) {
      if (!this.sc.hasNext()) {
        throw new IllegalStateException("Readable has no more input.");
      } else if (this.sc.hasNext("q") || this.sc.hasNext("Q")) {
        throw new QuitGameException("User quit the game.");
      } else {
        this.sc.next();
        this.appendMessage("Invalid input. Play again.");
      }
      return this.getNextInt();
    }
    return this.sc.nextInt();
  }

  private void handleGameEnding(List<Card> deck) {
    if (this.model.isGameOver()) {
      int score = this.model.getScore();
      if (deck.size() == score) {
        this.appendMessage("You win!" + "\n");
      } else {
        this.appendMessage("Game over. Score: " + score);
      }
    }
  }
}