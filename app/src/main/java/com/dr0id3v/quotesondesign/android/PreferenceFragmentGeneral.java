package com.dr0id3v.quotesondesign.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.preferences.PreferencePresenter;

@TargetApi( Build.VERSION_CODES.HONEYCOMB )
public class PreferenceFragmentGeneral extends PreferenceFragment
{
  private PreferencePresenter<Integer> updatePeriodPresenter;
  private PreferencePresenter<Integer> updateMethodPresenter;
  private ListPreference updatePeriodPreference;
  private ListPreference updateMethodPreference;

  public PreferenceFragmentGeneral() {}

  @Override
  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    addPreferencesFromResource( R.xml.preferences_general );

    App app = (App) getActivity().getApplication();
    updatePeriodPresenter = app.getUpdatePeriodPresenter();
    updateMethodPresenter = app.getUpdateMethodPresenter();

    setupUpdatePeriodPreference();
    setupUpdateMethodPreference();
  }

  private void setupUpdatePeriodPreference()
  {
    updatePeriodPreference = (ListPreference) findPreference(
      getResources().getString( R.string.prefUpdatePeriodKey )
    );
    updatePeriodPreference.setOnPreferenceChangeListener( listPreferenceListener );
    Android.setListPreferenceSummary(
      updatePeriodPreference, updatePeriodPresenter.getStringValue()
    );
  }

  private void setupUpdateMethodPreference()
  {
    updateMethodPreference = (ListPreference) findPreference(
      getResources().getString( R.string.prefUpdateMethodKey )
    );
    updateMethodPreference.setOnPreferenceChangeListener( listPreferenceListener );
    Android.setListPreferenceSummary(
      updateMethodPreference, updateMethodPresenter.getStringValue()
    );
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    updatePeriodPreference.setOnPreferenceChangeListener( null );
    updateMethodPreference.setOnPreferenceChangeListener( null );
  }

  private static final Preference.OnPreferenceChangeListener listPreferenceListener =
    ( preference, newValue ) ->
    {
      Android.setListPreferenceSummary( (ListPreference) preference, newValue.toString() );
      return true;
    };
}
