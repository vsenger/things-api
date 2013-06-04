package org.things.me.app.robot;

import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.GridLayout;
import java.io.IOException;
import org.things.ThingException;
import org.things.me.app.ThingsForm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author triangulum
 */
public class DriveForm extends ThingsForm {

  private int speed;
  private Label upArrow, downArrow, leftArrow, rightArrow;
  private final int UP_KEY = -1;
  private final int DOWN_KEY = -2;
  private final int LEFT_KEY = -3;
  private final int RIGHT_KEY = -4;
  private final int STOP_KEY = -5;

  public DriveForm(ThingsForm previousScreen) {
    super(previousScreen, "Drive", "RobotDrive");
    setLayout(new GridLayout(3, 3));

    try {
      upArrow = new Label(Image.createImage("/up.png"));
    } catch (IOException ex) {
      System.out.println("Image not found");
    }

    addComponent(new Label(""));
    addComponent(upArrow);
    addComponent(new Label(""));

    try {
      leftArrow = new Label(Image.createImage("/left.png"));
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    addComponent(leftArrow);
    addComponent(new Label(""));

    try {
      rightArrow = new Label(Image.createImage("/right.png"));
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    addComponent(rightArrow);
    addComponent(new Label(""));

    try {
      downArrow = new Label(Image.createImage("/down.png"));
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    addComponent(downArrow);
    addComponent(new Label(""));

    Command exitComm = new Command("Speed") {
      public void actionPerformed(ActionEvent ev) {
        SpeedForm speedControl = new SpeedForm(getMyself());
        speedControl.show();
      }
    };
    this.addCommand(exitComm);
  }

  public DriveForm getMyself() {
    return this;
  }

  public void keyPressed(int keyCode) {
    int speed = SpeedForm.loadSpeed();
    switch (keyCode) {
      case UP_KEY:
        setThingCommand("frente?");
        try {
          sendCommand(getThingCommand() + speed);
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        resetLabels();
        try {
          upArrow.setIcon(Image.createImage("/up_sel.png"));
        } catch (IOException ex) {
          System.out.println("Image not found");
        }
        break;
      case DOWN_KEY:
        setThingCommand("re?");
        try {
          sendCommand(getThingCommand() + speed);
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        resetLabels();
        try {
          downArrow.setIcon(Image.createImage("/down_sel.png"));
        } catch (IOException ex) {
          System.out.println("Image not found");
        }
        break;
      case LEFT_KEY:
        setThingCommand("ga?");
        try {
          sendCommand(getThingCommand() + speed);
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        resetLabels();
        try {
          leftArrow.setIcon(Image.createImage("/left_sel.png"));
        } catch (IOException ex) {
          System.out.println("Image not found");
        }
        break;
      case RIGHT_KEY:
        setThingCommand("gh?");
        try {
          sendCommand(getThingCommand() + speed);
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        resetLabels();
        try {
          rightArrow.setIcon(Image.createImage("/right_sel.png"));
        } catch (IOException ex) {
          System.out.println("Image not found");
        }
        break;
      case STOP_KEY:
        setThingCommand("parar");
        try {
          sendCommand(getThingCommand());
        } catch (ThingException ex) {
          ex.printStackTrace();
        }
        resetLabels();
        break;
    }
  }

  public void resetLabels() {
    //UP
    try {
      upArrow.setIcon(Image.createImage("/up.png"));
      downArrow.setIcon(Image.createImage("/down.png"));
      leftArrow.setIcon(Image.createImage("/left.png"));
      rightArrow.setIcon(Image.createImage("/right.png"));
    } catch (IOException ex) {
      System.out.println("Image not found");
    }
    repaint();
  }
}
