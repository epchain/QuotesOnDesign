package com.dr0id3v.quotesondesign.android.preferences;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;
import com.dr0id3v.quotesondesign.logic.QuoteRetriever;

public class UpdateMethodPresenter extends BasePreferencePresenter<Integer>
{
  private final String stringDefaultValue;

  public UpdateMethodPresenter( App app )
  {
    super( app, R.string.prefUpdateMethodKey );
    stringDefaultValue = super.resources.getString( R.string.prefUpdateMethodDefault );
  }

  public static QuoteRetriever.Method updateMethodFromInt( int updateMethod )
  {
    switch ( updateMethod )
    {
      case 0: return QuoteRetriever.Method.Latest;
      case 1: return QuoteRetriever.Method.Random;
      default: throw new IllegalArgumentException( "Unknown update method" );
    }
  }

  @Override
  public Integer getDefaultValue()
  {
    return Integer.valueOf( stringDefaultValue );
  }

  @Override
  public Integer getValue()
  {
    return Integer.valueOf( getStringValue() );
  }

  @Override
  public String getStringValue()
  {
    return sharedPreferences.getString( getKey(), stringDefaultValue );
  }
}