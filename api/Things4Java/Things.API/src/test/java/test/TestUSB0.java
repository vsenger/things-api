/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.things.Things;
import static org.things.Things.*;

/**
 *
 * @author vsenger
 */
public class TestUSB0 {

  public static void main(String[] args) {
    System.out.print("Sensor Luz");
    System.out.println(things.execute("/dev/ttyUSB0", "sl", "4"));
    System.out.print("Sensor Temperatura");
    System.out.println(things.execute("/dev/ttyUSB0", "st", "4"));
    System.out.print("Sensor Distancia 1");
    System.out.println(things.execute("/dev/ttyUSB0", "sd1", "4"));
    System.out.print("Sensor Distancia 2");
    System.out.println(things.execute("/dev/ttyUSB0", "sd2", "4"));
    things.execute("/dev/ttyUSB0", "frente", "4");
    delay(1000);
    Things.things.execute("/dev/ttyUSB0", "parar", "4");
    delay(1000);
    Things.things.execute("/dev/ttyUSB0", "servo", "90");
    delay(1000);
    things.execute("/dev/ttyUSB0", "servo", "110");
    Things.delay(1000);
    things.execute("/dev/ttyUSB0", "servo", "130");
    delay(1000);
    things.close();
  }
}
