/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.globalcode.eletronlivre.arduino.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vsenger
 */
public class Arduino {

    static boolean init = false;
    static Enumeration portList;
    static CommPortIdentifier portId;
    static String messageString = "10";
    static SerialPort serialPort;
    static OutputStream outputStream;
    static InputStream inputStream;
    static boolean outputBufferEmptyFlag = false;
    static String defaultPort = "/dev/rfcomm0";
    //static String defaultPort = "/dev/tty.usbserial-A6007W3X";
    //static String defaultPort = "/dev/ttyUSB0";
    public static void begin(int bps) {
        if (!init) {
            init();
        }

    }
    public static void delay(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
        }

    }

    public static void init() {
        init = true;
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

    public static void enviar(String s) throws Exception {
        if (!init) {
            init();
        }

        outputStream.write(s.getBytes());
        outputStream.flush();
    }

    public static void enviar(float f) throws Exception {

        enviar(new byte[]{(byte) f});
    }
    //100, 1, 1024, 1, 9
   public static int map(int val, int minOriginal, int maxOriginal, int minTarget, int maxTarget) {
        return val / ((maxOriginal  - minOriginal) / (maxTarget - minTarget));

        
    }
    public static void main(String[] args) throws Exception{
     enviar("L");
    }
    public static void enviar(byte bytes[]) throws Exception {
        if (!init) {
            init();
        }

        System.out.println("Escrevendo...");
        outputStream.write((int)'B'); //my I2C ID
        System.out.println(bytes[0]);

        outputStream.write(bytes[0] ==0 ? 10 : bytes[0] );
        outputStream.flush();
        System.out.println("Esperando...");


    }
    class BluetoothListener extends Thread {
        public void run() {
            while(true) {
                try {
                    if (inputStream.available() > 0) {
                        while(inputStream.available()>0) {
                            System.out.print(inputStream.read());
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
                }
                Arduino.delay(50);

            }
        }
    }
}
