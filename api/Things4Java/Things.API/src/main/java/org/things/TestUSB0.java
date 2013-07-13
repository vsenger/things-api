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
        /*Device things = new SerialDevice("/dev/ttyUSB0", 115200);
        things.open();
        Things.delay(1500);
        things.send("humidity");
        Things.delay(40);

        String s = things.receive();
        System.out.println("Luz: " + s);
        things.close();*/
        System.out.print("Sensor Luz ");
         System.out.println(things.execute("/dev/ttyUSB0", "light", null));
         System.out.print("Sensor Temperatura Interna ");
         System.out.println(things.execute("/dev/ttyUSB0", "temp_in", null));
         System.out.print("Sensor Temperatura Externa ");
         System.out.println(things.execute("/dev/ttyUSB0", "temp_out", null));
         Things.delay(50);
         System.out.print("Sensor Humidade ");
         System.out.println(things.execute("/dev/ttyUSB0", "humidity", null));
         
         things.execute("/dev/ttyUSB0", "servo", "1");
         delay(1000);
         things.execute("/dev/ttyUSB0", "servo", "180");
         delay(1000);
         things.execute("/dev/ttyUSB0", "servo", "90");
         delay(1000);
        things.close();
    }
}
