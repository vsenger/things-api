/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.eletronlivre.cofffeetimer.WakeUpScheduler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
@WebServlet(name = "Agendador", urlPatterns = {"/Agendador"})
public class Agendador extends HttpServlet {

  @EJB
  private WakeUpScheduler wakeUpBean;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      int tipo = Integer.parseInt(request.getParameter("tipoValor"));
      int emQuantoTempo = Integer.parseInt(request.getParameter("txtEmQuantoTempo"));
      int porQuantoTempo = Integer.parseInt(request.getParameter("txtPorQuantoTempo"));
      Calendar cal = Calendar.getInstance();
      cal.add(tipo == 2 ? Calendar.MINUTE : Calendar.SECOND, emQuantoTempo);
      request.setAttribute("horarioAgendado", cal.getTime());

      emQuantoTempo *= 1000;
      porQuantoTempo *= 1000;
      if (tipo == 2) {
        emQuantoTempo *= 60;
        porQuantoTempo *= 60;
      }
      wakeUpBean.agendarTarefa(emQuantoTempo, porQuantoTempo);
      RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
      dispatcher.forward(request, response);
    } finally {
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
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
   * Handles the HTTP <code>POST</code> method.
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
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
