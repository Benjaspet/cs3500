package cs3500.reversi.provider.model;

import java.util.Map;

/**
 * Represents a board for a game of Reversi. The board is composed of hexagonal cells,
 * each identified by axial coordinates and containing a piece.
 */
public interface Board {

  /**
   * Retrieves the current state of the board as a map where each entry corresponds
   * to a hexagon on the board identified by its axial coordinates.
   *
   * @return A Map containing the axial coordinates as keys and Hexagon objects
   *         as values representing the current state of the board.
   */
  Map<AxialCoordinate, CellType> getBoard();

  /**
   * Retrieves the Hexagon at the specified axial coordinate on the board.
   *
   * @param hex The axial coordinate identifying the location on the board.
   * @return The Hexagon at the specified axial coordinate.
   * @throws IllegalStateException if no Hexagon is found at the specified coordinate.
   */
  CellType getHexagonAt(AxialCoordinate hex);
}