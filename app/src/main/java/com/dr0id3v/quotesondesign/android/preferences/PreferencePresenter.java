package com.dr0id3v.quotesondesign.android.preferences;

import com.dr0id3v.quotesondesign.present.Presenter;

/** Presenter for preference. */
public interface PreferencePresenter<T> extends Presenter<PreferencePresenter.Listener<T>>
{
  /** Presenter events listener */
  interface Listener<T>
  {
    /**
     * Called when preference changed.
     * @param newValue new preference value
     */
    void onChange( T newValue );
  }

  /** Returns preference key. */
  String getKey();

  /** Returns default preference value. */
  T getDefaultValue();

  /** Returns current preference value. */
  T getValue();

  /** Returns current preference value as String. */
  String getStringValue();

  /** Destroys presenter, making it unusable. */
  void destroy();
}
