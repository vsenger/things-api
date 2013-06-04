/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import static org.things.Things.things;

/**
 * Web application lifecycle listener.
 *
 * @author vsenger
 */
@WebListener()
public class Stop implements ServletContextListener {

 
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Logger.getLogger(
            Stop.class.getName()).log(Level.INFO,
            "Stoping Things Server - Stuff 0.1 beta");
    things.close();
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {
  }
}
