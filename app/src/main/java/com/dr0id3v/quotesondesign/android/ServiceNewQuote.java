package com.dr0id3v.quotesondesign.android;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.notifications.NotificationsPresenter;
import com.dr0id3v.quotesondesign.android.preferences.PreferencePresenter;
import com.dr0id3v.quotesondesign.android.preferences.UpdateMethodPresenter;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.logic.QuoteRetriever;
import com.dr0id3v.quotesondesign.present.NewQuoteStoragePresenter;

/** Service for obtaining new quote. */
public class ServiceNewQuote extends IntentService
{
  public static final String ACTION = ServiceNewQuote.class.getName();
  public static final String RESULT_KEY = "result";

  private App app;
  private NewQuoteStoragePresenter newQuoteStorage;
  private PreferencePresenter<Integer> updateMethodPresenter;
  private PreferencePresenter<Boolean> newQuoteNotificationsPresenter;
  private QuoteRetriever quoteRetriever;
  private NotificationsPresenter notificationsPresenter;
  private int serverCooldownSeconds;

  public ServiceNewQuote()
  {
    super( ServiceNewQuote.class.getClass().getSimpleName() );
  }

  @Override
  public void onCreate()
  {
    super.onCreate();
    app = (App) getApplication();
    newQuoteStorage = app.getNewQuoteStorage();
    updateMethodPresenter = app.getUpdateMethodPresenter();
    newQuoteNotificationsPresenter = app.getNewQuoteNotificationsPresenter();
    quoteRetriever = app.getQuoteRetriever();
    notificationsPresenter = app.getNotificationsPresenter();
    serverCooldownSeconds = app.getResources().getInteger( R.integer.serverCooldownSeconds );
  }

  @Override
  protected void onHandleIntent( @Nullable Intent intent )
  {
    int updateMethod = updateMethodPresenter.getValue();
    QuoteRetriever.Method method = UpdateMethodPresenter.updateMethodFromInt( updateMethod );
    final boolean isNotificationsEnabled = newQuoteNotificationsPresenter.getValue();

    try
    { // Protect server from spamming
      Thread.sleep( serverCooldownSeconds * 1000 );
    }
    catch ( InterruptedException e )
    {
      Thread.currentThread().interrupt();
    }

    final Quote newQuote = quoteRetriever.getNewQuote( method );

    boolean result = false;

    if ( newQuote != null )
    { // Received new quote
      boolean doUpdate = doUpdate( method, newQuote );

      if ( doUpdate )
      {
        new Handler( app.getMainLooper() ).post( () -> newQuoteStorage.updateNewQuote(newQuote) );
      }

      if ( isNotificationsEnabled && !app.isActivityLaunched() && doUpdate )
      {
        notificationsPresenter.notifyOnNewQuote( newQuote );
      }

      result = true;
    }

    Intent resultIntent = new Intent( ACTION );
    resultIntent.putExtra( RESULT_KEY, result );
    LocalBroadcastManager.getInstance( app ).sendBroadcast( resultIntent );
  }

  /** Determines if it is necessary to update new quote. */
  private boolean doUpdate( QuoteRetriever.Method method, Quote newQuote )
  {
    switch ( method )
    {
      case Latest: // For Latest method we need to check if quote is really latest
        Quote currentQuote = newQuoteStorage.getNewQuote();
        return currentQuote == null || ( currentQuote.getId() != newQuote.getId() );

      default:
        return true;
    }
  }
}
