package com.netomarin.arduhome.view;

import com.netomarin.arduhome.bt.BluetoothClientServer;
import com.netomarin.arduhome.core.FlowManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author netomarin
 */
public class DimmerForm extends Form implements CommandListener, ItemCommandListener, ItemStateListener {

    private Gauge dimmerGauge = new Gauge("Dimmer", true, 9, 0);

    private Command sendCommand = new Command("Send", Command.OK, 0);
    private Command backCommand = new Command("Back", Command.BACK, 0);

    private StringItem statusItem = new StringItem("Command sent:", null);

    public DimmerForm() {
        super("Dimmer Control");

        this.append(dimmerGauge);
        this.setItemStateListener(this);
        this.append(statusItem);

        this.addCommand(backCommand);
        this.setCommandListener(this);

        //reseting dimmer
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        bous.write((byte)'B');
        bous.write(9);
        BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());
    }

    public void commandAction(Command command, Displayable displayable) {
        if ( command.equals(backCommand)) {
            FlowManager.getInstance().showMainScreen();
        }
    }

    public void commandAction(Command cmnd, Item item) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void itemStateChanged(Item item) {
        if (item.equals(dimmerGauge)) {
            try {
                ByteArrayOutputStream bous = new ByteArrayOutputStream();
                bous.write((int)'B');
                int dimmerValue = dimmerGauge.getValue();
                if ( dimmerValue == 0 )
                    dimmerValue = 9;
                bous.write(dimmerValue);
                BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());
                this.statusItem.setText("B" + dimmerGauge.getValue());
                bous.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
