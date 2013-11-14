/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletronlivre.cofffeetimer;

import br.com.eletronlivre.cofffeetimer.arduino.ArduinoBoard;
import br.com.globalcode.eletronlivre.arduino.serial.Arduino;
import javax.ejb.EJB;
import javax.ejb.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;

/**
 *
 * @author vsenger
 */
@Stateless
public class TurnOnBean implements WakeUpScheduler {

  @EJB
  private TurnOffBean turnOffBean;
  @EJB
  private ArduinoBoard board;

  public TurnOnBean() {
  }
  @Resource
  TimerService timerService;
  @Resource
  SessionContext ctx;

  @Override
  public void agendarTarefa(long emQuantoTempo, long porQuantoTempo) {
    System.out.println("Serviço agendado!");
    System.out.println("Ligando em " + emQuantoTempo + " por " + porQuantoTempo + " segundos");
    TimerService agendador = ctx.getTimerService();
    board.testando();
    agendador.createTimer(emQuantoTempo, null);
    turnOffBean.agendarTarefa(porQuantoTempo + emQuantoTempo);

  }




  @Timeout
  public void wakeUp(Timer timer) {
    System.out.println("Vamos ligarrr!");
    try {
      //Arduino.enviar("A+B");
      board.enviar("A+B");
    } catch (Exception ex) {
      Logger.getLogger(TurnOnBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
