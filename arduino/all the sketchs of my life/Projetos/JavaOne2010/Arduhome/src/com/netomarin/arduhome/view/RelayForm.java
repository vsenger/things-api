package com.netomarin.arduhome.view;

import com.netomarin.arduhome.bt.BluetoothClientServer;
import com.netomarin.arduhome.core.FlowManager;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author neto
 */
public class RelayForm extends Form implements CommandListener, ItemCommandListener {

    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command buttonCommand = new Command("Execute", Command.OK, 0);

    private StringItem lightState = new StringItem(null, "Turn ON", Item.BUTTON);
    private StringItem discoMode = new StringItem(null, "DISCO!!", Item.BUTTON);

    private boolean lightOn = false;
    private boolean discoOn = false;
    String msg = null;

    public RelayForm() {
        super("Relay Control");

        this.lightState.setLayout(Item.LAYOUT_NEWLINE_AFTER);

        this.append(lightState);
        this.append(discoMode);
        lightState.setDefaultCommand(buttonCommand);
        lightState.setItemCommandListener(this);
        discoMode.setDefaultCommand(buttonCommand);
        discoMode.setItemCommandListener(this);

        this.addCommand(backCommand);
        this.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if ( command.equals(backCommand)) {
            FlowManager.getInstance().showMainScreen();
        }
    }

    public void commandAction(Command command, Item item) {
        if ( item.equals(lightState)) {
            if ( lightOn ) {
                msg = "A+A";
                lightState.setText("Turn OFF");
            } else {
                msg = "A-A";
                lightState.setText("Turn ON");
            }

            lightOn = !lightOn;
        } else if ( item.equals(discoMode)) {
            if ( discoOn ) {
                msg = "A+C";
                lightState.setText("STOP Disco!");
            } else {
                msg = "A-C";
                lightState.setText("DISCO!!");
            }

            discoOn = !discoOn;
        }

        BluetoothClientServer.getInstance().sendMessage(msg.getBytes());
    }
}