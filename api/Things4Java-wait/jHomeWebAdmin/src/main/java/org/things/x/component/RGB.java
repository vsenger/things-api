/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.component;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author vsenger
 */
@Remote 
@Local
public interface RGB {
  public void changeColor(int red, int blue, int green) ;
  public void fade(String color, int start, int end, int time);
  
}
