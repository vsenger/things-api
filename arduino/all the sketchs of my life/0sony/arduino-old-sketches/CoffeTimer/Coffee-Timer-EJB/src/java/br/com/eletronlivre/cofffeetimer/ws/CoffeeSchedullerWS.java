/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eletronlivre.cofffeetimer.ws;

import br.com.eletronlivre.cofffeetimer.WakeUpScheduler;
import java.rmi.RemoteException;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author vsenger
 */
@WebService()
@Stateless()
public class CoffeeSchedullerWS {

  @EJB
  private WakeUpScheduler ejbRef;// Add business logic below. (Right-click in editor and choose
  // "Insert Code > Add Web Service Operation")

  @WebMethod(operationName = "agendarTarefa")
  public void agendarTarefa(@WebParam(name = "time") long time, @WebParam(name = "tt") long tt) throws RemoteException {
    ejbRef.agendarTarefa(time, tt);
  }
}
