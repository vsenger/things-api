/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web.servlet;

import org.things.x.component.RGB;
import org.things.x.component.RGB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "RGBController", urlPatterns = {"/RGBController"})
public class RGBController extends HttpServlet {

  @EJB
  private RGB rgb;

  
  protected void processRequest(HttpServletRequest request, 
          HttpServletResponse response)
          throws ServletException, IOException {
    int red = Integer.parseInt(request.getParameter("red"));
    int green = Integer.parseInt(request.getParameter("green"));
    int blue = Integer.parseInt(request.getParameter("blue"));
    rgb.changeColor(red, blue, green);
  }

  public int map(int val) {
    return (int) (((float) (val / 255f)) * 9);
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
