/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletronlivre.cofffeetimer.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

/**
 *
 * @author vsenger
 */
@Singleton
@LocalBean
public class ArduinoBoard {
  Enumeration portList;
  CommPortIdentifier portId;
  String messageString = "10";
  SerialPort serialPort;
  OutputStream outputStream;
  InputStream inputStream;
  boolean outputBufferEmptyFlag = false;
  String defaultPort = "/dev/rfcomm1";
  int x = 0;

  public SerialPort getSerialPort() {
    return serialPort;
  }

  public void setSerialPort(SerialPort serialPort) {
    this.serialPort = serialPort;
  }

  public void testando() {
    System.out.println("x++" + x++);
  }

  public void delay(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException ex) {
    }

  }

  @PostConstruct
  public void init() {

    portList = CommPortIdentifier.getPortIdentifiers();

    System.out.println("Iniciando");
    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();
      System.out.println(portId.getName());
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        if (portId.getName().equals(defaultPort)) {
          System.out.println("Found port " + defaultPort);
          try {
            serialPort =
                    (SerialPort) portId.open("SimpleWriteCOM17", 115200);
          } catch (Exception e) {
            System.out.println("Port in use.");
            continue;
          }

          try {
            outputStream = serialPort.getOutputStream();
            inputStream = serialPort.getInputStream();
            serialPort.setSerialPortParams(115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.notifyOnOutputEmpty(true);
          } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (Exception e) {
            System.out.println("Error setting event notification");
            System.out.println(e.toString());
          }
        }
      }
    }
  }

  public void enviar(String s) throws Exception {
    outputStream.write(s.getBytes());
    outputStream.flush();
  }


}
