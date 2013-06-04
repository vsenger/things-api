package org.things.me.app.sensor;

import com.sun.lwuit.Button;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import java.io.IOException;
import org.things.ThingException;
import org.things.me.app.ThingsForm;


public class TemperatureSensorForm extends ThingsForm {

  private Button tempButton;
  private boolean on;

  public TemperatureSensorForm(ThingsForm previousForm) {
    super(previousForm, "Temperature Sensor", "Temperature");
    this.setLayout(new BorderLayout());
    Image imgButton = null;
    try {
      imgButton = Image.createImage("/temp.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    tempButton = new Button("1000", imgButton);
    tempButton.setTextPosition(Label.BOTTOM);
    tempButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        String rcvd="";
        try {
          rcvd = sendReceiveCommand("temp");
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        //rcvd = rcvd.substring(rcvd.length()-5, rcvd.length()-1);
        float f = Float.parseFloat(rcvd);
        double t = f * 0.00488;
        t *= 100;
        tempButton.setText("" + t);

      }
    });
    this.addComponent(BorderLayout.CENTER, tempButton);


  }
}