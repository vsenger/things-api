/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletronlivre.cofffeetimer;

import br.com.eletronlivre.cofffeetimer.arduino.ArduinoBoard;
import br.com.globalcode.eletronlivre.arduino.serial.Arduino;
import javax.ejb.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;

/**
 *
 * @author vsenger
 */
@Stateless
public class TurnOffBean  {
  @EJB
  ArduinoBoard board;
  
  @Resource
  TimerService timerService;
  @Resource
  SessionContext ctx;

  public void agendarTarefa(long tempo) {
    TimerService agendador = ctx.getTimerService();
    agendador.createTimer(tempo,null);
  }
  
  @Timeout
  private void wakeUp(Timer timer) {
    System.out.println("Vamos desligar!");
    try {
      //Arduino.enviar("A-B");
      board.enviar("A-B");
    } catch (Exception ex) {
      Logger.getLogger(TurnOffBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
