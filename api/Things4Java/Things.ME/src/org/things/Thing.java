/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.things;

/**
 *
 * @author vsenger
 */
public interface Thing {
  public void send(String x) throws ThingException;
  public String receive() throws ThingException;
  public String getThingURL();
}
