package com.dr0id3v.quotesondesign.logic;

/** Interface for retrieving quotes. */
public interface QuoteRetriever
{
  /** Quote retrieve method. */
  enum Method { Latest, Random }

  /**
   * Obtain new quote, if this is possible.
   * @param method quote retrieve method
   * @return a new quote; {@code null}, if new quote is unavailable
   */
  Quote getNewQuote( Method method );
}
