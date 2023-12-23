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

package cs3500.reversi.model;

import java.util.ArrayList;

/**
 * Represents a generic hexagonal cell in a {@link HexagonalReversi} game.
 * Cells are represented by their axial coordinates (q, r, s).
 * We are using a <code>(row, column)</code> based coordinate system. The origin
 * is the top left corner of the board. The row increases as you move down the board,
 * and the column increases as you move right. For cell storage, we are using a
 * two-dimensional {@link ArrayList}. The first dimension represents the row, and the
 * second dimension represents the column.
 */

public interface Cell {

  /**
   * Get the q coordinate of this cell.
   *
   * @return the q coordinate of this cell.
   */

  int getQ();

  /**
   * Get the r coordinate of this cell.
   *
   * @return the r coordinate of this cell.
   */

  int getR();

  /**
   * Get the s coordinate of this cell.
   *
   * @return the s coordinate of this cell.
   */

  int getS();

}