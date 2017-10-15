package com.dr0id3v.quotesondesign.android.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;
import com.dr0id3v.quotesondesign.android.FavoriteActionBroadcastReceiver;
import com.dr0id3v.quotesondesign.android.SplashActivity;
import com.dr0id3v.quotesondesign.logic.Quote;

/** Default {@link NotificationsPresenter}. */
public class DefaultNotificationsPresenter implements NotificationsPresenter
{
  private final App app;
  private final NotificationManager notificationManager;
  private final NotificationCompat.Builder notificationBuilder;
  /**
   * ID for single message.
   * It's fine to post single new notification and update it, if it becomes stale.
   */
  private final int messageId = 0;

  public DefaultNotificationsPresenter( App app )
  {
    this.app = app;
    notificationManager =
      (NotificationManager) app.getSystemService( Context.NOTIFICATION_SERVICE );
    String favorite = app.getResources().getString( R.string.favorite );

    // Since API 26 we need to manage notification channels
    if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O )
    {
      Resources resources = app.getResources();
      String channelId = resources.getString( R.string.notificationsChannelId );
      String channelName = resources.getString( R.string.notificationsChannelName );
      String channelDescription = resources.getString( R.string.notificationsChannelDescription );

      NotificationChannel quotesChannel = new NotificationChannel(
        channelId, channelName, NotificationManager.IMPORTANCE_LOW
      );
      quotesChannel.setDescription( channelDescription );
      // Channel gets registered by systems and persists until app cache cleared or uninstalled.
      // It is fine to recreate it on each app launch, because it won't change.
      notificationManager.createNotificationChannel( quotesChannel );
      // Use new builder with vector icon
      notificationBuilder = new NotificationCompat.Builder( app, channelId )
        .setSmallIcon( R.drawable.ic_format_quote_white_24dp )
        .addAction( R.drawable.ic_favorite_white_24dp, favorite, createFavoriteActionIntent() );
    }
    else
    {
      // Use deprecated builder with image icon
      notificationBuilder = new NotificationCompat.Builder( app )
        .setSmallIcon( R.drawable.ic_format_quote_white_24dp_png )
        .addAction( R.drawable.ic_favorite_white_24dp_png, favorite, createFavoriteActionIntent() );
    }
    notificationBuilder.setContentIntent( createContentIntent() );
    notificationBuilder.setAutoCancel( true );
  }

  /** Create activity launch on notification click intent. */
  private PendingIntent createContentIntent()
  {
    Intent intent = new Intent( app, SplashActivity.class );
    int requestId = (int) System.currentTimeMillis();
    // Use TaskStackBuilder to make activity go back
    // to Home screen, when navigating back from it
    TaskStackBuilder stackBuilder = TaskStackBuilder.create( app );
    stackBuilder.addParentStack( SplashActivity.class );
    stackBuilder.addNextIntent( intent );
    return stackBuilder.getPendingIntent(
      requestId, PendingIntent.FLAG_UPDATE_CURRENT
    );
  }

  /** Create intent to launch {@link FavoriteActionBroadcastReceiver}. */
  private PendingIntent createFavoriteActionIntent()
  {
    Intent intent = new Intent( app, FavoriteActionBroadcastReceiver.class );
    return PendingIntent.getBroadcast(
      app, FavoriteActionBroadcastReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
    );
  }

  @Override
  public void notifyOnNewQuote( Quote quote )
  {
    notificationBuilder.setContentTitle( quote.getTitle() ).setContentText( quote.getContent() );
    notificationManager.notify( messageId, notificationBuilder.build() );
  }

  @Override
  public void cancelNotification()
  {
    notificationManager.cancel( messageId );
  }
}
