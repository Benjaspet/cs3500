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

package cs3500.reversi.strategy;

import java.awt.Point;
import java.util.Optional;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.types.PieceColor;

/**
 * Represents a fallible strategy for choosing a move in a game of Reversi.
 * A fallible strategy may not be able to choose a move for a given game state.
 * Typically, a fallible strategy is used in conjunction with a
 * {@link StrategyWithFallbacks} to create a complete strategy, and it often
 * chained with additional strategies to make a more complex strategy. The
 * strategy will only fail if there are no valid moves for the provided player,
 * in which case it will return an empty {@link Optional}.
 *
 * @see StrategyWithFallbacks
 */

public interface ReversiStrategy {

  /**
   * Chooses a move for the given player based on the game state of
   * the provided model. If the strategy cannot choose a move, then it
   * returns an empty {@link Optional} to indicate this.
   *
   * @param model  the model to choose a move from.
   * @param pieceColor the player to choose a move for.
   * @return an {@link Optional} containing the move to make.
   *
   * @see Optional
   * @see Point
   */

  Optional<Point> chooseMove(ReadonlyReversiModel model, PieceColor pieceColor);

}