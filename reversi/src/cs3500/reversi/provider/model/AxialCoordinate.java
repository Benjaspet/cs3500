/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an axial coordinate in a hexagonal grid, used for positioning hexagons in Hexagonal
 * Reversi.
 * <p>
 * Axial coordinates are a way of identifying positions on a hexagonal grid using
 * two coordinates (q, r), simplifying certain calculations compared to the cubic or
 * Cartesian coordinate systems. In the context of Hexagonal Reversi, these coordinates
 * help in identifying and navigating the positions of hexagons on the board.
 * </p>
 */

public class AxialCoordinate {

  private final int q;
  private final int r;

  /**
   * Creates a new axial coordinate with the specified q and r values for use in a hexagonal grid.
   *
   * @param q the q-coordinate, representing the diagonal part in the hexagonal grid - its value
   *          increases from bottom left to top right, and stays the same from the bottom right to
   *          the top left.
   * @param r the r-coordinate, representing the row (horizontal) part in the hexagonal grid.
   */
  public AxialCoordinate(int q, int r) {
    this.q = q;
    this.r = r;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.r);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    AxialCoordinate other = (AxialCoordinate) obj;
    return this.q == other.q && this.r == other.r;
  }

  /**
   * Retrieves the q-coordinate of this axial coordinate, representing the diagonal position in
   * the hexagonal grid.
   *
   * @return the q-coordinate as an integer.
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Retrieves the r-coordinate of this axial coordinate, representing the row position in the
   * hexagonal grid.
   *
   * @return the r-coordinate as an integer.
   */
  public int getR() {
    return this.r;
  }

  /**
   * Provides a list of axial coordinates representing the six neighboring directions in a
   * hexagonal grid.
   * <p>
   * The directions are represented as relative coordinates from a central hexagon,
   * useful for identifying neighboring hexagons in Hexagonal Reversi.
   * </p>
   *
   * @return a list of axial coordinates representing the neighboring directions.
   */
  public static List<AxialCoordinate> getNeighborDirections() {
    List<AxialCoordinate> directions = new ArrayList<>();
    directions.add(new AxialCoordinate(0, 1));        // bottom-right
    directions.add(new AxialCoordinate(0, -1));       // top-left
    directions.add(new AxialCoordinate(-1, 0));       // Left
    directions.add(new AxialCoordinate(1, 0));        // Right
    directions.add(new AxialCoordinate(-1, 1));       // bottom-left
    directions.add(new AxialCoordinate(1, -1));       // Top-right

    return directions;
  }
}