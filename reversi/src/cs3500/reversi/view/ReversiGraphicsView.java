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

package cs3500.reversi.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.types.HexCell;
import cs3500.reversi.model.types.PieceColor;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.components.ReversiPanel;

/**
 * Represents a visual (JFrame) view that can be interacted with by the user.
 * This view is used to play the game of Reversi. It displays the current state
 * of the game, and allows the user to interact with the game by clicking on
 * cells on the board. It also displays information about the current state of
 * the game, such as whose turn it is and the score.
 */

public final class ReversiGraphicsView extends JFrame implements GraphicsView {

  private final ReadonlyReversiModel model;
  private final ReversiPanel panel;

  /**
   * Constructs a new {@link ReversiGraphicsView} with the given model.
   * Initializes the frame to be used by this view with pre-determined configuration settings
   * that best optimize for play. This includes disabling resizing and setting the default close
   * operation to exit the program.
   *
   * @param model the {@link ReadonlyReversiModel} to use for this view.
   */

  public ReversiGraphicsView(ReadonlyReversiModel model, Player player) {
    super("Reversi");
    this.model = model;
    this.panel = new ReversiPanel(model, player);
    this.add(this.panel);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack();
  }

  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  @Override
  public Dimension getPreferredSize() {
    int diameter = this.model.getDiameter();
    int verticalInsets = this.getInsets().top + this.getInsets().bottom;
    int horizontalInsets = this.getInsets().left + this.getInsets().right;
    int width = diameter * HexCell.HEX_WIDTH + horizontalInsets;
    int height = diameter * HexCell.HEX_HEIGHT * 3 / 4 + HexCell.HEX_HEIGHT / 4 + verticalInsets;
    return new Dimension(width, height);
  }

  @Override
  public void refresh() {
    this.panel.refresh();
    this.repaint();
  }

  @Override
  public void notifyMessageDialog(String message) {
    String formatted = message.replace("X's", "your").replace("O's", "your")
            .replace("X", "Black").replace("O", "White");
    JOptionPane.showMessageDialog(this, formatted,
            "Alert", JOptionPane.PLAIN_MESSAGE, null);
    if (this.model.isGameOver()) {
      this.setTitle("Game Over! | " + this.model.getWinner().get() + " wins!");
    }
  }

  @Override
  public void notifyTurn(Player player) {
    String turnValue = player.isTurn() ? "Your Turn" : "Opponent's Turn";
    int whiteScore = this.model.getScore(PieceColor.WHITE);
    int blackScore = this.model.getScore(PieceColor.BLACK);
    if (player.isAI()) {
      turnValue = "AI Player";
    }
    this.setTitle(String.format("Player %s | %s | W=%s, B=%s",
            player.getPieceColor(), turnValue, whiteScore, blackScore));
  }
}