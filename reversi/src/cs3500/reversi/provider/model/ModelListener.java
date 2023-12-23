/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

/**
 * Defines a simple interface for classes interested in receiving notifications
 * about changes in the status of a model.
 */
public interface ModelListener {

  /**
   * Notifies the implementing class about changes in the status of the associated model.
   */
  void notifyStatus();
}
