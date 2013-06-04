package org.things.me.app;

import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import org.things.ThingException;
import org.things.Things;

/**
 *
 * @author globalcode triangulum
 * @mrquinta
 * @vsenger
 * @netomarin
 */
public abstract class ThingsForm extends Form {

  private ThingsForm previousForm;
  private String thingCommand;
  private String thingURL;
  private String thingName;

  public String getThingURL() {
    return thingURL;
  }

  public void setThingURL(String thingURL) {
    this.thingURL = thingURL;
  }

  public String getThingName() {
    return thingName;
  }

  public void setThingName(String thingName) {
    this.thingName = thingName;
  }

  public ThingsForm(ThingsForm previousForm, String title, String name) {
    super(title);
    this.thingName = name;
    this.previousForm = previousForm;
    restoreThingURL();
    Command exitComm = new Command("Back") {
      public void actionPerformed(ActionEvent ev) {
        goBack();
      }
    };
    Command setupComm = new Command("Setup") {
      public void actionPerformed(ActionEvent ev) {
        setupForm();
      }
    };

    this.addCommand(exitComm);
    this.addCommand(setupComm);
  }

  public void setupForm() {
    ThingsSetupForm form = new ThingsSetupForm(ThingsForm.this);
    form.show();

  }

  public void restoreThingURL() {
    RecordStore db = null;
    try {
      db = RecordStore.openRecordStore(thingName, true);
      if (db.getNumRecords() > 0) {
        thingURL = new String(db.getRecord(1));
      } else {
        thingURL = "";
      }
    } catch (RecordStoreException ex) {
      Dialog d = new Dialog("open");
      d.setDialogType(Dialog.TYPE_ERROR);
      d.addComponent(new Label(ex.getMessage()));
      d.setTimeout(10000);
      d.show();
    } finally {
      try {
        db.closeRecordStore();
      } catch (RecordStoreException ex) {
        Dialog d = new Dialog("Close");
        d.setDialogType(Dialog.TYPE_ERROR);
        d.addComponent(new Label(ex.getMessage()));
        d.setTimeout(10000);
        d.show();
      }
    }
  }

  public void saveThingURL() {
    RecordStore db = null;
    try {
      db = RecordStore.openRecordStore(this.thingName, true);
      if (db.getNumRecords() > 0) {
        db.setRecord(1, thingURL.getBytes(), 0, thingURL.getBytes().length);
      } else {
        db.addRecord(thingURL.getBytes(), 0, thingURL.getBytes().length);
      }
    } catch (RecordStoreException ex) {
      Dialog d = new Dialog("Op");
      d.setDialogType(Dialog.TYPE_ERROR);
      d.addComponent(new Label(ex.getMessage()));
      d.setTimeout(10000);
      d.show();
    } finally {
      try {
        db.closeRecordStore();
      } catch (RecordStoreException ex) {
        Dialog d = new Dialog("Cl");
        d.setDialogType(Dialog.TYPE_ERROR);
        d.addComponent(new Label(ex.getMessage()));
        d.setTimeout(10000);
        d.show();
      }
    }
  }

  public void goBack() {
    if (previousForm != null) {
      previousForm.showBack();
    }
  }

  public void sendCommand(String command) throws ThingException {
    if (this.thingURL.equals("")) {
      if (Things.anything() == null) {
        return;
      }
      Things.send(command);
      thingURL = Things.anything().getThingURL();
      saveThingURL();
    } else {
      Things.bluetooth(thingURL).send(command);
    }
  }

  public String sendReceiveCommand(String command) throws ThingException {
    if (this.thingURL.equals("")) {
      if (Things.anything() == null) {
        return "";
      }
      Things.send(command);
      thingURL = Things.anything().getThingURL();
      saveThingURL();
      Things.delay(50);
      return Things.receive();
    } else {
      Things.bluetooth(thingURL).send(command);
      Things.delay(50);
      return Things.receive();
    }
  }

  public String getThingCommand() {
    return thingCommand;
  }

  public void setThingCommand(String headerCode) {
    this.thingCommand = headerCode;
  }

  public ThingsForm getPreviousForm() {
    return previousForm;
  }

  public void ThingPreviousForm(ThingsForm previousForm) {
    this.previousForm = previousForm;
  }
}
