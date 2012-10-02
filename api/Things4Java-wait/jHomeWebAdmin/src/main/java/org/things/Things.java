/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import org.things.device.InternetDevice;

/**
 *
 * @author vsenger
 */
@Named
public class Things  {
  public Things() {
  }
  //para uso em Java SE
  public static Things things=new Things();
  
  public static void delay(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException ex) {
    }
  }
  
  protected Collection<Device> devices;

  
  public String getThingsString() {
    int t = 0;
    for (Device device : devices) {
      t += device.getThings().size();
    }
    String retornao = "jhome-server|" + t + "|";
    for (Device device : devices) {
      
      for(String c : device.getThings().keySet()) {
        Thing component = device.getThings().get(c);
        retornao += component.getName() + "|" +
                component.getType() + "|" +
                component.getPort() + "|" +
                component.getLastValue() + "|";
      }
    } 
    return retornao;
  }
  
  public Thing find(String id) {
    Thing thing = null;
    for (Device device : devices) {
      if (device.getThings().containsKey(id)) {
        thing = device.getThings().get(id);
      }
    }
    return thing;
  }

  public String execute(String thing) {
    String r = null;
    Thing found = find(thing);
    if (found != null) {
      try {
        r = found.execute();
        found.setLastValue(r);
      } catch (Exception ex) {
        Logger.getLogger(Things.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      Logger.getLogger(Things.class.getName()).log(Level.INFO, "Component " + thing + " not found!");
      //Auto rediscovery on..
    }
    return r;
  }

  public String execute(String thing, String args) {
    String r = null;
    Thing found = find(thing);
    if (found != null) {
      try {
        r = found.execute(args);
        found.setLastValue(r);
      } catch (Exception ex) {
        Logger.getLogger(Things.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      Logger.getLogger(Things.class.getName()).log(Level.INFO, "Component " + thing + " not found!");
    }
    return r;
  }

  public void close() {
    for (Device device : devices) {
      try {
        device.close();
      } catch (IOException e) {
        Logger.getLogger(Things.class.getName()).log(Level.INFO, "Exception while closing " + device.getName());
      }
    }
  }

  public Collection<Device> discoveryNetworkThings(String args) {
    if (devices == null) {
      devices = new ArrayList<Device>();
    }

    Collection<Device> devicesFound = new ArrayList<Device>();
    Device device = null;
    try {
      device = new InternetDevice(args);
      device.discovery();

      if (device.connected()) {
        this.devices.add(device);
      } else {
        Logger.getLogger(Things.class.getName()).log(Level.INFO,
                "Network Device is not jHome compatible " + device.getID());
        device.close();
      }
      devicesFound.add(device);
    } catch (Exception e) {
      Logger.getLogger(Things.class.getName()).log(Level.WARNING,
              "Network Device Error " + device.getID());
    }
    return devicesFound;
  }
  
  public Collection<Device> getDevices() {
    return devices;
  }

  /*public Collection<Device> discoveryBluetooth(String args) throws Exception {
    return this.discoverySerial(args);
  }*/

}
