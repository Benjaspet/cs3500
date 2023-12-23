/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JPanel;

import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.CellType;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadOnlyReversiInterface;
import cs3500.reversi.provider.player.IPlayer;

/**
 * The {@code ReversiBoardPanel} class represents the panel displaying the Reversi game board.
 * It extends {@code JPanel} and provides interaction with the game board, including mouse click
 * handling.
 * This panel is responsible for rendering the game board, highlighting clicked cells,
 * and scaling based on window size.
 */
public class ReversiBoardPanel extends JPanel {

  private final ReadOnlyReversiInterface model;

  private final IPlayer player;
  private double scaleFactor;
  private Map<AxialCoordinate, CellType> gameBoard;
  private final HashMap<AxialCoordinate, Shape> shapes = new HashMap<>();
  private final HashMap<AxialCoordinate, Color> shapeColors = new HashMap<>();
  private AxialCoordinate clickedCell;

  /**
   * Constructs a new {@code ReversiBoardPanel} with the provided {@code ReadOnlyReversiInterface}.
   *
   * @param readOnlyReversiInterface The read-only interface to the Reversi game data.
   */
  public ReversiBoardPanel(ReadOnlyReversiInterface readOnlyReversiInterface, IPlayer player) {
    super();
    this.model = readOnlyReversiInterface;
    this.gameBoard = readOnlyReversiInterface.getProviderBoard();
    this.player = player;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent me) {
        handleMouseClicked(me);
      }
    });
  }

  /**
   * Represents a click of a mouse and its associated location of the click.
   * @param me is the mouse event that is carried out.
   */
  private void handleMouseClicked(MouseEvent me) {
    boolean clickedInsideBoard = false;

    for (Map.Entry<AxialCoordinate, Shape> shapeEntry : shapes.entrySet()) {
      AxialCoordinate axialCoordinate = shapeEntry.getKey();
      Shape shape = shapeEntry.getValue();

      // Apply the inverse of the scale factor to the mouse click point
      Point unscaledPoint = new Point(
              (int) (me.getX() / scaleFactor),
              (int) (me.getY() / scaleFactor)
      );

      if (shape.contains(unscaledPoint)) {
        System.out.println("Clicked at (" + axialCoordinate.getQ() +
                ", " + axialCoordinate.getR() + ")");
        clickedInsideBoard = true;
        if (axialCoordinate.equals(clickedCell)) {
          // Toggle the color of the currently clicked cell
          Color currentColor = shapeColors.get(axialCoordinate);
          shapeColors.put(axialCoordinate, currentColor.equals(Color.CYAN) ?
                  Color.GRAY : Color.CYAN);
          clickedCell = currentColor.equals(Color.CYAN) ? null : axialCoordinate;
        } else {
          // Change the previously clicked cell back to gray
          if (clickedCell != null) {
            shapeColors.replace(clickedCell, Color.GRAY);
          }
          // Change the newly clicked cell to cyan
          shapeColors.put(axialCoordinate, Color.CYAN);
          clickedCell = axialCoordinate;
        }
        repaint();
        break;
      }
    }
    // Handle click outside any cell
    if (!clickedInsideBoard && clickedCell != null) {
      shapeColors.replace(clickedCell, Color.GRAY);
      clickedCell = null;
      repaint();
    }
  }

  /**
   * Returns the axial coordinate of the cell a user clicked.
   *
   * @return the axial coordinate of where the player clicked.
   */
  public Optional<AxialCoordinate> getClickedCell() {
    if (this.clickedCell == null) {
      return Optional.empty();
    } else {
      return Optional.of(this.clickedCell);
    }
  }

  /**
   * Gets the calculated scale factor based on the window size and initial board dimensions.
   *
   * @return The calculated scale factor.
   */
  private double getScaleFactorCalc() {
    int radius = model.getRadius();
    double hexSize = 30;
    // Initial width of the window
    double referenceWidth = hexSize * (radius + .5) * 4 * (Math.sqrt(3) / 2);
    // Initial height of the window
    double referenceHeight = hexSize * (radius + .5) * 4;
    int currentWidth = getWidth();
    int currentHeight = getHeight();
    // Calculate scale factors for width and height
    double scaleX = (double) currentWidth / referenceWidth;
    double scaleY = (double) currentHeight / referenceHeight;

    // Use the minimum scale factor to maintain aspect ratio
    scaleFactor = Math.min(scaleX, scaleY);
    return scaleFactor;
  }

  /**
   * Displays the Reversi game board on the graphics context.
   *
   * @param g2d The graphics context.
   */
  private void displayingBoard(Graphics2D g2d) {
    int radius = model.getRadius();
    double hexSize = 30;
    for (int q = -radius; q <= radius; q++) {
      int r1 = Math.max(-radius, -q - radius);
      int r2 = Math.min(radius, -q + radius);
      for (int r = r1; r <= r2; r++) {
        AxialCoordinate hexCoord = new AxialCoordinate(r, q);
        if (gameBoard.containsKey(hexCoord)) {
          double centerX = (hexSize * Math.sqrt(3) * (radius + 0.5)) +
                  (r * hexSize / 2 * Math.sqrt(3)) * 2 + (q * hexSize / 2 * Math.sqrt(3));

          double centerY = (hexSize + 1.5 * hexSize * radius) + q * (hexSize * 1.5);

          Shape hex = createHexagon(centerX, centerY, hexSize);

          // Get the color for the current shape or use a default color
          Color shapeColor = shapeColors.getOrDefault(hexCoord, Color.GRAY);

          g2d.setColor(shapeColor);
          g2d.fill(hex);
          g2d.setColor(Color.BLACK);
          g2d.draw(hex);
          shapes.put(hexCoord, hex);

          if (Objects.equals(gameBoard.get(hexCoord).toString(), "X")) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int) centerX - 14, (int) centerY - 15, 30, 30);
          } else if (gameBoard.get(hexCoord).toString().equals("O")) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval((int) centerX - 14, (int) centerY - 15, 30, 30);
          }
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    gameBoard = model.getProviderBoard();
    // Recalculate scale factor
    scaleFactor = getScaleFactorCalc();
    g2d.scale(scaleFactor, scaleFactor);

    displayingBoard(g2d);
    if (!model.isGameOver()) {
      if (model.getCurrentTurn() == player.getColor()) {
        setBackground(Color.green.darker());
      } else {
        setBackground(Color.RED.darker());
      }
    } else {
      Optional<Piece> winner = model.getGameWinner();
      int playerScore = model.getScore(player.getColor());
      int opponentScore = model.getScore(player.getColor()
              == Piece.BLACK ? Piece.WHITE : Piece.BLACK);

      if (winner.isEmpty() || playerScore == opponentScore) {
        setBackground(Color.GRAY); // Draw
      } else if (winner.get() == player.getColor()) {
        setBackground(Color.GREEN); // Winner
      } else {
        setBackground(Color.GRAY); // Loser
      }
    }
  }

  /**
   * Creates a hexagon shape with the specified parameters.
   *
   * @param x The x-coordinate of the hexagon's center.
   * @param y The y-coordinate of the hexagon's center.
   * @param size The size of the hexagon.
   * @return The hexagon shape.
   */
  private Shape createHexagon(double x, double y, double size) {
    Path2D.Double hexagon = new Path2D.Double();
    double angle;
    for (int i = 0; i < 6; i++) {
      angle = Math.PI / 3 * i + Math.PI / 6;
      double xCoord = x + size * Math.cos(angle);
      double yCoord = y + size * Math.sin(angle);
      if (i == 0) {
        hexagon.moveTo(xCoord, yCoord);
      } else {
        hexagon.lineTo(xCoord, yCoord);
      }
    }
    hexagon.closePath();
    return hexagon;
  }
}