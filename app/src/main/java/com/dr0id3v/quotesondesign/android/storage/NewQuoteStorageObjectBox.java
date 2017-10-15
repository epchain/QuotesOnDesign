package com.dr0id3v.quotesondesign.android.storage;

import android.content.Context;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import com.dr0id3v.quotesondesign.logic.Quote;
import com.dr0id3v.quotesondesign.present.NewQuoteStoragePresenter;
import com.dr0id3v.quotesondesign.present.SimplePresenter;

/** Storage for single new quote implemented with ObjectBox. */
public class NewQuoteStorageObjectBox
  extends SimplePresenter<NewQuoteStoragePresenter.Listener>
  implements NewQuoteStoragePresenter
{
  private final BoxStore boxStore;
  private final Box<NewQuoteEntity> quoteEntityBox;
  private final Query<NewQuoteEntity> quoteEntityQuery;
  private NewQuoteEntity newQuoteEntity;

  public NewQuoteStorageObjectBox( Context context )
  {
    boxStore = MyObjectBox.builder()
      .androidContext( context )
      .name( NewQuoteStorageObjectBox.class.getSimpleName() )
      .build();
    quoteEntityBox = boxStore.boxFor( NewQuoteEntity.class );
    quoteEntityQuery = quoteEntityBox.query().build();
    // Reactive query happens lately, i.e, after batch of put()'s there are batch of queries
    // So we will use manual updates
    updateNewQuoteEntityList();
  }

  private void updateNewQuoteEntityList()
  {
    // We know, it will always be first single row or null.
    List<NewQuoteEntity> newQuoteEntities = quoteEntityQuery.find( 0, 1 );
    if ( newQuoteEntities.size() > 0 ) newQuoteEntity = newQuoteEntities.get( 0 );
  }

  @Override
  public Quote getNewQuote()
  {
    return newQuoteEntity;
  }

  @Override
  public Date getNewQuoteDate()
  {
    if ( getNewQuote() == null ) return null;
    return newQuoteEntity.getDate();
  }

  @Override
  public void updateNewQuote( Quote newQuote )
  {
    NewQuoteEntity updateQuoteEntity = newQuoteEntity;
    if ( updateQuoteEntity == null )
    { // We need new one for (boxId == 0) to tell box to create new ID.
      updateQuoteEntity = new NewQuoteEntity( newQuote );
    }
    else
    { // Otherwise box will update entity using existing boxId
      updateQuoteEntity.setId( newQuote.getId() );
      updateQuoteEntity.setTitle( newQuote.getTitle() );
      updateQuoteEntity.setContent( newQuote.getContent() );
      updateQuoteEntity.setLink( newQuote.getLink() );
    }
    updateQuoteEntity.setDate( new Date() );
    quoteEntityBox.put( updateQuoteEntity );
    updateNewQuoteEntityList();

    for ( NewQuoteStoragePresenter.Listener listener : super.listeners )
    {
      listener.onNewQuoteUpdated( updateQuoteEntity, updateQuoteEntity.getDate() );
    }
  }
}
