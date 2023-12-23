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

import java.util.Objects;

import cs3500.reversi.model.Cell;

/**
 * Represents a cell in a hexagonal grid.
 */

public class HexCell implements Cell {

  public static final int HEX_SIZE = 30;
  public static final int HEX_HEIGHT = 2 * HEX_SIZE;
  public static final int HEX_WIDTH = (int) (Math.sqrt(3) * HEX_SIZE);

  private final int q;
  private final int r;

  /**
   * Constructs a new {@link HexCell} with the given q and r coordinates.
   * @param q the q coordinate.
   * @param r the r coordinate.
   */

  public HexCell(int q, int r) {
    this.q = q;
    this.r = r;
  }

  @Override
  public int getQ() {
    return this.q;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getS() {
    return -this.getQ() - this.getR();
  }

  @Override
  public String toString() {
    return String.format("(%d, %d, %d)", this.getQ(), this.getR(), this.getS());
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Cell) {
      Cell other = (Cell) o;
      return this.getQ() == other.getQ() &&
              this.getR() == other.getR() &&
              this.getS() == other.getS();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getQ(), this.getR(), this.getS());
  }
}
