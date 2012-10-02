package org.things.web.rest;

import java.text.DecimalFormat;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vsenger 
 */
@XmlRootElement(name = "sensor")
public class SensorHelper {
  String heartBeat;

  public String getHeartBeat() {
    return heartBeat;
  }

  public void setHeartBeat(String heartBeat) {
    this.heartBeat = heartBeat;
  }
  String temperature;
  String temperature1;
  String light;
  String distance;
  String note;
  String temperatureCelsius;
  String temperatureCelsius1;
  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public String getLight() {
    return light;
  }

  public void setLight(String light) {
    this.light = light;
  }
  public String getTemperature1() {
    return temperature1;
  }

  public void setTemperature1(String temperature) {
    this.temperature1 = temperature;
  }
  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }
  public void setTemperatureCelsius(String temperature) {
    this.temperature = temperature;
  }

  public  String getTemperatureCelsius() {
    float f = Float.parseFloat(temperature);
    double t = f  * 0.00488;
    t*=100;
    DecimalFormat dc = new DecimalFormat("##.00");
    return dc.format(t);
  }
  
  public void setTemperatureCelsius1(String temperature1) {
    this.temperature1 = temperature1;
  }

  public String getNote() {
    return note + " " + getHumanNote(note.charAt(0));          
  }
  public String getHumanNote(char c) {
    if(c=='E') return "MI";
    else if(c=='G') return "Sol";
    else if(c=='e') return "mi";
    else if(c=='A') return "Lá";
    else if(c=='D') return "Ré";
    else if(c=='B') return "Si";
    else return "";
    
  }
  public void setNote(String note) {
    this.note = note;
  }

  public  String getTemperatureCelsius1() {
    float f = Float.parseFloat(temperature1);
    double t = f  * 0.00488;
    t*=100;
    DecimalFormat dc = new DecimalFormat("##.00");
    return dc.format(t);
  }  
}
