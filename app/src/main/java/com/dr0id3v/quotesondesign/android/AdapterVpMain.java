package com.dr0id3v.quotesondesign.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class AdapterVpMain extends FragmentPagerAdapter
{
  private final FragmentNewQuote fragmentNewQuote = new FragmentNewQuote();
  private final FragmentFavoriteQuotes fragmentFavoriteQuotes = new FragmentFavoriteQuotes();

  AdapterVpMain( FragmentManager fm ) { super( fm ); }

  @Override
  public Fragment getItem( int position )
  {
    switch ( position )
    {
      case 0: return fragmentNewQuote;
      case 1: return fragmentFavoriteQuotes;
      default: return null;
    }
  }

  @Override
  public int getCount() { return 2; }
}
