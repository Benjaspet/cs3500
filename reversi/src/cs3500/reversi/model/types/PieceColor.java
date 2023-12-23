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

package cs3500.reversi.model.types;

import cs3500.reversi.view.TextualView;

/**
 * Represents a player in a Reversi game. Players are either
 * black or white. In the {@link TextualView}, black is represented
 * as "X" and white is represented as "O".
 */

public enum PieceColor {

  BLACK("X"), WHITE("O");

  private final String symbol;

  PieceColor(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return this.symbol;
  }
}