package com.netomarin.arduhome.core;

import com.netomarin.arduhome.view.DimmerForm;
import com.netomarin.arduhome.view.MainForm;
import com.netomarin.arduhome.view.RGBForm;
import com.netomarin.arduhome.view.RelayForm;
import com.netomarin.arduhome.view.SettingsForm;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author neto
 */
public class FlowManager {

    private static FlowManager instace;
    private MainForm mainForm;
    private RelayForm relayForm;
    private DimmerForm dimmerForm;
    private RGBForm rgbForm;

    private FlowManager() {
    }

    public static FlowManager getInstance() {
        if ( instace == null )
            instace = new FlowManager();

        return instace;
    }

    public void showMainScreen() {
        if ( mainForm == null )
            mainForm = new MainForm();

        Display.getDisplay(ArduhomeMIDlet.getInstance()).setCurrent(mainForm);
    }

    public void showRelayForm() {
        if ( relayForm == null )
            relayForm = new RelayForm();

        Display.getDisplay(ArduhomeMIDlet.getInstance()).setCurrent(relayForm);
    }

    public void showDimmerForm() {
        if ( dimmerForm == null )
            this.dimmerForm = new DimmerForm();

        Display.getDisplay(ArduhomeMIDlet.getInstance()).setCurrent(dimmerForm);
    }

    public void showRGBForm() {
        if ( rgbForm == null )
            this.rgbForm = new RGBForm();

        Display.getDisplay(ArduhomeMIDlet.getInstance()).setCurrent(rgbForm);
    }

    public void sendString(String stringToSend) {
        
    }

    public static String loadSettings() {
        RecordStore db = null;
        String macAddress = null;
        
        try {
            db = RecordStore.openRecordStore("Settings", true);
            if ( db.getNumRecords() > 0 ) {
                macAddress = new String(db.getRecord(0));
            } else {
                macAddress = SettingsForm.DEFAULT_MAC;
            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } finally {
            try {
                db.closeRecordStore();
            } catch (RecordStoreException ex) {}
        }

        return macAddress;
    }

    public static void saveSettings(String macAddress) {
        RecordStore db = null;
        
        try {
            db = RecordStore.openRecordStore("Settings", true);

            if ( db.getNumRecords() > 0 ) {
                db.setRecord(0, macAddress.getBytes(), 0, macAddress.getBytes().length);
            } else {
                db.addRecord(macAddress.getBytes(), 0, macAddress.getBytes().length);
            }

        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } finally {
            try {
                db.closeRecordStore();
            } catch (RecordStoreException ex) {}
        }
    }
}
