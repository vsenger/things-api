/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web.servlet;

import org.things.x.component.Relay;
import org.things.x.component.Relay;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "Light", urlPatterns = {"/Light"})
public class Light extends HttpServlet {

  private static boolean on;
  @EJB
  Relay light;
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    if (on) {
      light.turnOff("relay1");
      on = false;
    } else {
      light.turnOn("relay1");
      on = true;
    }

  }

}
