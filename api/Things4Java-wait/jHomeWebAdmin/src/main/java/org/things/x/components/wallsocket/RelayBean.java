/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.components.wallsocket;

import org.things.x.component.Relay;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.things.Things;

/**
 *
 * @author vsenger
 */
@Stateless
public class RelayBean implements Relay {

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
  @EJB
  private Things deviceManager;
  
  public void turnOn(String name) {
    try {
      deviceManager.execute(name, "1");
    } catch (Exception ex) {
      Logger.getLogger(RelayBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  public void turnOff(String name) {
    try {
      deviceManager.execute(name, "0");
    } catch (Exception ex) {
      Logger.getLogger(RelayBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
