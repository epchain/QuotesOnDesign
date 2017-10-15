package com.dr0id3v.quotesondesign.present;

import com.dr0id3v.quotesondesign.logic.QuoteRepository;

/** Presenter for {@link QuoteRepository}. */
public interface QuoteRepositoryPresenter
  extends Presenter<QuoteRepositoryPresenter.Listener>, QuoteRepository
{
  /** Listener for {@link QuoteRepository} events. */
  interface Listener
  {
    /**
     * On quote added event.
     * @param position position in repository, where quote was added
     */
    void onQuoteAdded( int position );

    /**
     * Quote was removed from specific position.
     * @param position position in repository, from which quote was removed
     */
    void onQuoteRemoved( int position );
  }
}
