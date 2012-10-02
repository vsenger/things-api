/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.globalcode.jhome;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author vsenger
 */
@Local
public interface Relay {
  public void turnOn(String name) ;
  public void turnOff(String name) ;
}
