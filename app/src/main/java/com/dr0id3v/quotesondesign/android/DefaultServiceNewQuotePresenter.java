package com.dr0id3v.quotesondesign.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dr0id3v.quotesondesign.present.SimplePresenter;

/**
 * This presenter makes use of IntentService's behaviour
 * of launching tasks sequentially and allows only single
 * service to run when multiple requests come.
 */
public class DefaultServiceNewQuotePresenter
  extends SimplePresenter<ServiceNewQuotePresenter.Listener>
  implements ServiceNewQuotePresenter
{
  private final App app;
  private boolean isServiceRunning = false;

  public DefaultServiceNewQuotePresenter( App app )
  {
    this.app = app;
    LocalBroadcastManager.getInstance( app ).registerReceiver(
      serviceNewQuoteReceiver, new IntentFilter( ServiceNewQuote.ACTION )
    );
  }

  @Override
  public boolean isServiceRunning()
  {
    return isServiceRunning;
  }

  @Override
  public void startService()
  {
    if ( !isServiceRunning() )
    {
      isServiceRunning = true;
      for ( ServiceNewQuotePresenter.Listener listener : super.listeners )
      {
        listener.onStart();
      }
      app.startService( new Intent(app, ServiceNewQuote.class) );
    }
  }

  @Override
  public void destroy()
  {
    clearListeners();
    LocalBroadcastManager.getInstance( app ).unregisterReceiver( serviceNewQuoteReceiver );
  }

  private final BroadcastReceiver serviceNewQuoteReceiver =
    new BroadcastReceiver()
    {
      @Override
      public void onReceive( Context context, Intent intent )
      {
        isServiceRunning = false;
        boolean result = intent.getBooleanExtra( ServiceNewQuote.RESULT_KEY, false );
        for ( ServiceNewQuotePresenter.Listener listener : listeners )
        {
          listener.onStop( result );
        }
      }
    };
}
