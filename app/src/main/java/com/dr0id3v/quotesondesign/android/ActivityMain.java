package com.dr0id3v.quotesondesign.android;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.preferences.PreferencePresenter;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.present.NewQuoteStoragePresenter;
import com.dr0id3v.quotesondesign.present.QuoteRepositoryPresenter;

public class ActivityMain extends AppCompatActivity
{
  private App app;
  private Toolbar appBar;
  private MenuItem miGetNewQuote;
  private TabLayout tabLayout;
  private FloatingActionButton fab;
  private ViewPager vpMain;
  private FragmentPagerAdapter adapterVpMain;
  private SwipeRefreshLayout swipeRefresh;

  private NewQuoteStoragePresenter newQuoteStorage;
  private QuoteRepositoryPresenter quoteRepository;
  private ServiceNewQuotePresenter serviceNewQuote;
  private PreferencePresenter<Boolean> dayNightPresenter;

  @Override
  protected void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_main );

    app = (App) getApplication();
    newQuoteStorage = app.getNewQuoteStorage();
    quoteRepository = app.getQuoteRepository();
    serviceNewQuote = app.getServiceNewQuotePresenter();
    dayNightPresenter = app.getDayNightPresenter();

    setupAppBar();
    setupVpMain();
    setupFab();
    setupSwipeRefresh();

    serviceNewQuote.addListener( serviceNewQuoteListener );
    dayNightPresenter.addListener( dayNightListener );
  }

  private void setupAppBar()
  {
    appBar = findViewById( R.id.toolbar );
    setSupportActionBar( appBar );
  }

  private void setupVpMain()
  {
    vpMain = findViewById( R.id.vpMain );
    adapterVpMain = new AdapterVpMain( getSupportFragmentManager() );
    vpMain.setAdapter( adapterVpMain );
    tabLayout = findViewById( R.id.tabs );
    tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener( tabLayout );
    vpMain.addOnPageChangeListener( tabLayoutOnPageChangeListener );
    tabLayout.addOnTabSelectedListener( tabSelectedListener );
  }

  private void setupFab()
  {
    fab = findViewById( R.id.fab );
    fab.setOnClickListener( fabClickListener );
    newQuoteStorage.addListener( fabNewQuoteListener );
    quoteRepository.addListener( fabQuiteRepositoryListener );
    handleFabState();
  }

  private void setupSwipeRefresh()
  {
    swipeRefresh = findViewById( R.id.swipeRefresh );
    // No need for swipe gesture
    swipeRefresh.setEnabled( false );
    // See onWindowFocusChanged()
  }

  @Override
  public void onWindowFocusChanged( boolean hasFocus )
  {
    int progressViewOffset =
      appBar.getHeight() + tabLayout.getHeight() + swipeRefresh.getProgressCircleDiameter();
    swipeRefresh.setProgressViewOffset( false, 0, progressViewOffset );
    if ( serviceNewQuote.isServiceRunning() )
    {
      swipeRefresh.setRefreshing( true );
    }
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    app.setActivityLaunched( true );
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    app.setActivityLaunched( false );
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    vpMain.removeOnPageChangeListener( tabLayoutOnPageChangeListener );
    tabLayout.removeOnTabSelectedListener( tabSelectedListener );
    vpMain.setAdapter( null );
    fab.setOnClickListener( null );
    newQuoteStorage.removeListener( fabNewQuoteListener );
    quoteRepository.removeListener( fabQuiteRepositoryListener );
    serviceNewQuote.removeListener( serviceNewQuoteListener );
    dayNightPresenter.removeListener( dayNightListener );
  }

  @Override
  public boolean onCreateOptionsMenu( Menu menu )
  {
    getMenuInflater().inflate( R.menu.activity_main, menu );
    miGetNewQuote = menu.findItem( R.id.miGetNewQuote );
    miGetNewQuote.setEnabled( !serviceNewQuote.isServiceRunning() );
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item )
  {
    switch ( item.getItemId() )
    {
      case R.id.miSettings:
        startActivity( new Intent(this, ActivityPreferences.class) );
        return true;

      case R.id.miGetNewQuote:
        serviceNewQuote.startService();
        return true;

      default:
        return super.onOptionsItemSelected( item );
    }
  }

  /** Fab should appear only for FragmentNewQuote. */
  private void animateFab( int position )
  {
    switch ( position )
    {
      case 0:
        fab.show();
        break;

      default:
        // Without this listener fab will never show up if was created and hidden
        fab.hide( fabVisibilityListener );
    }
  }

  private void handleFabState()
  {
    Quote newQuote = newQuoteStorage.getNewQuote();
    if ( newQuote == null )
    { // Cannot fave if no new quote available
      fab.setEnabled( false );
    }
    else
    { // Check if quote is in favorites to determine button style
      boolean isInFavorites = quoteRepository.query( newQuote.getId() ) != null;
      int colorStateListId = isInFavorites ? R.color.fab : R.color.fab_grey;
      Resources resources = getResources();
      if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
      {
        fab.setBackgroundTintList( resources.getColorStateList(colorStateListId, getTheme()) );
      }
      else
      {
        fab.setBackgroundTintList( resources.getColorStateList(colorStateListId) );
      }
      fab.setEnabled( true );
    }
  }

  private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener;

  private final TabLayout.OnTabSelectedListener tabSelectedListener =
    new TabLayout.OnTabSelectedListener()
    {
      @Override
      public void onTabSelected( TabLayout.Tab tab )
      {
        int position = tab.getPosition();
        vpMain.setCurrentItem( position );
        animateFab( position );
      }

      @Override
      public void onTabUnselected( TabLayout.Tab tab ) {}

      @Override
      public void onTabReselected( TabLayout.Tab tab ) {}
    };

  private final View.OnClickListener fabClickListener =
    view ->
    {
      Quote newQuote = newQuoteStorage.getNewQuote();
      Quote favQuote = quoteRepository.query( newQuote.getId() );
      boolean isInFavorites = favQuote != null;

      if ( isInFavorites )
      { // Remove from favorites
        quoteRepository.remove( favQuote );
      }
      else
      { // Add to favorites
        quoteRepository.put( newQuote );
      }
    };

  private final NewQuoteStoragePresenter.Listener fabNewQuoteListener =
    ( newQuote, date ) -> handleFabState();

  private final QuoteRepositoryPresenter.Listener fabQuiteRepositoryListener =
    new QuoteRepositoryPresenter.Listener()
    {
      @Override
      public void onQuoteAdded( int position ) { handleFabState(); }

      @Override
      public void onQuoteRemoved( int position ) { handleFabState(); }
    };

  /** Workaround to set visibility to Invisible rather than default Gone. */
  private final FloatingActionButton.OnVisibilityChangedListener fabVisibilityListener =
    new FloatingActionButton.OnVisibilityChangedListener()
    {
      @Override
      public void onHidden( FloatingActionButton fab )
      {
        super.onHidden( fab );
        fab.setVisibility( View.INVISIBLE );
      }
    };

  private final ServiceNewQuotePresenter.Listener serviceNewQuoteListener =
    new ServiceNewQuotePresenter.Listener()
    {
      @Override
      public void onStart()
      {
        miGetNewQuote.setEnabled( false );
        swipeRefresh.setRefreshing( true );
      }

      @Override
      public void onStop( boolean result )
      {
        miGetNewQuote.setEnabled( true );
        swipeRefresh.setRefreshing( false );
        if ( !result )
        {
          Snackbar.make( fab, R.string.noInternetConnection, Snackbar.LENGTH_LONG ).show();
        }
      }
    };

  private final PreferencePresenter.Listener<Boolean> dayNightListener =
    newValue -> recreate();
}
