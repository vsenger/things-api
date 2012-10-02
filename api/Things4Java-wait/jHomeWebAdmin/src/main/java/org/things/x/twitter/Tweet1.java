package org.things.x.twitter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.Date;

/**
 *
 * @author vsenger
 */
public class Tweet1 implements java.io.Serializable{
  private Date createdAt;
  private String screenName;
  private String text;

  public Tweet1(Date createdAt, String screenName, String text) {
    this.createdAt = createdAt;
    this.screenName = screenName;
    this.text = text;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Tweet1 other = (Tweet1) obj;
    if (this.createdAt != other.createdAt && (this.createdAt == null || !this.createdAt.equals(other.createdAt))) {
      return false;
    }
    if ((this.screenName == null) ? (other.screenName != null) : !this.screenName.equals(other.screenName)) {
      return false;
    }
    if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash;
  }
  

}
