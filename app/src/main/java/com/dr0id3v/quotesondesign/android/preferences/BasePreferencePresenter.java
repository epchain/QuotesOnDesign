package com.dr0id3v.quotesondesign.android.preferences;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.dr0id3v.quotesondesign.android.App;
import com.dr0id3v.quotesondesign.present.SimplePresenter;


/** Base presenter for preferences with string keys. */
public abstract class BasePreferencePresenter<T>
  extends SimplePresenter<PreferencePresenter.Listener<T>>
  implements PreferencePresenter<T>
{
  private final String key;
  protected final SharedPreferences sharedPreferences;
  protected final Resources resources;

  /**
   * Default constructor.
   * @param app app reference
   * @param keyId preference key id to string, like R.string.someId
   */
  public BasePreferencePresenter( App app, int keyId )
  {
    sharedPreferences = app.getSharedPreferences();
    resources = app.getResources();
    key = resources.getString( keyId );
    sharedPreferences.registerOnSharedPreferenceChangeListener( sharedPreferenceChangeListener );
  }

  @Override
  public String getKey()
  {
    return key;
  }

  @Override
  public void destroy()
  {
    clearListeners();
    sharedPreferences.unregisterOnSharedPreferenceChangeListener( sharedPreferenceChangeListener );
  }

  private final SharedPreferences.OnSharedPreferenceChangeListener
    sharedPreferenceChangeListener =
    ( sharedPreferences, key ) ->
    {
      if ( key.equals(getKey()) )
      {
        for ( Listener<T> listener : super.listeners )
        {
          listener.onChange( getValue() );
        }
      }
    };
}
