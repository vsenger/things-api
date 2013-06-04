package org.things.me.app.robot;

import com.sun.lwuit.Slider;
import com.sun.lwuit.layouts.BorderLayout;
import org.things.ThingException;
import org.things.me.app.ThingsForm;

public class ServoForm extends ThingsForm {

  private ServoSlider servoSlider;

  public ServoForm(ThingsForm previousForm) {
    super(previousForm, "Servo motor", "Servo");
    setThingCommand("servo?");
    this.setLayout(new BorderLayout());
    servoSlider = new ServoSlider();
    this.addComponent(BorderLayout.CENTER, servoSlider);
  }

  class ServoSlider extends Slider {

    private final int MIN = 0;
    private final int MAX = 180;
    private final int MINUS = -3;
    private final int PLUS = -4;
    private final int DEFAULT = 90;
    private int actualAngle;

    public ServoSlider() {
      super();
      this.setMinValue(MIN);
      this.setMaxValue(MAX);
      this.setEditable(true);
      actualAngle = DEFAULT;
      this.setProgress(actualAngle);
    }

    public void keyPressed(int keyCode) {
      switch (keyCode) {
        case MINUS:
          actualAngle -= 10;
          if (actualAngle < 0) {
            actualAngle = 0;
          }
          setProgress(actualAngle);
          try {
            sendCommand("" + actualAngle);
          } catch (ThingException ex) {
            ex.printStackTrace();
          }
          break;
        case PLUS:
          actualAngle += 10;
          if (actualAngle > 179) {
            actualAngle = 179;
          }
          this.setProgress(actualAngle);
          try {
            sendCommand("" + actualAngle);
          } catch (ThingException ex) {
            ex.printStackTrace();
          }
          break;
      }
    }

    public int getActualSpeed() {
      return actualAngle;
    }
  }
}
