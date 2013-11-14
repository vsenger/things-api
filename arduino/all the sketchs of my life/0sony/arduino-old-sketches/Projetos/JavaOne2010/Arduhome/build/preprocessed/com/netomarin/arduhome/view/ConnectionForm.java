/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netomarin.arduhome.view;

import com.netomarin.arduhome.core.ArduhomeMIDlet;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;

/**
 *
 * @author neto
 */
public class ConnectionForm extends Form implements CommandListener {

    private Gauge gauge;
    private ImageItem logoItem;
    private Command exitCommand = new Command("Exit", Command.EXIT, 0);

    private static ConnectionForm instance;

    public ConnectionForm() {
        super("Connecting");
        instance = this;
        try {
            logoItem = new ImageItem(null, Image.createImage("arduhomelogo.png"), Item.LAYOUT_VCENTER | Item.LAYOUT_CENTER, null);
            this.append(logoItem);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

        this.gauge = new Gauge("Conectando a Central...", false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
        this.append(gauge);

        this.addCommand(exitCommand);
        this.setCommandListener(this);
    }

    public static ConnectionForm getInstance() {
        return instance;
    }

    public void commandAction(Command cmnd, Displayable dsplbl) {
        if ( cmnd.equals(exitCommand)) {
            ArduhomeMIDlet.getInstance().destroyApp(true);
        }
    }

    public void updateGaugeStatus(String status) {
        gauge.setLabel(status);
    }
}