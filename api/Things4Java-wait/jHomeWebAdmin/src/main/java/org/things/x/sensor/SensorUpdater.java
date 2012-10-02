/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.sensor;

import org.things.x.component.Sensor;
import org.things.x.component.Updater;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import org.things.Things;

/**
 *
 * @author vsenger
 */
@Singleton
@Startup
public class SensorUpdater implements Updater {

  @EJB
  private Things deviceManager;
  @EJB
  private Sensor environment;
  @Resource
  TimerService timerService;
  // Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Business Method")

  @Timeout
  private void execute(Timer timer) {
    String sensors = null;
    try {
      sensors = null;
      if (deviceManager.find("light") != null) {
        sensors = deviceManager.execute("light");
        if (sensors == null) {
          return;
        }
        environment.setLight(Integer.parseInt(sensors));
        System.out.println("Ambient Light " + environment.getLight());
      }
      sensors = null;
      if (deviceManager.find("temp") != null) {
        sensors = deviceManager.execute("temp");
        org.things.Things.delay(10);
        sensors = deviceManager.execute("temp");
        org.things.Things.delay(10);
        sensors = deviceManager.execute("temp");

        if (sensors == null) {
          return;
        }

        environment.setTemperature1(Integer.parseInt(sensors));
        System.out.println("Temperature Reading " + environment.getTemperature1());
      }
      //DeviceUtil.delay(2);


      if (deviceManager.find("distance") != null) {
        sensors = null;
        sensors = deviceManager.execute("distance");
        if (sensors == null) {
          return;
        }
        try {
          environment.setDistance(Integer.parseInt(sensors));
        } catch (Exception e) {
          environment.setDistance(0);
        }
        System.out.println("Distance Reading " + environment.getDistance() + " cm");
      }

      if (deviceManager.find("heart") != null) {
        sensors = null;
        sensors = deviceManager.execute("heart");
        org.things.Things.delay(10);
        sensors = deviceManager.execute("heart");
        org.things.Things.delay(10);
        sensors = deviceManager.execute("heart");
        org.things.Things.delay(10);
        if (sensors == null) {
          environment.setHeartBeat(0);
          return;
        }
        try {
          environment.setHeartBeat(Integer.parseInt(sensors));
        } catch (Exception e) {
          environment.setHeartBeat(0);
        }
        System.out.println("Heart Beating  " + environment.getHeartBeat());
      }
    } catch (Exception ex) {
      Logger.getLogger(SensorUpdater.class.getName()).log(
              Level.SEVERE, "Error updating sensors!", ex);
      /*
       * Collection<Timer> timers = timerService.getTimers(); for (Timer t :
       * timers) { t.cancel(); }
       */
    }
  }

  @Override
  public void startUpdater(long interval) {
    Logger.getLogger(SensorUpdater.class.getName()).log(Level.INFO,
            "Initializing environment updater.");
    timerService.createTimer(0, interval, "");
  }

  @PreDestroy
  @Override
  public void stop() {
    Logger.getLogger(SensorUpdater.class.getName()).log(Level.INFO,
            "Stoping sensor reading.");

    Collection<Timer> timers = timerService.getTimers();
    for (Timer t : timers) {
      Logger.getLogger(SensorUpdater.class.getName()).log(Level.INFO,
              "Timer:" + t.hashCode());

      t.cancel();
    }
  }
}
