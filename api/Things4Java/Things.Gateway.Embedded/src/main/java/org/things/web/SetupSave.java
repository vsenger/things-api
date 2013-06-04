/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  
 */
package org.things.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.things.Device;
import static org.things.Things.*;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "SetupSaveSerlvlet", urlPatterns = {"/SetupSave"})
public class SetupSave extends HttpServlet {

  @Override
  public void init() {
    try {      
      Logger.getLogger(SetupSave.class.getName()).log(Level.INFO, 
              "Starting connection with /dev/ttyUSB0");
      Device d = things.discoverySerial("/dev/ttyUSB0", 115200);
      //Device d1 = things.discoveryNetworkThings("ip");
      if (d != null) {
      }
    } catch (Exception ex) {
      Logger.getLogger(SetupSave.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String command = "";
    Enumeration<String> args = request.getParameterNames();
    if (args != null) { 
      if (args.hasMoreElements()) {
        command = args.nextElement();
      }
    } 
    /*System.out.println("Command: " + command);
    System.out.println("URI: " + request.getRequestURI());
    System.out.println("URL : " + request.getRequestURL());*/
    String thingName = request.getRequestURI().replace("/things/thing/", "");
    try {
      String r = null;
      if (thingName != null && thingName.equals("discovery")) {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(things.getThingsString());
      }
      if (command != null && !command.equals("")) {
        r = things.execute(thingName, command);
      } else {
        r = things.execute(thingName);
      }
      if (r != null) {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(r);
      }
    } catch (Exception e) {
    }
  }
  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>



}
