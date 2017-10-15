package com.dr0id3v.quotesondesign.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.dr0id3v.quotesondesign.android.notifications.DefaultNotificationsPresenter;
import com.dr0id3v.quotesondesign.android.notifications.NotificationsPresenter;
import com.dr0id3v.quotesondesign.android.preferences.DayNightPresenter;
import com.dr0id3v.quotesondesign.android.preferences.NewQuoteNotificationsPresenter;
import com.dr0id3v.quotesondesign.android.preferences.PreferencePresenter;
import com.dr0id3v.quotesondesign.android.preferences.UpdateMethodPresenter;
import com.dr0id3v.quotesondesign.android.preferences.UpdatePeriodPresenter;
import com.dr0id3v.quotesondesign.android.rest.QuoteRetrieverRetrofit;
import com.dr0id3v.quotesondesign.android.storage.NewQuoteStorageObjectBox;
import com.dr0id3v.quotesondesign.android.storage.QuoteRepositoryObjectBox;
import com.dr0id3v.quotesondesign.logic.QuoteRetriever;
import com.dr0id3v.quotesondesign.present.NewQuoteStoragePresenter;
import com.dr0id3v.quotesondesign.present.QuoteRepositoryPresenter;

public class App extends Application
{
  private SharedPreferences sharedPreferences;
  private QuoteRepositoryPresenter quoteRepository;
  private NewQuoteStoragePresenter newQuoteStorage;
  private PreferencePresenter<Integer> updatePeriodPresenter;
  private PreferencePresenter<Integer> updateMethodPresenter;
  private PreferencePresenter<Boolean> newQuoteNotificationsPresenter;
  private PreferencePresenter<Boolean> dayNightPresenter;
  private QuoteRetriever quoteRetriever;
  private ServiceNewQuotePresenter serviceNewQuotePresenter;
  private ServiceOnAlarmPresenter serviceOnAlarmPresenter;
  private NotificationsPresenter notificationsPresenter;

  private boolean isActivityLaunched = false;

  public SharedPreferences getSharedPreferences()
  {
    return sharedPreferences;
  }

  public QuoteRepositoryPresenter getQuoteRepository()
  {
    return quoteRepository;
  }

  public NewQuoteStoragePresenter getNewQuoteStorage()
  {
    return newQuoteStorage;
  }

  public PreferencePresenter<Integer> getUpdatePeriodPresenter()
  {
    return updatePeriodPresenter;
  }

  public PreferencePresenter<Integer> getUpdateMethodPresenter()
  {
    return updateMethodPresenter;
  }

  public PreferencePresenter<Boolean> getNewQuoteNotificationsPresenter()
  {
    return newQuoteNotificationsPresenter;
  }

  public PreferencePresenter<Boolean> getDayNightPresenter()
  {
    return dayNightPresenter;
  }

  public QuoteRetriever getQuoteRetriever()
  {
    return quoteRetriever;
  }

  public ServiceNewQuotePresenter getServiceNewQuotePresenter()
  {
    return serviceNewQuotePresenter;
  }

  public NotificationsPresenter getNotificationsPresenter()
  {
    return notificationsPresenter;
  }

  public boolean isActivityLaunched()
  {
    return isActivityLaunched;
  }

  public void setActivityLaunched( boolean isActivityLaunched )
  {
    this.isActivityLaunched = isActivityLaunched;
  }

  @Override
  public void onCreate()
  {
    super.onCreate();

    sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
    quoteRepository = new QuoteRepositoryObjectBox( this );
    newQuoteStorage = new NewQuoteStorageObjectBox( this );
    updatePeriodPresenter = new UpdatePeriodPresenter( this );
    updateMethodPresenter = new UpdateMethodPresenter( this );
    newQuoteNotificationsPresenter = new NewQuoteNotificationsPresenter( this );
    dayNightPresenter = new DayNightPresenter( this );
    quoteRetriever = new QuoteRetrieverRetrofit( this );
    serviceNewQuotePresenter = new DefaultServiceNewQuotePresenter( this );
    serviceOnAlarmPresenter = new DefaultServiceOnAlarmPresenter( this );
    notificationsPresenter = new DefaultNotificationsPresenter( this );

    updatePeriodPresenter.addListener( updatePeriodListener );
    dayNightPresenter.addListener( dayNightListener );
    serviceOnAlarmPresenter.handleServiceNewQuoteAlarms( updatePeriodPresenter.getValue() );

    applyDayNight( dayNightPresenter.getValue() );
  }

  @Override
  public void onTerminate()
  {
    super.onTerminate();
    quoteRepository.clearListeners();
    newQuoteStorage.clearListeners();
    updatePeriodPresenter.destroy();
    updateMethodPresenter.destroy();
    newQuoteNotificationsPresenter.destroy();
    dayNightPresenter.destroy();
    serviceNewQuotePresenter.destroy();
  }

  private void applyDayNight( boolean isNightMode )
  {
    int mode = isNightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
    AppCompatDelegate.setDefaultNightMode( mode );
  }

  private final PreferencePresenter.Listener<Integer> updatePeriodListener =
    newValue -> serviceOnAlarmPresenter.handleServiceNewQuoteAlarms( newValue );

  private final PreferencePresenter.Listener<Boolean> dayNightListener =
    this::applyDayNight;
}
