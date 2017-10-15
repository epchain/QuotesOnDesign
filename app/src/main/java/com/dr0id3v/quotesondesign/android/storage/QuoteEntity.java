package com.dr0id3v.quotesondesign.android.storage;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import com.dr0id3v.quotesondesign.logic.Quote;

/**
 * ObjectBox Entity for {@link Quote}.
 * Polymorphic types are not supported by the moment.
 * {@link Convert} implementation might get complicated,
 * so this class just copies fields from {@link Quote}
 */
@Entity
public class QuoteEntity extends Quote
{
  @Id
  private long boxId;

  private int id;
  private String title;
  private String content;
  private String link;

  /** Empty entity constructor is mandatory for ObjectBox. */
  public QuoteEntity()
  {
    super( 0, null, null, null );
  }

  public QuoteEntity( int id, String title, String content, String link )
  {
    this();
    this.id = id;
    this.title = title;
    this.content = content;
    this.link = link;
  }

  public QuoteEntity( Quote quote )
  {
    this( quote.getId(), quote.getTitle(), quote.getContent(), quote.getLink() );
  }

  public long getBoxId() { return boxId; }

  public void setBoxId( long boxId ) { this.boxId = boxId; }

  @Override
  public int getId() { return id; }

  @Override
  public void setId( int id ) { this.id = id; }

  @Override
  public String getTitle() { return title; }

  @Override
  public void setTitle( String title ) { this.title = title; }

  @Override
  public String getContent() { return content; }

  @Override
  public void setContent( String content ) { this.content = content; }

  @Override
  public String getLink() { return link; }

  @Override
  public void setLink( String link ) { this.link = link; }

  @Override
  public boolean equals( Object obj )
  {
    if ( !(obj instanceof QuoteEntity) )
    {
      throw new UnsupportedOperationException( "Argument is not of proper type" );
    }
    QuoteEntity quoteEntity = (QuoteEntity) obj;
    return this.boxId == quoteEntity.boxId;
  }
}
