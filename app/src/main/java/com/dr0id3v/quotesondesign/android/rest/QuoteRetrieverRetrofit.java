package com.dr0id3v.quotesondesign.android.rest;

import android.text.Html;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.android.App;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.logic.QuoteRetriever;

/** Quote retrieving using Retrofit 2. */
public class QuoteRetrieverRetrofit implements QuoteRetriever
{
  private final Retrofit retrofit;
  private final ApiQuotesOnDesign apiQuotesOnDesign;

  private static String getFilterForMethod( Method method )
  {
    switch ( method )
    {
      case Latest: return "latest";
      case Random: return "rand";
      default: throw new IllegalArgumentException( "Unknown method" );
    }
  }

  public QuoteRetrieverRetrofit( App app )
  {
    int serverTimeoutSeconds = app.getResources().getInteger( R.integer.serverTimeoutSeconds );

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .readTimeout( serverTimeoutSeconds, TimeUnit.SECONDS )
      .connectTimeout( serverTimeoutSeconds, TimeUnit.SECONDS )
      .build();

    retrofit = new Retrofit.Builder()
      .baseUrl( "https://quotesondesign.com/" )
      .client( okHttpClient )
      .addConverterFactory( GsonConverterFactory.create() )
      .build();
    apiQuotesOnDesign = retrofit.create( ApiQuotesOnDesign.class );
  }

  @Override
  public Quote getNewQuote( Method method )
  {
    Call<List<QuoteModel>> quotes = apiQuotesOnDesign.getQuotes(
      getFilterForMethod( method ), 1
    );
    Quote newQuote;
    try
    {
      Response<List<QuoteModel>> response = quotes.execute();
      QuoteModel quoteModel = response.body().get( 0 );
      newQuote = quoteModel.newQuoteFromThis();
      newQuote.setContent( removeStringJunk(newQuote.getContent()) );
    }
    catch ( IOException | NullPointerException e )
    {
      return null;
    }
    return newQuote;
  }

  /** Removes HTML elements and trailing whitespace. */
  private static String removeStringJunk( String string )
  {
    String result = Html.fromHtml( string ).toString();
    Matcher matcher = Pattern.compile( "\n" ).matcher( result );
    result = matcher.replaceAll( "" );
    result = result.trim();
    return result;
  }
}
