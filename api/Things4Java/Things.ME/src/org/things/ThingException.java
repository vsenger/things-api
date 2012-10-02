/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.things;

/**
 *
 * @author vsenger
 */
public class ThingException extends Exception {
  public ThingException() {
    super();
  }
  public ThingException(String msg) {
    super(msg);
  }
  public ThingException(Exception root) {
    super(root.getMessage());
  }
  
}
