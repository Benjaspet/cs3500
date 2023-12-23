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

package cs3500.reversi.view.components;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.types.HexCell;
import cs3500.reversi.model.types.ReversiCellPair;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.ViewFeatures;

/**
 * Represents a Reversi Panel that can be interacted with by the user.
 *
 * @see JPanel for more information.
 */

public final class ReversiPanel extends JPanel {

  private final ReadonlyReversiModel model;
  private Graphics2D graphics;
  private final Player player;

  private List<List<Path2D>> hexagons;
  private final List<ViewFeatures> featuresListeners;

  private Point selected = new Point(-1, -1);
  private Point selectedCell = new Point(-1, -1);

  /**
   * Constructs a new {@link ReversiPanel} with the given model and view.
   * The view is used to notify the controller of user input.
   *
   * @param model the {@link ReadonlyReversiModel} to use for this view.
   */

  public ReversiPanel(ReadonlyReversiModel model, Player player) {
    this.model = model;
    this.player = player;
    this.featuresListeners = new ArrayList<>();
    this.generateHexagons();
    this.addMouseListener(new ReversiMouseListener());
    this.addMouseMotionListener(new ReversiMouseListener());
    this.addComponentListener(new ReversiResizeListener());
    this.initKeyComponent();
    this.getInputMap().put(KeyStroke.getKeyStroke('p'), "pass");
    this.getInputMap().put(KeyStroke.getKeyStroke('\n'), "move");
  }

  /**
   * Initializes the key component of this panel. When the player
   * presses the "p" key, the controller is notified that the player
   * wants to pass their turn. When the player presses the "enter" or
   * "return" key, the controller is notified that the player wants to
   * make a move at the selected cell.
   */

  private void initKeyComponent() {

    this.getActionMap().put("pass", new AbstractAction() {

      @Override
      public void actionPerformed(ActionEvent e) {
        for (ViewFeatures features : featuresListeners) {
          features.passTurn();
        }
      }
    });

    this.getActionMap().put("move", new AbstractAction() {

      @Override
      public void actionPerformed(ActionEvent e) {
        for (ViewFeatures features : featuresListeners) {
          Point selectedCell = ReversiPanel.this.selectedCell;
          features.makeMove(selectedCell.x, selectedCell.y);
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.graphics = (Graphics2D) g;
    this.graphics.setColor(Color.DARK_GRAY);
    this.graphics.setFont(this.graphics.getFont().deriveFont(18f));
    this.graphics.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
    this.drawHexagons();
  }

  /**
   * Draws the hexagons on the board using the model's board size.
   */

  private void drawHexagons() {
    for (int i = 0; i < this.hexagons.size(); i++) {
      for (int j = 0; j < this.hexagons.get(i).size(); j++) {
        Path2D path = this.hexagons.get(i).get(j);
        double x = path.getBounds2D().getX();
        double y = path.getBounds2D().getY();
        if (this.selected.x != -1 && path.contains(this.selected) && !this.player.isAI()) {
          this.graphics.setColor(Color.CYAN);
        } else {
          this.graphics.setColor(Color.LIGHT_GRAY);
        }
        this.graphics.fill(path);
        this.graphics.setColor(Color.BLACK);
        this.graphics.draw(path);
        if (this.model.getStateOf(i, j).equals("X")) {
          this.graphics.setColor(Color.BLACK);
          this.drawInlineCircle(this.graphics, x, y);
        } else if (this.model.getStateOf(i, j).equals("O")) {
          this.graphics.setColor(Color.WHITE);
          this.drawInlineCircle(this.graphics, x, y);
        }
      }
    }
  }

  /**
   * Generates the hexagons on the board using the model's board size.
   */

  private void generateHexagons() {
    double yoffset = 0;
    List<List<ReversiCellPair>> board = this.model.getBoardCopy();
    this.hexagons = new ArrayList<>();
    for (int i = 0; i < board.size(); i++) {
      this.hexagons.add(new ArrayList<>());
      double xoffset = Math.abs((HexCell.HEX_WIDTH / 2) * (model.getRadius() - i));
      for (int j = 0; j < model.getBoardCopy().get(i).size(); j++) {
        Path2D path = new Hexagon(xoffset, yoffset, HexCell.HEX_SIZE);
        this.hexagons.get(i).add(path);
        if (j != board.get(i).size() - 1) {
          xoffset += HexCell.HEX_WIDTH;
        }
      }
      yoffset += (double) (HexCell.HEX_HEIGHT * 3) / 4;
    }
  }

  /**
   * Draws an inline circle on the board.
   *
   * @param g the {@link Graphics2D} to draw with.
   * @param xoffset the x offset of the circle.
   * @param yoffset the y offset of the circle.
   */

  private void drawInlineCircle(Graphics2D g, double xoffset, double yoffset) {
    int x = (int) (xoffset + HexCell.HEX_WIDTH / 2) - HexCell.HEX_SIZE / 2;
    int y = (int) (yoffset + HexCell.HEX_HEIGHT / 2) - HexCell.HEX_SIZE / 2;
    g.fillOval(x, y, HexCell.HEX_SIZE, HexCell.HEX_SIZE);
  }

  /**
   * Adds the given {@link ViewFeatures} to this view.
   * @param features the {@link ViewFeatures} to add.
   */

  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  /**
   * Gets the player of this view.
   * @return the player of this view.
   */

  public Player getPlayer() {
    return this.player;
  }

  /**
   * Refreshes the selected cell and selected point.
   */

  public void refresh() {
    this.selected = new Point(-1, -1);
    this.selectedCell = new Point(-1, -1);
  }

  /**
   * Checks if the given point is on a cell line.
   *
   * @param point the {@link Point} to check.
   * @return true if the given point is on a cell line, false otherwise.
   */

  private boolean isOnCellLine(Point point) {
    boolean isOneSelected = false;
    boolean areTwoSelected = false;
    for (List<Path2D> hexagon : hexagons) {
      for (Path2D path : hexagon) {
        if (path.contains(point)) {
          if (isOneSelected) {
            areTwoSelected = true;
          } else {
            isOneSelected = true;
          }
        }
      }
    }
    return areTwoSelected;
  }

  /**
   * Represents a mouse listener for the {@link ReversiPanel}.
   *
   * @see MouseAdapter for more information.
   */

  private class ReversiMouseListener extends MouseAdapter implements MouseMotionListener {

    @Override
    public void mousePressed(MouseEvent event) {

      boolean clickedOnBoard = false;
      for (int i = 0; i < hexagons.size(); i++) {
        for (int j = 0; j < hexagons.get(i).size(); j++) {
          Path2D path = hexagons.get(i).get(j);
          if (path.contains(event.getPoint()) && !player.isAI()
                  && !ReversiPanel.this.isOnCellLine(event.getPoint())) {
            selectedCell = new Point(i, j);
            if (model.getStateOf(i, j).equals("_")) {
              if (selected.x != -1 && path.contains(selected)) {
                selected = new Point(-1, -1);
              } else {
                selected = event.getPoint();
              }
            } else {
              selected = new Point(-1, -1);
            }
            clickedOnBoard = true;
            repaint();
            break;
          }
        }
      }
      if (!clickedOnBoard) {
        selected = new Point(-1, -1);
        repaint();
      }
    }
  }

  /**
   * Represents a component listener for the {@link ReversiPanel}. This listener
   * is used to resize the hexagons when the panel is resized.
   */

  private class ReversiResizeListener extends ComponentAdapter {

    /**
     * Invoked when the component's size changes.
     * @param e the event to be processed.
     */

    public void componentResized(ComponentEvent e) {
      ReversiPanel.this.generateHexagons();
      ReversiPanel.this.repaint();
    }
  }
}