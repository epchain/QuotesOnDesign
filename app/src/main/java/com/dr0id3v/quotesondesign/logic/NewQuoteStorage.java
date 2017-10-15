package com.dr0id3v.quotesondesign.logic;

import java.util.Date;

/** Storage for new quote. */
public interface NewQuoteStorage
{
  /**
   * Returns current new quote.
   * @return new quote, if it exists in storage;
   *         null, if new quote was never existed
   */
  Quote getNewQuote();

  /**
   * Returns date of new quote.
   * @return date when new quote was added, if it exists in storage;
   *         null, if new quote was never existed
   */
  Date getNewQuoteDate();

  /**
   * Updates new quote contents and date.
   * @param newQuote a new quote
   */
  void updateNewQuote( Quote newQuote );
}
