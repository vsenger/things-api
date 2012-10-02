/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web.servlet;


import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.things.Things;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "Devices", urlPatterns = {"/Devices"})
public class ThingsServlet extends HttpServlet {

  private static boolean on;
  @EJB
  Things manager;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String component = request.getParameter("component");
    String command = request.getParameter("command");
    
    try {
      String r = null;
      if(command!=null && command.equals("discovery")) {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(manager.getThingsString());
        System.out.println("Called discovery string android!");
      }
      if(command!=null && !command.equals("")) r = manager.execute(component, command);
      else r = manager.execute(component);
      if(r!=null) {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(r);
      }
    } catch (Exception e) {
    }
  }
}
