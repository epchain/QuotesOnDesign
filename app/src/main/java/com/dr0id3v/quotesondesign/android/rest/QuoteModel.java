package com.dr0id3v.quotesondesign.android.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.dr0id3v.quotesondesign.logic.Quote;

/** JSON model class based on {@link com.dr0id3v.quotesondesign.logic.Quote}. */
class QuoteModel
{
  @SerializedName( "ID")
  @Expose
  private int id;

  @SerializedName( "title")
  @Expose
  private String title;

  @SerializedName( "content")
  @Expose
  private String content;

  @SerializedName( "link")
  @Expose
  private String link;

  public Quote newQuoteFromThis()
  {
    return new Quote( id, title, content, link );
  }

  public int getId() { return id; }

  public void setId( int id ) { this.id = id; }

  public String getTitle() { return title; }

  public void setTitle( String title ) { this.title = title; }

  public String getContent() { return content; }

  public void setContent( String content ) { this.content = content; }

  public String getLink() { return link; }

  public void setLink( String link ) { this.link = link; }
}
