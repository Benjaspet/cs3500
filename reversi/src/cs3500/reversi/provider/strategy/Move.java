/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.strategy;

import cs3500.reversi.provider.model.AxialCoordinate;

/**
 * Represents a potential move in Reversi, including its coordinates and the potential score
 * added by the move.
 */

public class Move {

  private final AxialCoordinate coord;
  private final int scoreAdded;

  public Move(AxialCoordinate coord, int scoreAdded) {
    this.coord = coord;
    this.scoreAdded = scoreAdded;
  }

  /**
   * What is the coordinate of this Move.
   * @return the coordinate as an AxialCoordinate.
   */
  public AxialCoordinate getCoord() {
    return this.coord;
  }

  /**
   * What is the potential score added by this move.
   * @return the potential score added as an Integer.
   */
  public int getScoreAdded() {
    return this.scoreAdded;
  }
}