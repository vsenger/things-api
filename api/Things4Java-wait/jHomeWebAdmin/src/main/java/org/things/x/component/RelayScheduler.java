/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.component;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author vsenger
 */
@Local
public interface RelayScheduler {
  public void schedule(long when, long period, String relayName);

}
