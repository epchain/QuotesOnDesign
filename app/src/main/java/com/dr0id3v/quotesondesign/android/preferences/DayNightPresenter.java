package com.dr0id3v.quotesondesign.android.preferences;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;

public class DayNightPresenter extends BasePreferencePresenter<Boolean>
{
  public DayNightPresenter( App app )
  {
    super( app, R.string.prefDayNightKey );
  }

  @Override
  public Boolean getDefaultValue()
  {
    return super.resources.getBoolean( R.bool.prefDayNightDefault );
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
