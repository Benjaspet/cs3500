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

/**
 * Represents the state of a cell in a Reversi game.
 * Empty cells are represented as "_", black cells as "X",
 * and white cells as "O".
 */

public enum HexCellState {

  EMPTY("_"), BLACK("X"), WHITE("O");

  private final String symbol;

  HexCellState(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Get the symbol of this cell state.
   *
   * @return the symbol of this cell state.
   */

  @Override
  public String toString() {
    return this.symbol;
  }
}