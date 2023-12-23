/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

/**
 * Enum representing a piece in a game of Reversi. Each piece can be either white, black, or empty.
 */

public enum Piece {

  WHITE("O"), BLACK("X"), EMPTY("_");

  private final String representation;

  /**
   * Constructs a Piece instance with a specified string representation.
   *
   * @param representation a String representing the piece, not null.
   * @throws NullPointerException if representation is null.
   */

  Piece(String representation) {
    if (representation == null) {
      throw new IllegalArgumentException("Representation of the enum cannot be null.");
    }
    this.representation = representation;
  }

  /**
   * Provides the string representation of this piece for use in a textual view.
   *
   * @return the String representation of this piece.
   */

  @Override
  public String toString() {
    return this.representation;
  }
}
