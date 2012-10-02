/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.things.device.SerialDevice;

/**
 *
 * @author vsenger
 */
public class Thing {

  private Device device;
  String identifier, name;
  String lastValue;
  String port, type;
  String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public String execute() throws Exception {
    try {
      //System.out.println("Sending " + identifier);
      device.send(identifier);
      if(device instanceof SerialDevice) Things.delay(40);
      //magic number, just don't change it. :) kkkk
      //DeviceUtil.delay((identifier.length()+2)*10);
      String r = device.receive();
      //System.out.println("Retorno: " + r);
      lastValue = r == null ? "" : r;
      return r;

    } catch (Exception e) {
      //Error with this component, probably device was unplugged
      device.close();
      throw e;
    }
  }

  public String execute(String args) throws Exception {
    device.send(identifier + "?" + args);
    //delay(20);
    if(device instanceof SerialDevice) Things.delay(40);//era 30

    String r = device.receive();
    lastValue = r == null ? "" : r;
    return r;
  }

  public Thing(Device device, String identifier, String name, String port, String type, String lastValue) {
    this.device = device;

    Logger.getLogger(Thing.class.getName()).log(Level.INFO,
            "Creating Component " + name + " with ID " + identifier);

    this.identifier = identifier;
    this.name = name;
    this.port = port;
    this.type = type;
    this.lastValue = lastValue;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public void setLastValue(String lastValue) {
    this.lastValue = lastValue;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getLastValue() {
    return lastValue;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getPort() {
    return port;
  }

  public String getType() {
    return type;
  }

  public void setType(String t) {
    this.type = t;
  }

  public void setPort(String p) {
    this.port = p;
  }
}
