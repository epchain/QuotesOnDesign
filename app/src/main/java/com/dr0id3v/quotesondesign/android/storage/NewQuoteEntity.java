package com.dr0id3v.quotesondesign.android.storage;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import com.dr0id3v.quotesondesign.logic.Quote;

/** This class is the same {@link QuoteEntity} with date field added. */
@Entity
public class NewQuoteEntity extends Quote
{
  @Id
  private long boxId;
  private Date date;

  private int id;
  private String title;
  private String content;
  private String link;

  /** Empty entity constructor is mandatory for ObjectBox. */
  public NewQuoteEntity()
  {
    super( 0, null, null, null );
  }

  public NewQuoteEntity( int id, String title, String content, String link )
  {
    this();
    this.id = id;
    this.title = title;
    this.content = content;
    this.link = link;
  }

  public NewQuoteEntity( Quote quote )
  {
    this( quote.getId(), quote.getTitle(), quote.getContent(), quote.getLink() );
  }

  public long getBoxId() { return boxId; }

  public void setBoxId( long boxId ) { this.boxId = boxId; }

  public Date getDate() { return date; }

  public void setDate( Date date ) { this.date = date; }

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
    if ( !(obj instanceof NewQuoteEntity) )
    {
      throw new UnsupportedOperationException( "Argument is not of proper type" );
    }
    NewQuoteEntity quoteEntity = (NewQuoteEntity) obj;
    return this.boxId == quoteEntity.boxId;
  }
}
