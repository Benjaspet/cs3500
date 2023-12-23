/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.view;

import java.awt.Component;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.reversi.provider.model.AxialCoordinate;
import cs3500.reversi.provider.model.ReadOnlyReversiInterface;
import cs3500.reversi.provider.player.IPlayer;

/**
 * The ReversiBoardFrame class represents the main JFrame for the Reversi game.
 * It implements the IView interface and is responsible for displaying the game board.
 * The frame includes a ReversiBoardPanel that visualizes the Reversi game board.
 * The size of the frame is dynamically set based on the provided ReadOnlyReversiInterfaces
 * radius to ensure sufficient space for the game board.
 */
public class ReversiBoardFrame extends JFrame implements IView {

  ReadOnlyReversiInterface model;
  ReversiBoardPanel reversiBoardPanel;

  IPlayer player;

  /**
   * Constructs a new ReversiBoardFrame with the specifies ReadOnlyReversiInterface to make
   * the frame as large as the board's size (plus some room in the bottom to display results).
   *
   * @param model The read-only interface to the Reversi game data.
   */
  public ReversiBoardFrame(ReadOnlyReversiInterface model, IPlayer player) {
    this.setTitle("Reversi Game" + " - " + player.getName());

    this.setSize(((model.getRadius() * 2) + 1) * 52, (model.getRadius() + 1) * 2 * 60);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Use BorderLayout for the main frame
    this.setLayout(new BorderLayout());

    // Add ReversiBoardPanel to the center
    reversiBoardPanel = new ReversiBoardPanel(model, player);
    reversiBoardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    this.add(reversiBoardPanel, BorderLayout.CENTER);

    this.setVisible(true);

  }

  @Override
  public AxialCoordinate getCellClicked() {
    if (reversiBoardPanel.getClickedCell().isPresent()) {
      return reversiBoardPanel.getClickedCell().get();
    }
    throw new IllegalStateException("Invalid Cell");
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void showMessageDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}