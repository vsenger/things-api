/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things;

import java.io.IOException;
import org.things.Things;
import static org.things.Things.*;
import org.things.device.SerialDevice;

/**
 *
 * @author vsenger
 */
public class TestUSB0 {

    public static void main(String[] args) throws IOException {
        //Device things = new SerialDevice("/dev/ttyUSB0", 115200);
        //things.open();
        //Things.delay(1500);
        /*things.send("humidity");
        Things.delay(40);

        String s = things.receive();
        System.out.println("Luz: " + s);
        things.close();*/
         
         things.execute("/dev/ttyUSB0", "led", "1");
         delay(1000);
         things.execute("/dev/ttyUSB0", "led", "0");
         delay(1000);
         things.execute("/dev/ttyUSB0", "led", "1");
         delay(1000);
         things.execute("/dev/ttyUSB0", "led", "0");
         delay(1000);
        things.close();
    }
}
