package com.dr0id3v.quotesondesign.android;

import com.dr0id3v.quotesondesign.present.Presenter;

/** Presents Android service that performs retrieving of new quote. */
public interface ServiceNewQuotePresenter
  extends Presenter<ServiceNewQuotePresenter.Listener>
{
  /** Listener for service events. */
  interface Listener
  {
    /** Called, when service started. */
    void onStart();

    /** Called, when service stopped. */
    void onStop( boolean result );
  }

  /** Returns true, if service currently running. */
  boolean isServiceRunning();

  /** Starts service. */
  void startService();

  /** Destroys presenter, making it unusable. */
  void destroy();
}
