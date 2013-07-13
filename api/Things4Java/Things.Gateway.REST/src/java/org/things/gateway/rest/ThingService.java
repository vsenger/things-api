package org.things.gateway.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    int relays[] = {0, 24, 25, 22, 27, 18, 23};
    String ARDUINO = "/dev/ttyUSB0";
    Runtime rt = Runtime.getRuntime();

    @GET
    @Path("/script/{command}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String script(@PathParam("command") String commandP) {
        try {
            Process pr1 = rt.exec("/home/pi/scripts/" + commandP + ".sh");
            return "executed";
        } catch (IOException ex) {
            Logger.getLogger(ThingService.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    @GET
    @Path("{command}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String execute(@PathParam("command") String commandP) {
        return things.execute(ARDUINO, commandP, null);
    }

    @GET
    @Path("{command}/{param}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String execute(@PathParam("command") String commandP, @PathParam("param") String param) {
        if (commandP.startsWith("port")) {
            try {
                int i = Integer.parseInt(param);
                int p = Integer.parseInt(commandP.substring(4, commandP.length()));
                Process pr1 = rt.exec("sudo gpio -g mode " + p + " output");
                Process pr2 = rt.exec("sudo gpio -g write " + p + " " + i);
                return "port " + p + ": " + (i == 0 ? " off " : " on");

            } catch (IOException ex) {
                Logger.getLogger(ThingService.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }

        } else if (commandP.startsWith("relay")) {
            Runtime rt = Runtime.getRuntime();
            try {
                //por numero de relé e com inversao
                int i = Integer.parseInt(param);
                int p = Integer.parseInt(commandP.substring(5, commandP.length()));
                Process pr1 = rt.exec("sudo gpio -g mode " + relays[p] + " output");
                Process pr2 = rt.exec("sudo gpio -g write " + relays[p] + " " + (i == 0 ? 1 : 0));
                return "relay " + p + ", port " + relays[p] + ": " + (i == 0 ? " off" : " on");

            } catch (IOException ex) {
                Logger.getLogger(ThingService.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage();
            }

        } else {
            return things.execute(ARDUINO, commandP, param);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ThingBean getTextAsBean() {
        return new ThingBean("");
    }
}
