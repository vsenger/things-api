package org.things.device;

import org.things.Device;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.things.Thing;
import org.things.Things;

/**
 *
 * @author vsenger
 */
public class SerialDevice implements Device {

  private static final int DEFAULT_BAUDRATE = 19200;
  final static int DISCOVERY_RETRY = 3;
  CommPortIdentifier portId;
  String portName;
  int baudRate;
  SerialPort serialPort;
  OutputStream outputStream;
  InputStream inputStream;
  String resources;
  String name;
  String description;
  boolean connected;
  Map<String, Thing> things;
  Collection<Thing> thingsList;

  public SerialDevice(CommPortIdentifier portId, int baudRate) {
    this.portId = portId;
    this.baudRate = baudRate;
    things = new Hashtable<String, Thing>();
    thingsList = new ArrayList<Thing>();
  }

  public SerialDevice(String portName, int baudRate) {
    this.portName = portName;
    this.baudRate = baudRate;
    things = new Hashtable<String, Thing>();
    thingsList = new ArrayList<Thing>();
  }

  @Override
  public boolean connected() {
    return connected;
  }

  @Override
  public void close() throws IOException {
    Logger.getLogger(SerialDevice.class.getName()).log(Level.INFO,
            "Closing device on {0}", serialPort.getName());
    //send("X");
    connected = false;
    try {
      inputStream.close();
    } catch (Exception e) {
    }
    try {
      outputStream.close();
    } catch (Exception e) {
    }
    try {
      if (serialPort != null) {
        serialPort.close();
      }
    } catch (Exception e) {
    }
  }

  public void open() throws IOException {
    try {
      if (portName != null) {
        portId =
                CommPortIdentifier.getPortIdentifier(portName);
      }
      if(portId==null) {
        throw new IOException("Invalid port " + portName);
      }
      serialPort =
              (SerialPort) portId.open(portId.getName(), baudRate);
      if(portId==null) {
        throw new IOException("Invalid port " + portName);
      }

      serialPort.setSerialPortParams(baudRate,
              SerialPort.DATABITS_8,
              SerialPort.STOPBITS_1,
              SerialPort.PARITY_NONE);
      serialPort.notifyOnOutputEmpty(true);
      outputStream = serialPort.getOutputStream();
      inputStream = serialPort.getInputStream();
      Logger.getLogger(SerialDevice.class.getName()).log(Level.INFO,
              "Connection Stabilished with {0}", serialPort.getName());
    } catch (Exception e) {
      e.printStackTrace();
      Logger.getLogger(SerialDevice.class.getName()).log(Level.SEVERE,
              "Could not init the device on " + serialPort.getName(), e);
      serialPort.close();
    }

  }

  @Override
  public void discovery() throws Exception {
    //Those times are totally dependent with the kind of communication...
    for (int x = 0; x < DISCOVERY_RETRY; x++) {
      System.out.println("Delaying 2500 - try no." + x);
      Things.delay(2500);
      send("discovery");
      resources = receive();
      Things.delay(50);

      if (resources != null) {
        Logger.getLogger(SerialDevice.class.getName()).log(Level.INFO,
                "Things API Compatible Device found! Resource String: {0}", resources);
        things = new Hashtable<String, Thing>();
        thingsList = new ArrayList<Thing>();

        connected = true;
        try {
          StringTokenizer tokenizer = new StringTokenizer(resources, "|");
          this.name = tokenizer.nextToken();
          int numberOfComponents = Integer.parseInt(tokenizer.nextToken());
          for (int y = 0; y < numberOfComponents; y++) {
            String name = tokenizer.nextToken();
            String type = tokenizer.nextToken();
            String port = tokenizer.nextToken();
            String value = tokenizer.nextToken();

            Thing component = new Thing(this, name, name, port, type, value);
            this.things.put(name, component);
            this.thingsList.add(component);

          }
          break;
        } catch (Exception e) {
          Logger.getLogger(SerialDevice.class.getName()).log(Level.INFO,
                  "Wrong resource String. Parse error!", e);
        }
      } else {
        Logger.getLogger(SerialDevice.class.getName()).log(Level.INFO,
                "Empty Resource String - Nor a Thigns API device", resources);
      }
    }
  }
  public void send(char s) throws IOException {

    if (outputStream == null) {
      Logger.getLogger(SerialDevice.class.getName()).log(Level.SEVERE,
              "This device ({0}) is not working because IO objects are null. "
              + "You should restart the device!", this.getName());
    } else {
      outputStream.write(s);
      outputStream.flush();

    }
  }

  
  
  
  
  @Override
  public void send(String s) throws IOException {

    if (outputStream == null) {
      Logger.getLogger(SerialDevice.class.getName()).log(Level.SEVERE,
              "This device ({0}) is not working because IO objects are null. "
              + "You should restart the device!", this.getName());
    } else {
      outputStream.write(s.getBytes());
      outputStream.flush();

    }
  }

  @Override
  public String receive() throws IOException {

    if (inputStream == null) {
      String msg = "This device (" + this.getName()
              + ") is not working because IO objects are null. "
              + "You should restart the device!";
      Logger.getLogger(SerialDevice.class.getName()).log(Level.SEVERE, msg);
      throw new IOException(msg);
    } else {
        
      int available = inputStream.available();
      if (available == 0) {
        //inputStream.close();
        return null;
      } else {
        byte chunk[] = new byte[available];
        inputStream.read(chunk, 0, available);
        String retorno = new String(chunk);
        inputStream.close();
        return retorno;
      }
    }
  }
  public String receive(long timeout) throws IOException {

    if (inputStream == null) {
      String msg = "This device (" + this.getName()
              + ") is not working because IO objects are null. "
              + "You should restart the device!";
      Logger.getLogger(SerialDevice.class.getName()).log(Level.SEVERE, msg);
      throw new IOException(msg);
    } else {
      long time = System.currentTimeMillis();
      while(inputStream.available()==0 && System.currentTimeMillis() - time<timeout);
      
      int available = inputStream.available();
      if (available == 0) {
        //inputStream.close();
        return null;
      } else {
        byte chunk[] = new byte[available];
        inputStream.read(chunk, 0, available);
        String retorno = new String(chunk);
        inputStream.close();
        return retorno;
      }
    }
  }
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getResourceString() {
    return resources;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Map<String, Thing> getThings() {
    return this.things;
  }

  @Override
  public String getID() {
    return this.portId.getName();
  }

  @Override
  public Collection<Thing> getThingsList() {
    return thingsList;
  }

  public Collection<Device> scanPorts() {

    Collection<Device> devicesFound = new ArrayList<Device>();

    Enumeration portList;
    CommPortIdentifier portId;

    portList = CommPortIdentifier.getPortIdentifiers();
    Logger.getLogger(Things.class.getName()).log(Level.INFO, "Starting jHome 1.0");
    SerialPort serialPort = null;
    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();
      Logger.getLogger(Things.class.getName()).log(Level.INFO,
              "Scaning port: " + portId.getName());

      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        Logger.getLogger(Things.class.getName()).log(Level.INFO,
                "Serial Device Port found " + portId.getName()
                + ". Trying to discovery this device.");
        try {
          /*serialPort =
           (SerialPort) portId.open(portId.getName(), 115200);
           Device device = new SerialDevice(serialPort);*/
          Device device = new SerialDevice(portId, DEFAULT_BAUDRATE);
          device.open();
          device.discovery();
          if (device.connected()) {
            devicesFound.add(device);
          } else {
            Logger.getLogger(Things.class.getName()).log(Level.INFO,
                    "Serial Device is not Things API Compatible" + portId.getName());
            device.close();
          }
          devicesFound.add(device);
        } catch (Exception e) {
          Logger.getLogger(Things.class.getName()).log(Level.SEVERE,
                  "Couldn't connect to" + portId.getName());
          e.printStackTrace();
        }
      }
    }
    return devicesFound;
  }

  public String getPortName() {
    return portName;
  }

  public void setPortName(String portName) {
    this.portName = portName;
  }
}
