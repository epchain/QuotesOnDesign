package com.dr0id3v.quotesondesign.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Broadcast receiver, that will start {@link ServiceNewQuote}. */
public class AlarmBroadcastReceiver extends BroadcastReceiver
{
  public static final int REQUEST_CODE = 0;

  @Override
  public void onReceive( Context context, Intent intent)
  {
    App app = (App) context.getApplicationContext();
    app.getServiceNewQuotePresenter().startService();
  }
}
