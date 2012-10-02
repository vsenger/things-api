/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.components.rgb;

import org.things.x.component.RGB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.things.Things;

/**
 *
 * @author vsenger
 */
@Stateless
public class RGBBean implements RGB {

  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")
  @EJB
  private Things deviceManager;

  @Asynchronous
  public void changeColor(int red, int blue, int green) {
    try {
      //Arduino.enviar("A-B");
      deviceManager.execute("red", "" + red);
      deviceManager.execute("red", "" + red);
      org.things.Things.delay(20);
      deviceManager.execute("green", "" + green);
      deviceManager.execute("green", "" + green);
      org.things.Things.delay(20);
      deviceManager.execute("blue", "" + blue);
      deviceManager.execute("blue", "" + blue);
    } catch (Exception ex) {
      Logger.getLogger(RGBBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Asynchronous
  public void fade(String color, int start, int end, int time) {
    /*
     * for(int x=0;x<255;x++) {
     *
     * }
     */
    System.out.println("Iniciando fade");
    if (end == start) {
      return;
    }
    long timeStart = System.currentTimeMillis();
    double fracao;
    if (end > start) {
      fracao = time / (end - start);
    } else {
      fracao = time / (start - end);
    }
    //50 1 20
    if (end > start) {
      for (int x = start; x < end; x += 20) {
        deviceManager.execute(color, "" + x);
        org.things.Things.delay(20);
      }
      for (int x = end; x > start; x -= 20) {
        deviceManager.execute(color, "" + x);
        org.things.Things.delay(20);
      }

      deviceManager.execute(color,"0");



    }
    //deviceManager.execute(color, "" + end);

    System.out.println(
            "fim fade");

  }
}
