package org.things.me.app.automation;

import org.things.me.app.ThingsForm;
import com.sun.lwuit.Button;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import java.io.IOException;
import org.things.ThingException;
import org.things.me.app.ThingsMenu;

public class RelayForm extends ThingsForm {

  //public static String deviceURL = "btspp://000666434582:1;authenticate=false;encrypt=false;master=false";
  private Button tempButton;
  private boolean on;

  public RelayForm(ThingsForm previousForm) {
    super(previousForm, "Relay", "Relay");
    this.setLayout(new BorderLayout());
    Image imgButton = null;

    try {
      imgButton = Image.createImage("/lamp.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    tempButton = new Button("", imgButton);
    tempButton.setTextPosition(Label.BOTTOM);
    tempButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        if (on) {
          try {
            sendMessage(RelayForm.this.getThingCommand() + "0");
            //Things.bluetooth(deviceURL).send(RelayForm.this.getThingCommand() + "0");
          } catch (ThingException ex) {
            ThingsMenu.getInstance().updateStatus(ex.getMessage());
          }
        } 
        else {
          try {
            sendMessage(RelayForm.this.getThingCommand() + "1");
            //Things.bluetooth(deviceURL).send(RelayForm.this.getThingCommand() + "1");
          } catch (ThingException ex) {
            ThingsMenu.getInstance().updateStatus(ex.getMessage());
          }
        }
        on = !on;
      }
    });
    this.addComponent(BorderLayout.CENTER, tempButton);
  }
}