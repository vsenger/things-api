package org.things.me.app.automation;

import org.things.me.app.ThingsForm;
import com.sun.lwuit.Slider;
import com.sun.lwuit.layouts.GridLayout;
import org.things.ThingException;

public class RGBForm extends ThingsForm {

  private RGBSlider redSlider;
  private RGBSlider greenSlider;
  private RGBSlider blueSlider;

  public RGBForm(ThingsForm previousForm) {
    super(previousForm, "RGB Strip", "RGB");
    setThingCommand("");
    this.setLayout(new GridLayout(3, 1));
    redSlider = new RGBSlider("red");
    greenSlider = new RGBSlider("green");
    blueSlider = new RGBSlider("blue");
    this.addComponent(redSlider);
    this.addComponent(greenSlider);
    this.addComponent(blueSlider);
  }

  class RGBSlider extends Slider {

    private final int MIN = 0;
    private final int MAX = 255;
    private final int MINUS = -3;
    private final int PLUS = -4;
    private final int DEFAULT = 55;
    private int actualValue;
    private String color;

    public RGBSlider(String color) {
      super();
      this.setMinValue(MIN);
      this.setMaxValue(MAX);
      this.setEditable(true);
      actualValue = DEFAULT;
      this.setProgress(actualValue);
      this.color = color;
    }

    public void keyPressed(int keyCode) {
      switch (keyCode) {
        case MINUS:
          actualValue -= 10;
          if (actualValue < MIN) {
            actualValue = MIN;
          }
          setProgress(actualValue);
          try {
            sendMessage(color + "?" + actualValue);
          } catch (ThingException ex) {
            ex.printStackTrace();
          }
          break;
        case PLUS:
          actualValue += 10;
          if (actualValue > MAX) {
            actualValue = MAX;
          }
          this.setProgress(actualValue);
          try {
            sendMessage(color + "?" + actualValue);
          } catch (ThingException ex) {
            ex.printStackTrace();
          }
          break;
      }
    }

    public int getActualValue() {
      return actualValue;
    }
  }
}
