/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.eletronlivre.arduino.osgi;

/**
 *
 * @author vsenger
 */
public class ArduinoImpl implements Arduino {

  public void send(byte[] b) {
    System.out.println("UIA!");
  }

}
