package com.dr0id3v.quotesondesign.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dr0id3v.quotesondesign.R;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.present.QuoteRepositoryPresenter;

class RvAdapterFavoriteQuotes extends RecyclerView.Adapter<RvAdapterFavoriteQuotes.ViewHolder>
{
  private final QuoteRepositoryPresenter quoteRepository;
  private RecyclerView recyclerView;

  RvAdapterFavoriteQuotes( QuoteRepositoryPresenter quoteRepository )
  {
    this.quoteRepository = quoteRepository;
    this.quoteRepository.addListener( quoteRepositoryListener );
  }

  @Override
  public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
  {
    View view = LayoutInflater.from( parent.getContext() )
      .inflate( R.layout.card_quote, parent, false );
    return new ViewHolder( view );
  }

  @Override
  public void onBindViewHolder( ViewHolder viewHolder, int position )
  {
    Quote quote = quoteRepository.get( position );
    viewHolder.quoteText.setText( quote.getContent() );
    viewHolder.quoteAuthor.setText(
      viewHolder.quoteAuthor.getResources().getString( R.string.quoteAuthor, quote.getTitle() )
    );
    viewHolder.btRemove.setOnClickListener( view -> quoteRepository.remove( quote ) );
  }

  @Override
  public int getItemCount()
  {
    return quoteRepository.size();
  }

  @Override
  public void onAttachedToRecyclerView( RecyclerView recyclerView )
  {
    super.onAttachedToRecyclerView( recyclerView );
    this.recyclerView = recyclerView;
  }

  void destroy()
  {
    quoteRepository.removeListener( quoteRepositoryListener );
  }

  static class ViewHolder extends RecyclerView.ViewHolder
  {
    final TextView quoteText;
    final TextView quoteAuthor;
    final Button btRemove;

    public ViewHolder( View itemView )
    {
      super( itemView );
      quoteText = itemView.findViewById( R.id.quote );
      quoteAuthor = itemView.findViewById( R.id.author );
      btRemove = itemView.findViewById( R.id.btRemove );
    }
  }

  private final QuoteRepositoryPresenter.Listener quoteRepositoryListener =
    new QuoteRepositoryPresenter.Listener()
    {
      @Override
      public void onQuoteAdded( int position )
      {
        notifyItemInserted( position );
        // Force recycler view update by fake scroll to solve problem on API 16
        recyclerView.scrollBy( 0, 0 );
      }

      @Override
      public void onQuoteRemoved( int position )
      {
        notifyItemRemoved( position );
        // Force recycler view update by fake scroll to solve problem on API 16
        recyclerView.scrollBy( 0, 0 );
      }
    };
}
