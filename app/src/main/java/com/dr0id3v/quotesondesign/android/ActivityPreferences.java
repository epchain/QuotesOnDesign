package com.dr0id3v.quotesondesign.android;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.preferences.PreferencePresenter;

public class ActivityPreferences extends ActivityAppCompatPreference
{
  private PreferencePresenter<Boolean> dayNightPresenter;

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_preferences );

    setupActionBar();
    App app = (App) getApplication();
    dayNightPresenter = app.getDayNightPresenter();
    dayNightPresenter.addListener( dayNightListener );
  }

  /** Set up the {@link android.app.ActionBar}, if the API is available. */
  private void setupActionBar()
  {
    ActionBar actionBar = getSupportActionBar();
    if ( actionBar != null )
    {
      // Show the Up button in the action bar.
      actionBar.setDisplayHomeAsUpEnabled( true );
    }
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    dayNightPresenter.removeListener( dayNightListener );
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item )
  {
    switch ( item.getItemId() )
    {
      case android.R.id.home:
        onBackPressed();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean onIsMultiPane()
  {
    return Android.isXLargeTablet( this );
  }

  /**
   * This method stops fragment injection in malicious applications.
   * Make sure to deny any unknown fragments here.
   */
  @Override
  protected boolean isValidFragment( String fragmentName )
  {
    return PreferenceFragment.class.getName().equals( fragmentName )
           || PreferenceFragmentGeneral.class.getName().equals( fragmentName );
  }

  private final PreferencePresenter.Listener<Boolean> dayNightListener =
    newValue -> recreate();
}
