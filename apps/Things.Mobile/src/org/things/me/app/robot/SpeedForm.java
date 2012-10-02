package org.things.me.app.robot;


import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.Slider;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author triangulum
 */
public class SpeedForm extends Form {

    private DriveForm previousForm;
    private static int DEFAULT_SPEED = 127;
    private Button saveButton;
    private SpeedSlider speedEntry;

    public SpeedForm(DriveForm previous) {
        super("Speed control");
        setLayout(new BorderLayout());
        this.previousForm = previous;

        speedEntry = new SpeedSlider();

        saveButton = new Button("Save");
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    saveSpeed(speedEntry.getActualSpeed());
                    Dialog d = new Dialog("Speed saved");
                    d.setDialogType(Dialog.TYPE_ERROR);
                    d.addComponent(new Label("Go drive!"));
                    d.setTimeout(3000);
                    d.show();
                } catch (NumberFormatException x) {
                    Dialog d = new Dialog("Wrong Value");
                    d.setDialogType(Dialog.TYPE_ERROR);
                    d.addComponent(new Label("You must enter a integer between"
                            + " 0 and 255"));
                    d.setTimeout(3000);
                    d.show();
                }
            }
        });

        addComponent(BorderLayout.CENTER, speedEntry);
        addComponent(BorderLayout.SOUTH, saveButton);

        Command exitComm = new Command("Back") {

            public void actionPerformed(ActionEvent ev) {
                goBack();
            }
        };
        this.addCommand(exitComm);
    }

    public void goBack() {
        previousForm.showBack();
    }

    public static int loadSpeed() {
        RecordStore db = null;
        int speed = 0;

        try {
            db = RecordStore.openRecordStore("speed", true);
            if (db.getNumRecords() > 0) {
                speed = Integer.parseInt(new String(db.getRecord(1)));
            } else {
                speed = DEFAULT_SPEED;
            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
            Dialog d = new Dialog("open");
            d.setDialogType(Dialog.TYPE_ERROR);
            d.addComponent(new Label(ex.getMessage()));
            d.setTimeout(3000);
            d.show();
        } finally {
            try {
                db.closeRecordStore();
            } catch (RecordStoreException ex) {
                Dialog d = new Dialog("Close");
                d.setDialogType(Dialog.TYPE_ERROR);
                d.addComponent(new Label(ex.getMessage()));
                d.setTimeout(3000);
                d.show();
            }
        }
        return speed;
    }

  public static void saveSpeed(int speed) {
    RecordStore db = null;

    try {
      db = RecordStore.openRecordStore("speed", true);
      String speedString = Integer.toString(speed);
      if (db.getNumRecords() > 0) {
        db.setRecord(1, speedString.getBytes(), 0, speedString.getBytes().length);
      } else {
        db.addRecord(speedString.getBytes(), 0, speedString.getBytes().length);
      }
    } catch (RecordStoreException ex) {
      ex.printStackTrace();
      Dialog d = new Dialog("Op");
      d.setDialogType(Dialog.TYPE_ERROR);
      d.addComponent(new Label(ex.getMessage()));
      d.setTimeout(3000);
      d.show();
    } finally {
      try {
        db.closeRecordStore();
      } catch (RecordStoreException ex) {
        Dialog d = new Dialog("Cl");
        d.setDialogType(Dialog.TYPE_ERROR);
        d.addComponent(new Label(ex.getMessage()));
        d.setTimeout(3000);
        d.show();
      }
    }
  }

    class SpeedSlider extends Slider {

        private final int MIN = 0;
        private final int MAX = 255;
        private final int MINUS = -3;
        private final int PLUS = -4;
        private int actualSpeed;

        public SpeedSlider() {
            super();
            this.setMinValue(MIN);
            this.setMaxValue(MAX);
            this.setEditable(true);
            actualSpeed = loadSpeed();
            System.out.println("Speed is " + actualSpeed);
            this.setProgress(actualSpeed);
        }

        public void keyPressed(int keyCode) {
            switch (keyCode) {
                case MINUS:
                    actualSpeed -= 20;
                    if (actualSpeed < 0) {
                        actualSpeed = 0;
                    }
                    setProgress(actualSpeed);
                    break;
                case PLUS:
                    actualSpeed += 20;
                    if (actualSpeed > 255) {
                        actualSpeed = 255;
                    }
                    this.setProgress(actualSpeed);
                    break;
            }
        }
        
        public int getActualSpeed(){
            return actualSpeed;
        }
    }
}
