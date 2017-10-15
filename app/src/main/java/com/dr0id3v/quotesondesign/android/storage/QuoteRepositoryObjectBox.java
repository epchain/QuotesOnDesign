package com.dr0id3v.quotesondesign.android.storage;

import android.content.Context;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.present.QuoteRepositoryPresenter;
import com.dr0id3v.quotesondesign.present.SimplePresenter;

/** Quote repository implemented with ObjectBox. */
public class QuoteRepositoryObjectBox
  extends SimplePresenter<QuoteRepositoryPresenter.Listener>
  implements QuoteRepositoryPresenter
{
  private final BoxStore boxStore;
  private final Box<QuoteEntity> quoteEntityBox;
  private final Query<QuoteEntity> quoteEntityQuery;
  private List<QuoteEntity> quoteEntityList;

  public QuoteRepositoryObjectBox( Context context )
  {
    boxStore = MyObjectBox.builder()
      .androidContext( context )
      .name( QuoteRepositoryObjectBox.class.getSimpleName() )
      .build();
    quoteEntityBox = boxStore.boxFor( QuoteEntity.class );
    // Query all quotes, bringing last added to top
    quoteEntityQuery =
      quoteEntityBox.query().order( QuoteEntity_.boxId, QueryBuilder.DESCENDING ).build();
    // Reactive query happens lately, i.e, after batch of put()'s there are batch of queries
    // So we will use manual updates
    updateQuotesList();
  }

  private void updateQuotesList()
  {
    quoteEntityList = quoteEntityQuery.find();
  }

  @Override
  public boolean put( Quote quote )
  {
    QuoteEntity newQuoteEntity = new QuoteEntity( quote );
    quoteEntityBox.put( newQuoteEntity );
    updateQuotesList();

    int position = quoteEntityList.indexOf( newQuoteEntity );

    for ( QuoteRepositoryPresenter.Listener listener : super.listeners )
    {
      listener.onQuoteAdded( position );
    }
    return true;
  }

  @Override
  public Quote get( int position )
  {
    // We will return QuoteEntity for later getting boxId from it
    return quoteEntityList.get( position );
  }

  @Override
  public Quote query( int id )
  {
    List<QuoteEntity> quote =
      quoteEntityBox.query().equal( QuoteEntity_.id, id ).build().find();

    switch ( quote.size() )
    {
      case 0: return null;
      case 1: return quote.get( 0 );
      default: throw new IllegalStateException( "Quote ID is not unique inside ObjectBox" );
    }
  }

  @Override
  public boolean remove( Quote quote )
  {
    // Casting back to QuoteEntity to remove by boxId
    QuoteEntity quoteEntity = (QuoteEntity) quote;
    int position = quoteEntityList.indexOf( quoteEntity );
    if ( position >= 0 )
    {
      quoteEntityBox.remove( quoteEntity );
      updateQuotesList();
      for ( QuoteRepositoryPresenter.Listener listener : super.listeners )
      {
        listener.onQuoteRemoved( position );
      }
      return true;
    }
    return false;
  }

  @Override
  public int size()
  {
    return quoteEntityList.size();
  }
}
