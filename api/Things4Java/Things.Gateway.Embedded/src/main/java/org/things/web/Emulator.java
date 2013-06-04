/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vsenger
 */
@WebServlet(name = "Emulator", urlPatterns = {"/ThingsEmulator/*"})
public class Emulator extends HttpServlet {

  static Collection<Component> components;

  @Override
  public void init() {
    if (components == null) {
      components = new ArrayList<Component>();
      components.add(new Component("relay-1", "digital", 0, 1));
      components.add(new Component("relay-2", "digital", 0, 1));
      components.add(new Component("light", "analog", 0, 1));
      components.add(new Component("temperature", "analog", 0, 1));
      components.add(new Component("red", "pwm", 0, 1));
      components.add(new Component("green", "pwm", 0, 1));
      components.add(new Component("blue", "pwm", 0, 1));
    }
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    try {

      String component =
              request.getPathInfo() != null ? request.getPathInfo().substring(
              1, request.getPathInfo().length()) : null;
      String param = request.getQueryString();
      if (component == null || component.equals("") || component.equals("discovery")) {
        discovery(out);
      } else {
        execute(component, param, out);

      }
    } finally {
      out.close();
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

  private void discovery(PrintWriter out) {
    out.print("emulator-device|" + components.size() + "|");
    for (Component c : components) {
      out.print(c.name + "|" + c.type + "|" + c.port + "|" + c.value + "|");
    }
  }

  private void execute(String componentName, String value, PrintWriter out) {
    System.out.println("Component: " + componentName);
    System.out.println("Value: " + value);
    Component c = findComponent(componentName);
    if (c == null) {
      return;
    }
    if (value != null) {
      c.value = Integer.parseInt(value);
    } else {
      c.value = new Random().nextInt() / 10000;
    }

    out.print(value);
  }

  Component findComponent(String name) {
    Component r = null;
    for (Component c : components) {
      if (c.name.equals(name)) {
        r = c;
      }
    }
    return r;
  }

  class Component {

    String name;
    String type;
    int value;
    int port;

    public Component(String name, String type, int value, int port) {
      this.name = name;
      this.type = type;
      this.value = value;
      this.port = port;
    }
  }
}
