package org.things.me.app;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import org.things.Thing;
import org.things.Things;
import org.things.bluetooth.BluetoothDiscovery;

public class ThingsSetupForm extends Form {

  private Button button;
  private TextArea text;
  private com.sun.lwuit.List devices;
  private ThingsForm targetForm;

  public ThingsSetupForm(ThingsForm targetForm) {
    super("Thing Setup");
    this.targetForm = targetForm;
    
    this.setLayout(new BorderLayout());
    if (BluetoothDiscovery.getInstance().getDevices() != null) {
      devices = new List(Things.everyThing());
    } else {
      devices = new List();
    }
    button = new Button("Choose");
    button.setTextPosition(Label.BOTTOM);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Thing thing = (Thing) devices.getSelectedItem();
        if(thing!=null) {
          setupNewThing(thing);
        }
      }
    });
    Command exitComm = new Command("Back") {
      public void actionPerformed(ActionEvent ev) {
        goBack();
      }
    };
    Command setupComm = new Command("Reset thing") {
      public void actionPerformed(ActionEvent ev) {
        reset();
      }
    };
    this.addCommand(exitComm);
    this.addCommand(setupComm);
    this.addComponent(BorderLayout.NORTH, button);
    this.addComponent(BorderLayout.CENTER, devices);

  }
  public void setupNewThing(Thing newThing) {
    targetForm.setThingURL(newThing.getThingURL());
  }
  public void goBack() {
    if (targetForm != null) {
      targetForm.showBack();
    }
  }
  public void reset() {
    targetForm.setThingURL("");
    targetForm.saveThingURL();
  }
}