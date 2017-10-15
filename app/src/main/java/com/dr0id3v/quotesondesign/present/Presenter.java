package com.dr0id3v.quotesondesign.present;

/** Presenter interface. */
public interface Presenter<L>
{
  /**
   * Adds listener for set of events.
   * @param listener listener to add
   * @return true, if successfully added
   */
  boolean addListener( L listener );

  /**
   * Removes listener.
   * @param listener listener to remove
   * @return true, if listener existed and was deleted
   */
  boolean removeListener( L listener );

  /** Removes all listeners. */
  void clearListeners();
}
