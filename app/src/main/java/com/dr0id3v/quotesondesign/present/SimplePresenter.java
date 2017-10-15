package com.dr0id3v.quotesondesign.present;

import java.util.LinkedList;
import java.util.List;

/** Presenter with listeners storage implemented with LinkedList. */
public class SimplePresenter<L> implements Presenter<L>
{
  protected final List<L> listeners = new LinkedList<>();

  @Override
  public boolean addListener( L listener )
  {
    return listeners.add( listener );
  }

  @Override
  public boolean removeListener( L listener )
  {
    return listeners.remove( listener );
  }

  @Override
  public void clearListeners()
  {
    listeners.clear();
  }
}
