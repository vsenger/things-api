package org.things;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vsenger
 */
import org.things.Device;
import org.things.Things;
import org.things.device.SerialDevice;

public class Teste {

    public static void main(String[] args) throws Exception {
        /*SerialDevice d = new SerialDevice("/dev/ttyUSB0", 19200);
        d.open();
        d.send((char) 0x02 + "AUz" + (char) 0x03);
        String r = d.receive(5000);
        System.out.println(r);
        d.close();*/
        SerialDevice d = new SerialDevice("/dev/ttyUSB0", 115200);
        d.open();
        d.send("discovery");
        String r = d.receive(5000);
        System.out.println(r);
        d.close();
        
    }
}
