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
import org.things.x.component.Sensor;
import org.things.x.component.SensorDrivenBean;
/**
 *
 * @author vsenger
 */
@WebServlet(name = "HeartDrivenBean",
urlPatterns = {"/HeartDrivenBean"},
loadOnStartup = 1)
public class HeartDrivenBean extends HttpServlet
        implements SensorDrivenBean {

  @EJB(name = "deviceManagerBean")
  private Things deviceManager;
  @EJB
  private Sensor environment;

  @Override
  public void notifyChange(String sensor) {
    if (sensor.equals("heart") && environment.getHeartBeat() > 110) {
      System.out.println("VAI MORRERRR!!!!!");
      deviceManager.execute("buzz", "40");
      deviceManager.execute("relay1", "1");
      org.things.Things.delay(500);
      deviceManager.execute("relay1", "0");
      deviceManager.execute("buzz", "0");
    }

  }

  @Override
  public void init() {
    environment.registerSensorBean(this);
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
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
