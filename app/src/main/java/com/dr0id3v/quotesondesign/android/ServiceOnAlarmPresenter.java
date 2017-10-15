package com.dr0id3v.quotesondesign.android;

/** Presenter for scheduling periodical {@link ServiceNewQuote} launches. */
public interface ServiceOnAlarmPresenter
{
  /**
   * Performs setup of {@link android.app.AlarmManager}
   * according to update period value.
   * @param updatePeriod current update period value
   */
  void handleServiceNewQuoteAlarms( int updatePeriod );
}
