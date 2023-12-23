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

package cs3500.reversi.view.components;

import java.awt.geom.Path2D;

/**
 * Represents a hexagon shape.
 *
 * @see Path2D.Double for more information.
 */

public final class Hexagon extends Path2D.Double {

  private final double x;
  private final double y;
  private final double size;

  /**
   * Constructs a new {@link Hexagon} with the given x, y, and size.
   *
   * @param x the x coordinate of the center of the hexagon.
   * @param y the y coordinate of the center of the hexagon.
   * @param size the size of the hexagon.
   */

  public Hexagon(double x, double y, double size) {
    this.x = x;
    this.y = y;
    this.size = size;
    double width = Math.sqrt(3) * size;
    double height = size * 2;
    this.moveTo(x + width / 2, y);
    this.lineTo(x + width, y + height / 4);
    this.lineTo(x + width, y + height * 3 / 4);
    this.lineTo(x + width / 2, y + height);
    this.lineTo(x, y + height * 3 / 4);
    this.lineTo(x, y + height / 4);
    this.closePath();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Hexagon)) {
      return false;
    }
    Hexagon that = (Hexagon) o;
    return this.x == that.x && this.y == that.y && this.size == that.size;
  }

  @Override
  public int hashCode() {
    return (int) (this.x + this.y + this.size);
  }

  @Override
  public String toString() {
    return "Hexagon: " + this.x + ", " + this.y + ", " + this.size;
  }
}
