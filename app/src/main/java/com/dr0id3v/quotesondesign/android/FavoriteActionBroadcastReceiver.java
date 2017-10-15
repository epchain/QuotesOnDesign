package com.dr0id3v.quotesondesign.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.logic.Quote;

/** Broadcast receiver, that will add current new quote to favorite. */
public class FavoriteActionBroadcastReceiver extends BroadcastReceiver
{
  public static final int REQUEST_CODE = 1;

  @Override
  public void onReceive( Context context, Intent intent )
  {
    App app = (App) context.getApplicationContext();
    Quote newQuote = app.getNewQuoteStorage().getNewQuote();
    Quote favQuote = app.getQuoteRepository().query( newQuote.getId() );
    boolean isInFavorites = favQuote != null;

    if ( !isInFavorites )
    {
      boolean ok = app.getQuoteRepository().put( newQuote );
      if ( ok )
      {
        Toast.makeText( context, R.string.addedToFavorites, Toast.LENGTH_LONG ).show();
      }
      app.getNotificationsPresenter().cancelNotification();
    }
  }
}
