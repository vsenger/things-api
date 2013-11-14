package com.netomarin.arduhome.view;

import com.netomarin.arduhome.core.FlowManager;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author neto
 */
public class SettingsForm extends Form implements CommandListener {

    //public static final String DEFAULT_MAC = "0006660412A9"; //bluesmirf
    public static final String DEFAULT_MAC = "00066604127D"; //bluesmirf
    //public static final String DEFAULT_MAC = "000666041225"; //bluesmirf

    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command saveCommand = new Command("Save", Command.OK, 1);

    private TextField macAddressTextField;

    public SettingsForm() {
        super("Settings");

        String macAddress = FlowManager.loadSettings();
        macAddressTextField = new TextField("Bluetooth: ",
                macAddress == null ? DEFAULT_MAC : macAddress, 12, TextField.ANY);
        this.append(macAddressTextField);

        this.setCommandListener(this);
        this.addCommand(backCommand);
        this.addCommand(saveCommand);
    }
    
    public void commandAction(Command cmnd, Displayable dsplbl) {
        if ( cmnd.equals(backCommand) ) {
            FlowManager.getInstance().showMainScreen();
        } else if ( cmnd.equals(saveCommand)) {
            FlowManager.saveSettings(macAddressTextField.getString());
            FlowManager.getInstance().showMainScreen();
        }
    }
}