/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.globalcode.jhome;


/**
 *
 * @author vsenger
 */

public interface Sensor {
  public void registerSensorBean(SensorDrivenBean o);
  public void stop();
  public void startUpdater(long interval);

  public void startUpdater();

  public int getDistance();

  public void setDistance(int distance);

  public int getLight();

  public void setLight(int light);

  public int getTemperature();

  public void setTemperature(int temperature);
  public int getTemperature1();

  public void setTemperature1(int temperature1);  
  public void setHeartBeat(int hb);
  public int getHeartBeat();
  public void setSoundNote(char c);
  public char getSoundNote();
  
}
