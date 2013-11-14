/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netomarin.arduhome.view;

import com.netomarin.arduhome.bt.BluetoothClientServer;
import com.netomarin.arduhome.core.ArduhomeMIDlet;
import com.netomarin.arduhome.core.FlowManager;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author neto
 */
public class MainForm extends List implements CommandListener {

    private Command exitCommand = new Command("Exit", Command.EXIT, 0);
    private Command selectCommand = new Command("Select", Command.OK, 1);
    private Command settingsCommand = new Command("Settings", Command.SCREEN, 2);
    
    public MainForm() {
        super("Arduhome", List.IMPLICIT, new String[]{"Relay", "Dimmer", "RGB Leds"}, null);

        this.setSelectCommand(selectCommand);
        this.addCommand(settingsCommand);
        this.addCommand(exitCommand);
        this.setCommandListener(this);
    }

    public void commandAction(Command cmnd, Displayable dsplbl) {
        if ( cmnd.equals(exitCommand) ) {
            BluetoothClientServer.getInstance().CloseAll();
            ArduhomeMIDlet.getInstance().destroyApp(true);
        } else if ( cmnd.equals(settingsCommand) ) {
            Display.getDisplay(ArduhomeMIDlet.getInstance()).setCurrent(new SettingsForm());
        } else if ( cmnd.equals(selectCommand) ) {
            switch (this.getSelectedIndex() ) {
                case 0:
                    FlowManager.getInstance().showRelayForm();
                    break;
                case 1:
                    FlowManager.getInstance().showDimmerForm();
                    break;
                case 2:
                    FlowManager.getInstance().showRGBForm();
                    break;
            }
            
        }
    }
}