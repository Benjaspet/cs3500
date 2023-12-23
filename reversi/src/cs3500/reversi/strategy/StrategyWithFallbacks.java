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
 * Represents a strategy builder that attempts to use a list of
 * {@link ReversiStrategy} in order until one succeeds. If one of the
 * provided strategies succeeds, then the move it chooses is returned by the
 * method. If none of the provided strategies succeed, then an empty
 * {@link Optional} is returned. This practice is better than returning
 * {@code null} because it allows the caller to use {@link Optional#isPresent()}
 * to check if a move was found.
 *
 * @see ReversiStrategy
 */

public final class StrategyWithFallbacks implements ReversiStrategy {

  private final ReversiStrategy[] strategies;

  /**
   * Constructs a new {@link StrategyWithFallbacks} with the given strategies.
   *
   * @param strategies the strategies to use.
   */
  public StrategyWithFallbacks(ReversiStrategy... strategies) {
    this.strategies = strategies;
  }

  @Override
  public Optional<Point> chooseMove(ReadonlyReversiModel model, PieceColor pieceColor) {
    for (ReversiStrategy strategy : this.strategies) {
      Optional<Point> ans = strategy.chooseMove(model, pieceColor);
      if (ans.isPresent()) {
        return ans;
      }
    }
    return Optional.empty();
  }
}
