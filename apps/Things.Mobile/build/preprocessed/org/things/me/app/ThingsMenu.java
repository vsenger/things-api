package org.things.me.app;

import org.things.me.app.automation.RelayForm;
import org.things.me.app.automation.RGBForm;
import org.things.me.app.sensor.LightSensorForm;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import java.io.IOException;
import org.things.me.app.robot.DriveForm;
import org.things.me.app.robot.ServoForm;
import org.things.me.app.sensor.TemperatureSensorForm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author triangulum
 */
public class ThingsMenu extends ThingsForm {

  private Button relay1Button;
  private Button relay2Button;
  private Button RGBButton;
  private Button sensorButton1;
  private Button sensorButton2;
  private Button bluetoothButton;
  private Button robotButton;
  private Button servoButton;
  private ThingsMidlet mainMidlet;
  private static ThingsMenu instance;

  public ThingsMenu(ThingsMidlet mainMidlet) {
    super(null, "Things for Java ME", "Menu");
    instance = this;
    this.mainMidlet = mainMidlet;
    setLayout(new GridLayout(5, 3));

    //Relay1 button
    Image imgBotaoS = null;
    try {
      imgBotaoS = Image.createImage("/switch.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    relay1Button = new Button("Relay 1", imgBotaoS);
    relay1Button.setTextPosition(Label.BOTTOM);
    relay1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        RelayForm formRelay = new RelayForm(getMyself());
        formRelay.setThingCommand("relay1?");
        formRelay.show();
      }
    });
    addComponent(relay1Button);

    //Relay2 button
    Image imgBotao2 = null;
    try {
      imgBotao2 = Image.createImage("/coffee-machine.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    relay2Button = new Button("Relay 2", imgBotao2);
    relay2Button.setTextPosition(Label.BOTTOM);
    relay2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        RelayForm formRelay = new RelayForm(getMyself());
        formRelay.setThingCommand("relay2?");
        formRelay.show();
      }
    });
    addComponent(relay2Button);

    //RGB button
    Image imgBotao3 = null;
    try {
      imgBotao3 = Image.createImage("/colours_rgb.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    RGBButton = new Button("RGB", imgBotao3);
    RGBButton.setTextPosition(Label.BOTTOM);
    RGBButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        RGBForm formRGB = new RGBForm(getMyself());
        formRGB.show();
      }
    });
    addComponent(RGBButton);


    Image imgBotaoL = null;
    try {
      imgBotaoL = Image.createImage("/lamp.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }

    sensorButton1 = new Button("Light Sensor", imgBotaoL);
    sensorButton1.setTextPosition(Label.BOTTOM);
    sensorButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        LightSensorForm formSensor = new LightSensorForm(getMyself());
        formSensor.show();
      }
    });
    addComponent(sensorButton1);

    
    Image imgBotaoTe = null;
    try {
      imgBotaoTe = Image.createImage("/temp.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }

    sensorButton2 = new Button("Temperature", imgBotaoTe);
    sensorButton2.setTextPosition(Label.BOTTOM);
    sensorButton2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        TemperatureSensorForm formSensor = new TemperatureSensorForm(getMyself());
        formSensor.show();
      }
    });
    addComponent(sensorButton2);
    //Robot button
    Image imgBotaoRobot = null;
    try {
      imgBotaoRobot = Image.createImage("/bot.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    robotButton = new Button("Drive", imgBotaoRobot);
    robotButton.setTextPosition(Label.BOTTOM);
    robotButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        DriveForm formDrive = new DriveForm(getMyself());
        formDrive.show();
      }
    });
    addComponent(robotButton);

    //Servo button
    Image imgBotaoServo = null;
    try {
      imgBotaoServo = Image.createImage("/servo.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    servoButton = new Button("Servo", imgBotaoServo);
    servoButton.setTextPosition(Label.BOTTOM);
    servoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        ServoForm formServo = new ServoForm(getMyself());
        formServo.show();
      }
    });
    addComponent(servoButton);

    
    Image imgBotao4 = null;
    try {
      imgBotao4 = Image.createImage("/bluetooth.png");
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    bluetoothButton = new Button("Bluetooth", imgBotao4);
    bluetoothButton.setTextPosition(Label.BOTTOM);
    bluetoothButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        ThingsSetupBTForm form = new ThingsSetupBTForm(getMyself());
        form.show();
      }
    });
    addComponent(bluetoothButton);

    Command exitComm = new Command("Exit") {
      public void actionPerformed(ActionEvent ev) {
        destroy();
      }
    };
    addCommand(exitComm);

  }

  public ThingsMenu getMyself() {
    return this;
  }

  public void destroy() {
    mainMidlet.destroyApp(true);
  }

  public static ThingsMenu getInstance() {
    return instance;
  }

  public void updateStatus(String status) {
    setTitle(status);
  }
}
