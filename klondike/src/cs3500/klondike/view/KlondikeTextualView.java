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

package cs3500.klondike.view;

import java.io.IOException;
import java.util.stream.Collectors;

import cs3500.klondike.model.hw02.Card;
import cs3500.klondike.model.hw02.KlondikeModel;

/**
 * A simple text-based rendering of the Klondike game.
 */

public class KlondikeTextualView implements TextualView {

  private final KlondikeModel model;
  private final Appendable in;

  /**
   * Create a new textual view of Klondike with the provided model.
   * @param model the model to render.
   */

  public KlondikeTextualView(KlondikeModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    this.in = System.out;
  }

  /**
   * Create a new textual view of Klondike with the provided model and appendable.
   * @param model the model to render.
   * @param in the appendable to write to.
   */

  public KlondikeTextualView(KlondikeModel model, Appendable in) {
    if (model == null || in == null) {
      throw new IllegalArgumentException("Model and appendable cannot be null.");
    }
    this.model = model;
    this.in = in;
  }

  /**
   * Renders the game state as a string.
   * @return the string representation of the game state.
   */

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Draw: ")
            .append(this.model.getDrawCards().stream().map(Card::toString)
            .collect(Collectors.joining(", ")))
            .append("\n");
    result.append("Foundation: ");
    for (int i = 0; i < this.model.getNumFoundations(); i++) {
      if (this.model.getCardAt(i) == null) {
        result.append("<none>");
      } else {
        result.append(this.model.getCardAt(i).toString());
      }
      if (i != this.model.getNumFoundations() - 1) {
        result.append(", ");
      }
    }
    result.append("\n");
    for (int rowIndex = 0; rowIndex < this.model.getNumRows(); rowIndex++) {
      for (int pileIndex = 0; pileIndex < this.model.getNumPiles(); pileIndex++) {
        if (rowIndex < this.model.getPileHeight(pileIndex)) {
          if (this.model.isCardVisible(pileIndex, rowIndex)) {
            if (this.model.getCardAt(pileIndex, rowIndex).getValue() != 10) {
              result.append(" ");
            }
            result.append(this.model.getCardAt(pileIndex, rowIndex));
          } else {
            result.append("  ?");
          }
        } else {
          if (rowIndex == 0 && this.model.getPileHeight(pileIndex) == 0) {
            result.append("  X");
          } else {
            result.append("   ");
          }
        }
      }
      result.append("\n");
    }
    return result.toString().strip();
  }

  @Override
  public void render() throws IOException {
    try {
      this.in.append(this.toString()).append("\n");
    } catch (IOException e) {
      throw new IOException("Could not write to appendable.");
    }
  }
}