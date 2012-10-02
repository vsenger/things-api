/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things;

import org.things.bluetooth.BluetoothThing;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author vsenger
 */
public class Things {

  private static Hashtable things;
  private static Vector thingsVector;
  public static Vector everyThing() {
    return thingsVector;
  }
  public static Thing anything() throws ThingException {
    if(things.isEmpty()) throw new ThingException("No devices!");
    else {
      return (Thing) thingsVector.elementAt(0);
    }
  }
  public static void send(String message) throws ThingException {
    anything().send(message);
  }
  public static String receive() throws ThingException {
    return anything().receive();
  }  
  public static void delay(long ms) {
    try {
      Thread.sleep(ms);
    }
    catch(Exception e) {
      Thread.currentThread().interrupt();
    }
  }
  public static Thing bluetooth(String URL) throws ThingException {    
    if(things==null) {
       things = new Hashtable();
       thingsVector = new Vector();
    }
    Thing thing = null;
    if (things.containsKey(URL)) {
      thing = (Thing) things.get(URL);
    } else {
      try {
        thing = new BluetoothThing(URL);
        things.put(URL, thing);
        thingsVector.addElement(thing);
      } catch (Exception e) {
        throw new ThingException("Hash" + things.size() + 
                " Debug: " + things.contains(URL) + 
                " - " + e.getMessage() + " - " + URL);
      }
    }
    return thing;
  }
}
