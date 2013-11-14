<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cafeteira na Web</title>
  </head>
  <body>
    <h1>Vida além do Java...</h1>
    <hr/>
    <img src="duke.gif" align="left"/>
    <h2>Agendar por tempo</h2>
    <form action="Agendador" method="GET">
      <input type="radio" name="tipoValor" value="1" checked/> Segundos
      <input type="radio" name="tipoValor" value="2"/> Minutos 
      <br/>
      Em quanto tempo que ligar a cafeteira ? <input type="text" name="txtEmQuantoTempo"/><br/>
      Por quanto tempo quer ligar a cafeteira ? <input type="text" name="txtPorQuantoTempo"/><br/>
      <input type="submit" />
    </form>
    <%
      if (request.getAttribute("horarioAgendado") != null) {
        Date horario = (Date) request.getAttribute("horarioAgendado");
        out.println("<p>Café agendado para ");
        out.println(horario);
        out.println("</p>");
      }
    %>
  </body>

</html>
