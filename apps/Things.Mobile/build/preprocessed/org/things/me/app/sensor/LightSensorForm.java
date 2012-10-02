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

public class LightSensorForm extends ThingsForm {

  private Button tempButton;
  private boolean on;

  public LightSensorForm(ThingsForm previousForm) {
    super(previousForm, "Light Sensor", "Light");
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
          rcvd = sendAndReceiveMessage("light");
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        tempButton.setText(rcvd + inte(rcvd));
      }

      public String inte(String ms) {
        int v = Integer.parseInt(ms);
        
        return v < 400 ? "clear"
                : v > 400 && v < 800 ? "medium"
                : "dark";
      }
    });
    this.addComponent(BorderLayout.CENTER, tempButton);


  }
}