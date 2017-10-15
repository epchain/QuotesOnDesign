package com.dr0id3v.quotesondesign.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.present.NewQuoteStoragePresenter;

public class FragmentNewQuote extends Fragment
{
  private TextView tvNoNewQuote;
  private ViewGroup layoutNewQuote;
  private TextView newQuote;
  private TextView author;
  private NewQuoteStoragePresenter newQuoteStorage;

  public void setNewQuote( Quote quote )
  {
    setNewQuoteText( quote.getContent() );
    setAuthorText( quote.getTitle() );
  }

  public void setNewQuoteText( String text )
  {
    newQuote.setText( text );
  }

  public void setAuthorText( String text )
  {
    author.setText( getResources().getString( R.string.quoteAuthor, text ) );
  }

  @Nullable
  @Override
  public View onCreateView( LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState )
  {
    return inflater.inflate( R.layout.fragment_new_quote, container, false );
  }

  @Override
  public void onViewCreated( View view, @Nullable Bundle savedInstanceState )
  {
    super.onViewCreated( view, savedInstanceState );

    tvNoNewQuote = view.findViewById( R.id.tvNoNewQuote );
    layoutNewQuote = view.findViewById( R.id.layoutNewQuote );
    newQuote = view.findViewById( R.id.quote );
    author = view.findViewById( R.id.author );
    newQuoteStorage = ((App) getActivity().getApplication()).getNewQuoteStorage();
    newQuoteStorage.addListener( listener );

    setupNewQuote( newQuoteStorage.getNewQuote() );
  }

  private void setupNewQuote( Quote newQuote )
  {
    if ( newQuote != null )
    {
      setNewQuote( newQuote );
      tvNoNewQuote.setVisibility( View.GONE );
      layoutNewQuote.setVisibility( View.VISIBLE );
    }
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    newQuoteStorage.removeListener( listener );
  }

  private final NewQuoteStoragePresenter.Listener listener =
    ( newQuote, date ) ->
      setupNewQuote( newQuote );
}
