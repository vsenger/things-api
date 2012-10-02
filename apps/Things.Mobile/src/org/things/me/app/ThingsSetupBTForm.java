package org.things.me.app;

import com.sun.lwuit.Button;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import org.things.bluetooth.BluetoothDiscovery;
import org.things.bluetooth.BluetoothDiscoveryListener;

public class ThingsSetupBTForm extends ThingsForm implements BluetoothDiscoveryListener {

  private Button button;
  private TextArea text;
  private com.sun.lwuit.List devices;

  public ThingsSetupBTForm(ThingsForm previousForm) {
    super(previousForm, "BT Discovery", "BT Discovery");
    this.setLayout(new BorderLayout());
    text = new TextArea();
    devices = new List(BluetoothDiscovery.getInstance().getDevices());
    button = new Button("Discovery");
    button.setTextPosition(Label.BOTTOM);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        BluetoothDiscovery.getInstance().start(ThingsSetupBTForm.this);
      }
    });
    this.addComponent(BorderLayout.NORTH, button);
    this.addComponent(BorderLayout.CENTER, text);
    //this.addComponent(BorderLayout.SOUTH, devices);

  }

  public void notify(String msg) {
    this.text.setText(text.getText() + "\n" + msg);
    this.repaint();
  }
}