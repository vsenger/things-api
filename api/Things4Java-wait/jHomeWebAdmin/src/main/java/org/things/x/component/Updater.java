/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.x.component;


/**
 *
 * @author vsenger
 */

public interface Updater {
    public void startUpdater(long interval);    
    public void stop();
}
