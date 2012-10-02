/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.components.wallsocket;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import org.things.Things;

/**
 *
 * @author vsenger
 */
@Stateless
public class RelaySchedulerBean implements org.things.x.component.RelayScheduler{

  @EJB
  private Things deviceManager;

  public RelaySchedulerBean() {
  }
  @Resource
  TimerService timerService;
  @Resource
  SessionContext ctx;

  @Override
  public void schedule(long when, long period, String relayName) {
    Logger.getLogger(RelaySchedulerBean.class.getName()).log(Level.INFO, 
            "Scheduling Wall Socket. Turn on in "
            + when + " miliseconds for "
            + period  + "ms.");
    //OK I also hate this, but I did. Sorry.
    //Schedule two action: ON and OFF
    String turnon[] = {relayName, "1"};
    String turnoff[] = {relayName, "0"};
    
    timerService.createTimer(when, turnon);
    timerService.createTimer(period  + when, turnoff);
  }
  @PreDestroy
  public void stopTimer() {
    Collection<Timer> timers = timerService.getTimers();
    for (Timer t : timers) {
      t.cancel();
    }
  }

  @Timeout
  private void execute(Timer timer) {
    String[] command = (String[]) timer.getInfo();
    try {
    Logger.getLogger(RelaySchedulerBean.class.getName()).log(Level.INFO, 
            "Executing command " + command[1] + " on relay " + command[0]  );
      deviceManager.execute(command[0], command[1]);
    } catch (Exception ex) {
      Logger.getLogger(RelaySchedulerBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
