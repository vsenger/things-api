/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.sensor;

import org.things.x.component.Sensor;
import org.things.x.component.SensorDrivenBean;
import org.things.x.component.Updater;
import org.things.x.components.wallsocket.RelaySchedulerBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

/**
 *
 * @author vsenger
 */
@Singleton
public class EnvironmentBean implements Sensor {

  private Collection<SensorDrivenBean> sensorsBean =
          new ArrayList<SensorDrivenBean>();
  private int temperature1;
  private int temperature;
  private int light;
  private int distance;
  private int heartBeat;
  private char soundNote;

  public int getHeartBeat() {
    return heartBeat;
  }

  public void setHeartBeat(int heartBeat) {
    this.heartBeat = heartBeat;
    notifyAll("heart");

  }
  @EJB
  Updater updater;
  @Resource
  SessionContext ctx;

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
    notifyAll("distance");

  }

  public int getLight() {
    return light;
  }

  public void setLight(int light) {
    this.light = light;
    notifyAll("light");

  }

  public int getTemperature1() {
    return temperature1;
  }

  public void setTemperature1(int temperature1) {
    this.temperature1 = temperature1;
    notifyAll("temperature1");

  }

  public int getTemperature() {
    return temperature;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
    notifyAll("temperature");

  }

  @Override
  public void startUpdater() {
    startUpdater(250);
  }

  @Override
  public void startUpdater(long interval) {
    Logger.getLogger(RelaySchedulerBean.class.getName()).log(Level.INFO,
            "Initializing environment updater.");
    updater.startUpdater(interval);

  }

  public void stop() {
    updater.stop();
  }

  @Override
  public void setSoundNote(char c) {
    soundNote = c;
    notifyAll("sound");
  }

  @Override
  public char getSoundNote() {
    return soundNote;
  }

  @Override
  public void registerSensorBean(SensorDrivenBean o) {
    sensorsBean.add(o);
  }

  private void notifyAll(String sensor) {
    for (SensorDrivenBean x : sensorsBean) {
      x.notifyChange(sensor);
    }
  }
}
