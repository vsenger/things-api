/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.web.rest;

import org.things.x.component.Sensor;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author vsenger
 */
@Path("sensor")
@Stateless 
public class SensorResource {
  @EJB
  private Sensor environment;
  @Context
  private UriInfo context;

  /** Creates a new instance of SensorResource */
  public SensorResource() {
  }

  /**
   * Retrieves representation of an instance of br.com.globalcode.javahome.rest.SensorResource
   * @return an instance of java.lang.String
   */
  @GET
  @Produces("application/json")
  public SensorHelper getJson() {
    //TODO return proper representation object
    SensorHelper s = new SensorHelper();
    
    s.setTemperature1("" + environment.getTemperature1());
    s.setTemperature("" + environment.getTemperature());
    s.setLight("" + environment.getLight());
    s.setDistance("" + environment.getDistance());
    s.setHeartBeat("" + environment.getHeartBeat());
    s.setNote("" + environment.getSoundNote());    
    return s;
  }

  /**
   * PUT method for updating or creating an instance of SensorResource
   * @param content representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */
  @PUT
  @Consumes("application/json")
  public void putJson(String content) {
  }
}
