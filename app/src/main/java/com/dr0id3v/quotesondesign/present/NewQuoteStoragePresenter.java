package com.dr0id3v.quotesondesign.present;

import java.util.Date;

import com.dr0id3v.quotesondesign.logic.NewQuoteStorage;
import com.dr0id3v.quotesondesign.logic.Quote;

/** Presenter for {@link NewQuoteStorage}. */
public interface NewQuoteStoragePresenter
  extends Presenter<NewQuoteStoragePresenter.Listener>, NewQuoteStorage
{
  /** Listener for {@link NewQuoteStorage} events. */
  interface Listener
  {
    /**
     * On new quote updated event.
     * @param newQuote new quote contents
     * @param date date of new quote update
     */
    void onNewQuoteUpdated( Quote newQuote, Date date );
  }
}
