package com.dr0id3v.quotesondesign.logic;

/** Repository of {@link Quote}'s. */
public interface QuoteRepository
{
  /**
   * Puts quote to repository. If quote with same id already exists
   * in repository, original quote must be replaced with new contents.
   * @param quote a new quote
   * @return true, when operation completed successfully
   */
  boolean put( Quote quote );

  /** Returns quote for given position. */
  Quote get( int position );

  /**
   * Returns quote for given Quote ID.
   * @param id quote's ID
   * @return quote, if it exists; null, if no quote with such ID
   */
  Quote query( int id );

  /**
   * Remove quote from repository.
   * @param quote quote to remove
   * @return true, if quote existed in repository and was deleted
   */
  boolean remove( Quote quote );

  /** Returns repository size. */
  int size();
}
