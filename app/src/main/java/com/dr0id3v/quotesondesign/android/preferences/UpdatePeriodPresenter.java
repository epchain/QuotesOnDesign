package com.dr0id3v.quotesondesign.android.preferences;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;

public class UpdatePeriodPresenter extends BasePreferencePresenter<Integer>
{
  private final String stringDefaultValue;

  public UpdatePeriodPresenter( App app )
  {
    super( app, R.string.prefUpdatePeriodKey );
    stringDefaultValue = super.resources.getString( R.string.prefUpdatePeriodDefault );
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
