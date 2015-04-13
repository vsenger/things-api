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
        Device things = new SerialDevice("/dev/ttyUSB0", 115200);
        things.open();
        Things.delay(1500);
        /*things.send("humidity");
         Things.delay(40);

         String s = things.receive();
         System.out.println("Luz: " + s);
         things.close();*/
        //things.send("/dev/ttyUSB0", "sl");
        //delay(3000);
        /*things.send("frente?5");
         delay(3000);
         things.send("parar");
         delay(1000);
         things.send("re?5");
         delay(3000);
         things.send("parar");

         delay(1000);*/

        String t;
        for (int x = 0; x < 100; x++) {
            things.send("light");
            delay(30);
            t = things.receive();
            System.out.println("light " + t);

            things.send("alcohol");
            delay(50);
            t = things.receive();
            System.out.println("alcohol " + t);

            things.send("pot");
            delay(50);
            t = things.receive();
            System.out.println("potenciometer " + t);
            
            things.send("distance");
            delay(350);
            t = things.receive();
            System.out.println("distance " + t);
            
            things.send("clock");
            delay(350);
            t = things.receive();
            System.out.println("date / time " + t);            
            things.send("temp");
            delay(350);
            t = things.receive();
            System.out.println("temperature " + t);

            things.send("humidity");
            delay(350);
            t = things.receive();
            System.out.println("humidity " + t);
        }
        things.close();
    }
}
