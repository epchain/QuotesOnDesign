package com.dr0id3v.quotesondesign.logic;

/** Quote object */
public class Quote
{
  /** Unique id for quote. */
  private int id;

  /** Quote title. */
  private String title;

  /** Quote content. */
  private String content;

  /** Link to quote source, if such exists. */
  private String link;

  public Quote( int id, String title, String content, String link )
  {
    this.id = id;
    this.title = title;
    this.content = content;
    this.link = link;
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
