/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.globalcode.jhome;


/**
 *
 * @author vsenger
 */

public interface Updater {
    public void startUpdater(long interval);    
    public void stop();
}
