/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
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
public class AdminController {

  @Inject
  private Things things;
  private String discoveryIP = "192.168.1.15";
  private String discoveryDeviceType;
  private String discoverySerial = "/dev/ttyUSB0";
  private Device lastDeviceFound;
  private Thing componentToEdit;

  public Thing getComponentToEdit() {
    return componentToEdit;
  }

  public void setComponentToEdit(Thing componentToEdit) {
    this.componentToEdit = componentToEdit;
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
  public AdminController() {
  }

  public Device getLastDeviceFound() {
    return lastDeviceFound;
  }

  public void setLastDeviceFound(Device lastDeviceFound) {
    this.lastDeviceFound = lastDeviceFound;
  }

  public String discoverySerial() {
    String navegacao = null;
    SerialDevice device = 
            new SerialDevice(this.discoverySerial, 115200);
    try {
      
      
      device.open(); 
      this.lastDeviceFound = device;
      navegacao = "setup";
    } catch (IOException ex) {
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
      navegacao = "discovery";
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
      Logger.getLogger(AdminController.class
              .getName()).log(Level.SEVERE, null, ex);
    }
    return "discovery";
  }

  public void updateComponent() {
    try {
      componentToEdit.setLastValue(componentToEdit.execute());
    } catch (Exception ex) {
      Logger.getLogger(AdminController.class
              .getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void editComponent(int index) {
    componentToEdit = (Thing) lastDeviceFound.getThingsList().toArray()[index];
  }
}
