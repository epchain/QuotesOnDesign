package com.dr0id3v.quotesondesign.android.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <pre>
 * quotesondesign.com website API (WordPress).
 * Examples:
 *  https://quotesondesign.com/wp-json/posts?filter[orderby]=latest&filter[posts_per_page]=1
 *  https://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1
 * </pre>
 */
public interface ApiQuotesOnDesign
{
  /**
   * Query quotes from quotesondesign.com.
   * @param orderBy posts order
   * @param postsPerPage number of posts per page
   * @return list of {@link QuoteModel}s
   */
  @GET( "/wp-json/posts" )
  Call<List<QuoteModel>> getQuotes(
    @Query( "filter[orderby]" ) String orderBy,
    @Query( "filter[posts_per_page]" ) int postsPerPage
  );
}
