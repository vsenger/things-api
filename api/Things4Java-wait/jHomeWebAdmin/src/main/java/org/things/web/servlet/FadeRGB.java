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
import org.things.x.component.RGB;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "FadeRGB", urlPatterns = {"/FadeRGB"})
public class FadeRGB extends HttpServlet {

  private static boolean on;
  @EJB
  Things manager;
  @EJB
  private RGB rgb;  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String color = request.getParameter("color");
    String start = request.getParameter("start");
    String end = request.getParameter("end");
    String time = request.getParameter("time");
    try {
      rgb.fade(color, Integer.parseInt(start), Integer.parseInt(end), Integer.parseInt(time));
    } catch (Exception e) {
    }
  }
}
