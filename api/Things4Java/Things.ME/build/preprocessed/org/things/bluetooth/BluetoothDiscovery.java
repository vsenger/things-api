package org.things.bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.StreamConnection;
import org.things.ThingException;
import org.things.Things;

/**
 *
 * @author @vsenger e
 * @netomarin Classe para fazer discovery e mostrar no display
 */
public class BluetoothDiscovery implements DiscoveryListener {

  UUID RFCOMM_UUID = new UUID(0x1101);
  private LocalDevice localDevice = null;
  private DataInputStream dis = null;
  private DataOutputStream dos = null;
  private DiscoveryAgent discoveryAgent = null;
  private Vector services;
  private static BluetoothDiscovery instance;
  private BluetoothDiscoveryListener listener;
  private Vector devices;

  public Vector getDevices() {
    return devices;
  }

  public void setDevices(Vector devices) {
    this.devices = devices;
  }

  private BluetoothDiscovery() {
    devices = new Vector();
    
  }

  public static BluetoothDiscovery getInstance() {
    if (instance == null) {
      instance = new BluetoothDiscovery();
    }

    return instance;
  }

  public void start(BluetoothDiscoveryListener listener) {
    devices = new Vector();
    this.listener = listener;
    try {
      localDevice = LocalDevice.getLocalDevice();
      discoveryAgent = localDevice.getDiscoveryAgent();
      listener.notify("Starting search...");
      discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
    } catch (BluetoothStateException ex) {
      listener.notify("Error searching devices "
              + ex.getMessage() + "...");
    }

  }

  public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
    try {
      listener.notify("Device found: " + btDevice.getBluetoothAddress());
      devices.addElement(btDevice);
    } catch (Exception e) {
      listener.notify("Error " + e.getMessage());
    }
  }

  public void inquiryCompleted(int discType) {
    if (devices.size() > 0) {
      for (int x = 0; x < devices.size(); x++) {
        services = new Vector();
        UUID[] query = new UUID[1];
        query[0] = RFCOMM_UUID;
        try {
          discoveryAgent.searchServices(null, query,
                  (RemoteDevice) devices.elementAt(x), this);
        } catch (BluetoothStateException ex) {
          listener.notify(ex.getMessage());
        }
      }
    } else {
      //MainForm.getInstance().updateStatus("Central not found!");
    }
  }

  //called when service found during service search
  public void servicesDiscovered(int transID, ServiceRecord[] records) {
    //MainForm.getInstance().updateStatus("Looking for RFCOMM channel!");
    for (int i = 0; i < records.length; i++) {
      this.services.addElement(records[i]);
      String s = ((ServiceRecord) (services.elementAt(i))).getConnectionURL(0, false);
      listener.notify("found service " + s);
      if (s.startsWith("btspp")) {
        try {
          Things.bluetooth(s);
          
          Things.delay(4000);
        } catch (ThingException ex) {
          ex.printStackTrace();
          //MainForm.getInstance().updateStatus("Error connection: " + ex.getMessage());
        }
      }
    }
    //MainForm.getInstance().updateStatus("Service search completed!");
  }

  public static void delay(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }
  //called when service search gets complete

  public void serviceSearchCompleted(int transID, int respCode) {
    //.getInstance().updateStatus("callback serviceSearchCompleted");
    if (services.size() > 1) {
      for (int x = 0; x < services.size(); x++) {
        String s = ((ServiceRecord) (services.elementAt(x))).getConnectionURL(0, false);
        listener.notify(s);
      }
    }
    //streamConn = (StreamConnection) Connector.open(s);
    //dos = streamConn.openDataOutputStream();
    //dis = streamConn.openDataInputStream();
  }
  //methods not used anymore
  private void sendMessage(byte[] messageToSend) {
    try {
      dos.write(messageToSend);
      dos.flush();
    } catch (IOException ex) {
    }
  }

  private String recieveMessages() {
    byte[] data = null;
    char[] dataChar = null;
    try {
      //MainForm.getInstance().updateStatus("Data: " + dis.available());
      if (dis.available() > 0) {
        data = new byte[dis.available()];
        dataChar = new char[dis.available()];
        dis.read(data);
        for (int x = 0; x < data.length; x++) {
          dataChar[x] = (char) data[x];
        }
        return new String(dataChar);
      }
    } catch (IOException e) {
      return "-1";
    }
    return "0";
  }
}