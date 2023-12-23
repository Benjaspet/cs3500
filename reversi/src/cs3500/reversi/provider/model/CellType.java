package cs3500.reversi.provider.model;

/**
 * Represents a cell type in a Reversi game board.
 * A cell can hold a piece which can be either black, white or empty.
 */
public interface CellType {

  /**
   * Gets the current piece on this cell.
   *
   * @return the piece on this cell.
   */
  Piece getPiece();

  /**
   * Sets a piece on this cell.
   *
   * @param piece the piece to be set on this cell.
   */
  void setPiece(Piece piece);

  /**
   * Gets the string representation of the piece on this cell.
   *
   * @return the string representation of the piece.
   */
  String toString();

  /**
   * Flips the piece on this cell to the opposite color.
   * @throws IllegalStateException if the piece on this cell is empty.
   */
  void flipPiece() throws IllegalStateException;
}
