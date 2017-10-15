package com.dr0id3v.quotesondesign.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.dr0id3v.quotesondesign.logic.Quote;

public class DefaultServiceOnAlarmPresenter implements ServiceOnAlarmPresenter
{
  private final App app;
  private final AlarmManager alarmManager;
  private final PendingIntent alarmBroadcastIntent;
  private int previousUpdatePeriod;

  public DefaultServiceOnAlarmPresenter( App app )
  {
    this.app = app;
    alarmManager = (AlarmManager) app.getSystemService( Context.ALARM_SERVICE );

    Intent intent = new Intent( app, AlarmBroadcastReceiver.class );
    alarmBroadcastIntent = PendingIntent.getBroadcast(
      app, AlarmBroadcastReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
    );
    previousUpdatePeriod = app.getUpdatePeriodPresenter().getValue();
  }

  @Override
  public void handleServiceNewQuoteAlarms( int updatePeriod )
  {
    if ( updatePeriod > 0 )
    {
      Quote newQuote = app.getNewQuoteStorage().getNewQuote();
      int periodMillis = updatePeriod * 1000;
      if ( (newQuote == null) || (previousUpdatePeriod < 0) )
      { // If there was no quote or switching from Never setting,
        // we'll get quote right now
        alarmManager.setInexactRepeating(
          AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, periodMillis, alarmBroadcastIntent
        );
      }
      else
      { // Get quote on next period
        alarmManager.setInexactRepeating(
          AlarmManager.ELAPSED_REALTIME_WAKEUP,
          SystemClock.elapsedRealtime() + periodMillis,
          periodMillis,
          alarmBroadcastIntent
        );
      }
    }
    else
    {
      alarmManager.cancel( alarmBroadcastIntent );
    }
    previousUpdatePeriod = updatePeriod;
  }
}
