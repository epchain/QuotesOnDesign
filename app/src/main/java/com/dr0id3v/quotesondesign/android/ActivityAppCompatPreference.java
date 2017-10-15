package com.dr0id3v.quotesondesign.android;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A {@link android.preference.PreferenceActivity} which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
public abstract class ActivityAppCompatPreference extends PreferenceActivity
{
  private AppCompatDelegate mDelegate;
  /** Needed for day-night theme switching. */
  private int themeId;

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    final AppCompatDelegate delegate = getDelegate();
    delegate.installViewFactory();
    delegate.onCreate( savedInstanceState );
    if ( delegate.applyDayNight() && (themeId != 0) )
    {
      onApplyThemeResource( getTheme(), themeId, false );
    }
    super.onCreate( savedInstanceState );
  }

  @Override
  protected void onPostCreate( Bundle savedInstanceState )
  {
    super.onPostCreate( savedInstanceState );
    getDelegate().onPostCreate( savedInstanceState );
  }

  @Override
  protected void onPostResume()
  {
    super.onPostResume();
    getDelegate().onPostResume();
  }

  @Override
  protected void onTitleChanged( CharSequence title, int color )
  {
    super.onTitleChanged( title, color );
    getDelegate().setTitle( title );
  }

  @Override
  public void onConfigurationChanged( Configuration newConfig )
  {
    super.onConfigurationChanged( newConfig );
    getDelegate().onConfigurationChanged( newConfig );
  }

  @Override
  protected void onStop()
  {
    super.onStop();
    getDelegate().onStop();
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    getDelegate().onDestroy();
  }

  @Override
  public void setTheme( int themeId )
  {
    super.setTheme( themeId );
    this.themeId = themeId;
  }

  public ActionBar getSupportActionBar()
  {
    return getDelegate().getSupportActionBar();
  }

  public void setSupportActionBar( @Nullable Toolbar toolbar )
  {
    getDelegate().setSupportActionBar( toolbar );
  }

  @NonNull
  @Override
  public MenuInflater getMenuInflater()
  {
    return getDelegate().getMenuInflater();
  }

  @Override
  public void setContentView( @LayoutRes int layoutResID )
  {
    getDelegate().setContentView( layoutResID );
  }

  @Override
  public void setContentView( View view )
  {
    getDelegate().setContentView( view );
  }

  @Override
  public void setContentView( View view, ViewGroup.LayoutParams params )
  {
    getDelegate().setContentView( view, params );
  }

  @Override
  public void addContentView( View view, ViewGroup.LayoutParams params )
  {
    getDelegate().addContentView( view, params );
  }

  @Override
  public void invalidateOptionsMenu()
  {
    getDelegate().invalidateOptionsMenu();
  }

  private AppCompatDelegate getDelegate()
  {
    if ( mDelegate == null )
    {
      mDelegate = AppCompatDelegate.create( this, null );
    }
    return mDelegate;
  }
}
