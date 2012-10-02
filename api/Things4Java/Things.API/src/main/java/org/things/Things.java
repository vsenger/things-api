/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.things.device.InternetDevice;
import org.things.device.SerialDevice;

/**
 *
 * @author vsenger
 */
public class Things {

  private static final long MIN_INTERVAL = 40;

  public Things() {
  }
  //para uso em Java SE
  public static Things things = new Things();

  public static void delay(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException ex) {
    }
  }
  protected Collection<Device> devices;
  protected Map<String, Device> devicesTable;
  private long lastSend;

  public String getThingsString() {
    int t = 0;
    for (Device device : devices) {
      t += device.getThings().size();
    }
    String retornao = "jhome-server|" + t + "|";
    for (Device device : devices) {

      for (String c : device.getThings().keySet()) {
        Thing component = device.getThings().get(c);
        retornao += component.getName() + "|"
                + component.getType() + "|"
                + component.getPort() + "|"
                + component.getLastValue() + "|";
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

  public void timeControl() {
    if (System.currentTimeMillis() - lastSend < MIN_INTERVAL) {
      delay(System.currentTimeMillis() - lastSend);
    }
    lastSend = System.currentTimeMillis();
  }

  public synchronized String execute(String thing) {
    timeControl();
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

  public synchronized String execute(String thing, String args) {
    timeControl();

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
    if (devices == null || devices.isEmpty()) {
      return;
    }
    for (Device device : devices) {
      try {
        Logger.getLogger(Things.class.getName()).log(Level.INFO, "Closing device connection " + device.getName());

        device.close();
      } catch (IOException e) {
        Logger.getLogger(Things.class.getName()).log(Level.INFO, "Exception while closing " + device.getName());
      }
    }
  }

  public Collection<Device> discoveryNetworkThings(String args) {
    if (devices == null) {
      devices = new ArrayList<Device>();
      devicesTable = new HashMap<String, Device>();
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
                "Network Device is not Things API compatible " + device.getID());
        device.close();
      }
      devicesFound.add(device);
      this.devicesTable.put(args, device);

    } catch (Exception e) {
      Logger.getLogger(Things.class.getName()).log(Level.WARNING,
              "Network Device Error " + device.getID());
    }
    return devicesFound;
  }

  public Device discoverySerial(String serial, int baudRate) throws Exception {
    if (devices == null) {
      devices = new ArrayList<Device>();
      devicesTable = new HashMap<String, Device>();
    }
    if (devicesTable.containsKey(serial)) {
      return devicesTable.get(serial);
    }
    SerialDevice device =
            new SerialDevice(serial, baudRate);
    device.open();
    device.discovery();
    if (device.getResourceString() == null) {
      device.close();
      return null;
    } else {
      this.devices.add(device);
      this.devicesTable.put(serial, device);
      return device;
    }
  }

  public Collection<Device> getDevices() {
    return devices;
  }

  public Collection<Thing> getEveryThing() {
    ArrayList<Thing> ethings = new ArrayList<Thing>();
    if (devices != null) {
      for (Device d : devices) {
        ethings.addAll(d.getThingsList());
      }
    }
    return ethings;
  }
  /*public Collection<Device> discoveryBluetooth(String args) throws Exception {
   return this.discoverySerial(args);
   }*/
}
