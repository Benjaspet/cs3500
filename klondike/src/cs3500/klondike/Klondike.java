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

import java.io.InputStreamReader;

import cs3500.klondike.controller.KlondikeTextualController;
import cs3500.klondike.model.hw02.BasicKlondike;
import cs3500.klondike.model.hw02.KlondikeModel;
import cs3500.klondike.model.hw04.LimitedDrawKlondike;
import cs3500.klondike.model.hw04.WhiteheadKlondike;

/**
 * The main class for the Klondike game.
 * basic <?numPiles> <?numDraw>
 *   Starts a basic Klondike game with the given number of piles and draw cards.
 *   If no number of piles is given, the default is 7.
 *   If no number of draw cards is given, the default is 3.
 *   Example: basic 7 3
 * whitehead <?numPiles> <?numDraw>`
 *   Starts a Whitehead Klondike game with the given number of piles and draw cards.
 *   If no number of piles is given, the default is 7.
 *   If no number of draw cards is given, the default is 3.
 *   Example: whitehead 7 3
 * limited <?numRedraw> <?numPiles> <?numDraw>
 *   Starts a Limited Draw Klondike game with the given number of redraws, piles,
 *   and draw cards.
 *   If no number of redraws is given, the default is 2.
 *   If no number of piles is given, the default is 7.
 *   If no number of draw cards is given, the default is 3.
 *   Example: limited 2 7 3
 */

public final class Klondike {

  /**
   * The main method for the Klondike game. This takes in command input.
   * @param args the command line arguments.
   */

  public static void main(String[] args) {
    InputStreamReader in = new InputStreamReader(System.in);
    KlondikeTextualController controller = new KlondikeTextualController(in, System.out);
    KlondikeModel model;
    if (args.length == 0) {
      throw new IllegalArgumentException("Provide a game type: basic, whitehead, or limited.");
    }
    int numPiles = 7;
    int numDraw = 3;
    int numRedraw = 1;
    switch (args[0].toLowerCase()) {
      case "basic":
      case "whitehead":
        if (args[0].equalsIgnoreCase("basic")) {
          model = new BasicKlondike();
        } else {
          model = new WhiteheadKlondike();
        }
        if (args.length >= 2) {
          numPiles = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
          numDraw = Integer.parseInt(args[2]);
        }
        break;
      case "limited":
        if (args.length >= 2) {
          numRedraw = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
          numPiles = Integer.parseInt(args[2]);
        }
        if (args.length >= 4) {
          numDraw = Integer.parseInt(args[3]);
        }
        model = new LimitedDrawKlondike(numRedraw - 1);
        break;
      default:
        throw new IllegalArgumentException("Invalid game type.");
    }
    try {
      controller.playGame(model, model.getDeck(), true, numPiles, numDraw);
    } catch (IllegalArgumentException | IllegalStateException ise) {
      System.out.println(ise.getMessage());
    }
  }
}