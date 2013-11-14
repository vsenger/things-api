/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.eletronlivre.cofffeetimer;

import java.rmi.RemoteException;
import javax.ejb.Remote;

/**
 *
 * @author vsenger
 */
@Remote
public interface WakeUpScheduler {

  void agendarTarefa() throws RemoteException;
  void agendarTarefa(long time, long tt) throws RemoteException;

}
