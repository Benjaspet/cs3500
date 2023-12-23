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

package cs3500.reversi.adapter;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.player.Player;
import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.player.IPlayer;
import cs3500.reversi.provider.view.IView;
import cs3500.reversi.provider.view.ReversiBoardFrame;
import cs3500.reversi.provider.view.ReversiBoardPanel;
import cs3500.reversi.view.GraphicsView;
import cs3500.reversi.view.ViewFeatures;

/**
 * Adapts the provider's view to our view interface. This class implements
 * {@link GraphicsView} and overrides the appropriate methods to make the
 * provider's view interface methods work with our view. It takes in a {@link ModelAdapter}
 * and an {@link IPlayer} to use for the view.
 */

public class ViewAdapter implements GraphicsView {

  private final IView frame;
  private final ReversiBoardPanel panel;

  private final List<ViewFeatures> featuresListeners;

  /**
   * Constructs a new {@link ViewAdapter} using the provided {@link ModelAdapter} and
   * {@link IPlayer}.
   *
   * @param model the model to use for the view.
   * @param player the player to use for the view.
   */

  public ViewAdapter(ModelAdapter model, IPlayer player) {
    this.frame = new ReversiBoardFrame(model, player);
    this.panel = new ReversiBoardPanel(model, player);
    this.featuresListeners = new ArrayList<>();
    this.frame.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
          for (ViewFeatures features : featuresListeners) {
            features.passTurn();
          }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          for (ViewFeatures features : featuresListeners) {
            try {
              AxialCoordinate coord = frame.getCellClicked();
              List<List<ReversiCellPair>> board = model.getBoardCopy();
              Point xy = getPoint(coord, board);
              features.makeMove(xy.x, xy.y);
            } catch (IllegalStateException ex) {
              ViewAdapter.this.frame.showMessageDialog("No cell is selected.");
            }
          }
        }
      }
    });
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.featuresListeners.add(features);
  }

  @Override
  public void setVisible(boolean visible) {
    this.panel.setVisible(visible);
  }

  @Override
  public void refresh() {
    this.frame.refresh();
  }

  @Override
  public void notifyTurn(Player player) {
    if (player.isTurn()) {
      this.notifyMessageDialog("Your turn.");
    }
  }

  @Override
  public void notifyMessageDialog(String message) {
    this.frame.showMessageDialog(message);
  }

  /**
   * Gets the row, column point of the given axial coordinate based on the given board.
   *
   * @param coord the axial coordinate to get the point of.
   * @param board the board to get the point from.
   * @return the row, column point of the given axial coordinate.
   */

  public static Point getPoint(AxialCoordinate coord, List<List<ReversiCellPair>> board) {
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(i).size(); j++) {
        int boardQ = board.get(i).get(j).getCell().getQ();
        int boardR = board.get(i).get(j).getCell().getR();
        if (boardQ == coord.getQ() && boardR == coord.getR()) {
          return new Point(i, j);
        }
      }
    }
    return new Point(-1, -1);
  }
}
