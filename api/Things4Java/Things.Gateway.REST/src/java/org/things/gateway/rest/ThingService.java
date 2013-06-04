package org.things.gateway.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static org.things.Things.*;
/*
 * O mano aqui é irado. Ele vai rotear requisições REST
 * em requisições Serial usando RXTX no protocolo simplão
 * que inventamos. Esta classe harmoniza com queijo provolone
 * e cervejas.
 */

@Path("/")
public class ThingService {
  String ROBOT = "/dev/ttyUSB0";
  
  @GET
  @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
  public String light() {
    return things.execute(ROBOT, "sl", "4");
  }
  
  @GET
  @Path("{command}")  
  @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
  public String execute(@PathParam("command") String commandP) {
    return things.execute(ROBOT, commandP, "0");
  }
  
  @GET
  @Path("{command}/{param}")  
  @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
  public String execute(@PathParam("command") String commandP, @PathParam("param") String param) {
    return things.execute(ROBOT, "servo", "1");
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public ThingBean getTextAsBean() {
    return new ThingBean("");
  }
}
