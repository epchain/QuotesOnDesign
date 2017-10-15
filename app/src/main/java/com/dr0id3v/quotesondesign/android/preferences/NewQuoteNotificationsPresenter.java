package com.dr0id3v.quotesondesign.android.preferences;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;

public class NewQuoteNotificationsPresenter extends BasePreferencePresenter<Boolean>
{
  public NewQuoteNotificationsPresenter( App app )
  {
    super( app, R.string.prefNewQuoteNotificationsKey );
  }

  @Override
  public Boolean getDefaultValue()
  {
    return super.resources.getBoolean( R.bool.prefNewQuoteNotificationsDefault );
  }

  @Override
  public Boolean getValue()
  {
    return sharedPreferences.getBoolean( getKey(), getDefaultValue() );
  }

  @Override
  public String getStringValue()
  {
    return String.valueOf( getValue() );
  }
}