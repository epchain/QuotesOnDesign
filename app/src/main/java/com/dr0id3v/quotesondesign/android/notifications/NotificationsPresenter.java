package com.dr0id3v.quotesondesign.android.notifications;

import com.dr0id3v.quotesondesign.logic.Quote;

/** Presenter for notifications facility. */
public interface NotificationsPresenter
{
  /** Posts notification of new quote available. */
  void notifyOnNewQuote( Quote quote );

  /** Cancels currently visible notification. */
  void cancelNotification();
}
