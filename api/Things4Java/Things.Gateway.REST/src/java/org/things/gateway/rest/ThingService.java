package org.things.gateway.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
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

    public ThingService() {
        Properties prop = new Properties();
        try {
            //load a properties file
            String path = System.getProperty("things.config", "/etc");

            prop.load(new FileInputStream(path + "/things.properties"));
            FTDI = prop.getProperty("arduino.port");
            PHONE = prop.getProperty("cell.port");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (FTDI == null || FTDI.equals("")) {
            FTDI = "/dev/ttyUSB0";
        }
    }
    int relays[] = {0, 24, 25, 22, 27, 18, 23};
    String FTDI, PHONE;
    Runtime rt = Runtime.getRuntime();
    
    //playing audio service
    @GET
    @Path("/audio/{command}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String audio(@PathParam("command") String commandP) {
        try {
            Process pr1 = rt.exec("mpg321 /home/pi/audio/" + commandP + ".mp3");
            return "executed";
        } catch (IOException ex) {
            Logger.getLogger(ThingService.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    
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
    @Path("/phone/{command}/{number}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String phone(@PathParam("command") String commandP, @PathParam("number") String numberP) {
        if (commandP.equals("call")) {
            System.out.println("Call" + numberP);
            things.send(PHONE, "ATD" + numberP + "\r");
        }
        return "executed";
    }

    @GET
    @Path("/phone/{command}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String phoneCommand(@PathParam("command") String commandP) {
        if (commandP.equals("init")) {
            System.out.println("Init");
            things.send(PHONE, "AT+SBAN=1\r");
        }
        else if (commandP.equals("hangup")) {
            System.out.println("Hangup");
            things.send(PHONE, "ATH\r");
        }
        else if (commandP.equals("answer")) {
            System.out.println("Answer");
            things.send(PHONE, "ATA\r");
        }

        return "executed";
    }
 
    @GET
    @Path("{command}")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public String execute(@PathParam("command") String commandP) {
        return things.execute(FTDI, commandP, null);
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
            
            things.execute(FTDI, commandP, param);
            return commandP;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ThingBean getTextAsBean() {
        return new ThingBean("");
    }
}
