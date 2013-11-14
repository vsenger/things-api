/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netomarin.arduhome.view;

import com.netomarin.arduhome.bt.BluetoothClientServer;
import com.netomarin.arduhome.core.FlowManager;
import java.io.ByteArrayOutputStream;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author neto
 */
public class RGBForm extends Form implements CommandListener, ItemStateListener {

    private Gauge rGauge = new Gauge("Red", true, 9, 0);
    private Gauge gGauge = new Gauge("Green", true, 9, 0);
    private Gauge bGauge = new Gauge("Blue", true, 9, 0);

    private StringItem statusItem = new StringItem("Command sent:", null);

    private Command backCommand = new Command("Back", Command.BACK, 0);

    public RGBForm() {
        super("RGB Leds");

        this.append(rGauge);
        this.append(gGauge);
        this.append(bGauge);
        this.setItemStateListener(this);

        this.append(statusItem);

        this.addCommand(backCommand);
        this.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if ( command.equals(backCommand)) {
            FlowManager.getInstance().showMainScreen();
        }
    }

    public void itemStateChanged(Item item) {
        if (item instanceof Gauge) {
            ByteArrayOutputStream bous = new ByteArrayOutputStream();
            bous.write((int)'C');
            bous.write((int)'R');
            bous.write((int)rGauge.getValue());
            BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());
            bous.write((int)'C');
            bous.write((int)'R');
            
            bous.write((int)gGauge.getValue());
            BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());

            bous.write((int)'C');
            bous.write((int)'R');
            bous.write((int)bGauge.getValue());
            BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());
            
            this.statusItem.setText("C" + (byte)rGauge.getValue() + (byte)gGauge.getValue() + (byte)bGauge.getValue());
        }
    }

}
