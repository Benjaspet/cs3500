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

import java.util.List;

/**
 * Represents the controller for the game of Klondike.
 * This controller takes in a Readable and Appendable, and uses them to
 * interact with the user.
 * The controller takes in input from the user, and uses it to play the game.
 * The controller will display the game state to the user, and will display
 * error messages if the user enters invalid input.
 */

public interface KlondikeController {

  /**
   * The primary method for beginning and playing a game.
   *
   * @param model The game of solitaire to be played
   * @param deck The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numPiles How many piles should be in the initial deal
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model is null
   * @throws IllegalStateException if the game cannot be started,
   *          or if the controller cannot interact with the player.
   */

  void playGame(KlondikeModel model, List<Card> deck, boolean shuffle, int numPiles, int numDraw);
}
