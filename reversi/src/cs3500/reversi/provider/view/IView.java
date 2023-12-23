/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.view;

import java.awt.event.KeyListener;

import cs3500.reversi.provider.model.AxialCoordinate;

/**
 * Represents an interface that can contains general classes that are used within typical JFrames.
 * It has all the methods fit for passing to other classes for the basics to implement a GUI.
 */
public interface IView {

  AxialCoordinate getCellClicked();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   * Thus, our Swing-based implementation of this interface will already have such a method.
   */
  void addKeyListener(KeyListener listener);

  void showMessageDialog(String message);
}