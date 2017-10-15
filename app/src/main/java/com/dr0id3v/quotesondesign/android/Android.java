package com.dr0id3v.quotesondesign.android;

import android.content.Context;
import android.content.res.Configuration;
import android.preference.ListPreference;

/** Helper methods from Android platform. */
public final class Android
{
  /** Sets summary for ListPreference based on current value. */
  public static void setListPreferenceSummary( ListPreference preference, Object value )
  {
    String stringValue = value.toString();
    int index = preference.findIndexOfValue( stringValue );
    preference.setSummary( index >= 0 ? preference.getEntries()[ index ] : null );
  }

  /**
   * Helper method to determine if the device has an extra-large screen.
   * For example, 10" tablets are extra-large.
   */
  public static boolean isXLargeTablet( Context context )
  {
    Configuration config = context.getResources().getConfiguration();
    int screenLayoutSize = config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    return screenLayoutSize >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
  }
}
