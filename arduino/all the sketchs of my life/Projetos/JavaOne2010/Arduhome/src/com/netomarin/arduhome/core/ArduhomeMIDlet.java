/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netomarin.arduhome.core;

import com.netomarin.arduhome.bt.BluetoothClientServer;
import com.netomarin.arduhome.view.ConnectionForm;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author neto
 */
public class ArduhomeMIDlet extends MIDlet {

    private static ArduhomeMIDlet instance;
    private static String centralMacAddress;

    public void startApp() {
        if ( instance == null )
            instance = this;

        centralMacAddress = FlowManager.loadSettings();
        Display.getDisplay(this).setCurrent(new ConnectionForm());
        BluetoothClientServer.getInstance().initClient();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        this.notifyDestroyed();
    }

    public static ArduhomeMIDlet getInstance() {
        return instance;
    }

    public static String getCentralMacAddress() {
        return centralMacAddress;
    }
}