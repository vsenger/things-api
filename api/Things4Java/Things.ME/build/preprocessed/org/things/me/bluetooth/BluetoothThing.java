/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.me.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import org.things.Thing;
import org.things.ThingException;

/**
 *
 * @author vsenger
 */
public class BluetoothThing implements Thing {

  private LocalDevice localDevice = null;
  private StreamConnection streamConn = null;
  private DataInputStream dis = null;
  private DataOutputStream dos = null;
  private String url;
  public String getThingURL() {
    return url;
  }
  public String toString() {
    return url;
  }
  public BluetoothThing(String URL) throws ThingException {
    this.url = URL;
    try {
      localDevice = LocalDevice.getLocalDevice();
      localDevice.setDiscoverable(DiscoveryAgent.GIAC);
      //DiscoveryAgent da = localDevice.getDiscoveryAgent();
      //RemoteDevice devices[] = da.retrieveDevices(DiscoveryAgent.PREKNOWN);

      streamConn = (StreamConnection) Connector.open(URL);
      dos = streamConn.openDataOutputStream();
      dis = streamConn.openDataInputStream();
    } catch (IOException ex) {

      throw new ThingException("BT Connectio" + ex.getMessage());
    }
  }

  public void send(String message) throws ThingException {
    ByteArrayOutputStream bous = new ByteArrayOutputStream();
    for (int i = 0; i < message.length(); i++) {
      bous.write((int) message.charAt(i));
    }
    try {
      dos.write(bous.toByteArray());
      dos.flush();
    } catch (IOException ex) {
      throw new ThingException("Error Sending to BT: " + ex.getMessage());
    }
  }

  public String receive() throws ThingException {
    try {
      if (dis.available() > 0) {
        byte[] data = new byte[dis.available()];
        char[] dataChar = new char[dis.available()];
        dis.read(data);
        for (int x = 0; x < data.length; x++) {
          dataChar[x] = (char) data[x];
        }
        return new String(dataChar);
      } else {
        return "";
      }
    } catch (IOException ex) {
      throw new ThingException("Error Receiving from BT: " + ex.getMessage());
    }
  }

}
