/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.things.Thing;
import org.things.Things;
import org.things.Device;
import org.things.device.SerialDevice;

/**
 *
 * @author vsenger
 */
@ManagedBean
@SessionScoped
public class Controller {

  Things things = Things.things;
  private String discoveryIP = "192.168.1.15";
  private String discoveryDeviceType;
  private String discoverySerial = "/dev/ttyUSB0";
  private Device lastDeviceFound;
  private Thing thingToEdit;

  public Things getThings() {
    return things;
  }

  public void setThings(Things things) {
    this.things = things;
  }

  public Thing getComponentToEdit() {
    return thingToEdit;
  }

  public void setComponentToEdit(Thing componentToEdit) {
    this.thingToEdit = componentToEdit;
  }

  public Things getDeviceManager() {
    return things;
  }

  public void setDeviceManager(Things deviceManager) {
    this.things = deviceManager;
  }

  public String getDiscoveryDeviceType() {
    return discoveryDeviceType;
  }

  public void setDiscoveryDeviceType(String discoveryDeviceType) {
    this.discoveryDeviceType = discoveryDeviceType;
  }

  public String getDiscoveryIP() {
    return discoveryIP;
  }

  public void setDiscoveryIP(String discoveryIP) {
    this.discoveryIP = discoveryIP;
  }

  public String getDiscoverySerial() {
    return discoverySerial;
  }

  public void setDiscoverySerial(String discoverySerial) {
    this.discoverySerial = discoverySerial;
  }

  /**
   * Creates a new instance of AdminController
   */
  public Controller() {
  }

  public Device getLastDeviceFound() {
    return lastDeviceFound;
  }

  public void setLastDeviceFound(Device lastDeviceFound) {
    this.lastDeviceFound = lastDeviceFound;
  }

  public String discoverySerial() {
    String navegacao = "discovery";
    try {      
      Logger.getLogger(Controller.class.getName()).log(Level.INFO, "Starting connection with " + this.getDiscoverySerial());
      Device d = things.discoverySerial(this.getDiscoverySerial(), 115200);
      lastDeviceFound = d;
      if (d != null) {
        navegacao = "setup";
      }
    } catch (Exception ex) {
      Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
    return navegacao;

  }

  public String discoveryNetwork() {
    try {
      Collection<Device> devices = things.discoveryNetworkThings(this.getDiscoveryIP());
      if (devices.size() > 0) {
        this.lastDeviceFound = devices.iterator().next();
        return "setup";
      }
    } catch (Exception ex) {
      Logger.getLogger(Controller.class
              .getName()).log(Level.SEVERE, null, ex);
    }
    return "discovery";
  }

  public void updateComponent() {
    try {
      thingToEdit.setLastValue(thingToEdit.execute());
    } catch (Exception ex) {
      Logger.getLogger(Controller.class
              .getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void editComponent(int index) {
    thingToEdit = (Thing) lastDeviceFound.getThingsList().toArray()[index];
  }
}
