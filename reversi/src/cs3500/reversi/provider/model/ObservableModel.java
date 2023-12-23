/**
 * PROVIDER CODE:
 * This code was provided by another group. We did not write this code.
 */

package cs3500.reversi.provider.model;

/**
 * Represents a model that can be observed by views. Allows views to register themselves as
 * observers and be notified when the model changes.
 */

public interface ObservableModel {

  /**
   * Adds an observer to the list of observers. This observer will be notified of any changes to the
   * game state.
   *
   * @param listener The observer to be added, which is an implementation of the IView interface.
   */
  void addObserver(ModelListener listener);

  /**
   * Notifies all registered observers of a change in the game state. Each observer will update its
   * view to reflect the new game state.
   */
  void notifyObservers();
}
