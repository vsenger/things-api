package com.netomarin.arduhome.bt;

import com.netomarin.arduhome.core.ArduhomeMIDlet;
import com.netomarin.arduhome.core.FlowManager;
import com.netomarin.arduhome.view.ConnectionForm;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Neto Marin
 */
public class BluetoothClientServer implements DiscoveryListener {
    UUID RFCOMM_UUID = new UUID(0x1101);
    private StreamConnection streamConn = null;
    private LocalDevice localDevice = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private OutputStream os = null;
    private OutputStreamWriter osw = null;
    private DiscoveryAgent discoveryAgent = null;
    private RemoteDevice remoteDevice;
    private Vector services;
    private static BluetoothClientServer instance;

    private boolean connected = false;

    private BluetoothClientServer() {
    }

    public static BluetoothClientServer getInstance() {
        if (instance == null) {
            instance = new BluetoothClientServer();
        }

        return instance;
    }

    public void InitClient() {
        SearchAvailDevices();

    }

    public void SearchAvailDevices() {
        try {
            //First get the local device and obtain the discovery agent.
            this.remoteDevice = null;
            localDevice = LocalDevice.getLocalDevice();
            discoveryAgent = localDevice.getDiscoveryAgent();
            ConnectionForm.getInstance().updateGaugeStatus("Starting search...");
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (BluetoothStateException ex) {
            System.out.println("Problem in searching the blue tooth devices");
            ConnectionForm.getInstance().updateGaugeStatus("Error searching devics!!");
            ConnectionForm.getInstance().append(ex.getMessage());
        }
    }

    public void sendMessage(byte[] messageToSend) {
        try {
            //osw.write(messageToSend);
            //osw.flush();
            os.write(messageToSend);
            os.flush();
        } catch (IOException ex) {
        }
    }

    public String RecieveMessages() {
        byte[] data = null;

        try {
            int length = is.read();
            data = new byte[length];
            length = 0;

            while (length != data.length) {
                int ch = is.read(data, length, data.length - length);

                if (ch == -1) {
                    throw new IOException("Can't read data");
                }
                length += ch;
            }
        } catch (IOException e) {
            System.err.println(e);
//            MainForm.updateConnectionStatus("Error receiving messages!");
        }

        return new String(data);
    }

    /*********************************************************************************************
     * below are the pure virtual  methods of discoverlistern
     *
     *
     *******************************************************************************************/
    //Called when device is found during inquiry
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        try {
            // Get Device Info
          boolean found = btDevice.getBluetoothAddress().equals(ArduhomeMIDlet.getCentralMacAddress());
            ConnectionForm.getInstance().updateGaugeStatus("Device found: " + btDevice.getBluetoothAddress()
                    + (found ? " - Central FOUND!" :  " - not central device!"));

            if (found) {
                ConnectionForm.getInstance().updateGaugeStatus("Central FOUND!");
                this.remoteDevice = btDevice;
                return;
            }
            
        } catch (Exception e) {
            System.out.println("Device Discovered Error: " + e);
            ConnectionForm.getInstance().updateGaugeStatus("Error on device discovered");
            ConnectionForm.getInstance().append(e.getMessage());
        }
    }

    public void inquiryCompleted(int discType) {
        if ( remoteDevice != null ) {
            services = new Vector();
            UUID[] query = new UUID[1];
	    query[0] = RFCOMM_UUID;
            try {
                discoveryAgent.searchServices(null, query, remoteDevice, this);
            } catch (BluetoothStateException ex) {
                ex.printStackTrace();
            }
        } else {
            ConnectionForm.getInstance().updateGaugeStatus("Central not found!");
        }
    }

    

    //called when service found during service search
    public void servicesDiscovered(int transID, ServiceRecord[] records) {
        ConnectionForm.getInstance().updateGaugeStatus("Looking for RFCOMM channel!");
        for(int i = 0; i < records.length; i++)
	    this.services.addElement(records[i]);
        ConnectionForm.getInstance().updateGaugeStatus("Service search completed!");
    }

    //called when service search gets complete
    public void serviceSearchCompleted(int transID, int respCode) {
        ConnectionForm.getInstance().updateGaugeStatus("callback serviceSearchCompleted");
        if ( remoteDevice != null) {
            try {
                ConnectionForm.getInstance().updateGaugeStatus("Starting connection...");
                //lets the communication start by setting the url and send client reponse
                streamConn = (StreamConnection)Connector.open(((ServiceRecord)(services.elementAt(0))).getConnectionURL(0, false));
                ConnectionForm.getInstance().updateGaugeStatus("Opening communication...");
                os = streamConn.openOutputStream();
                osw = new OutputStreamWriter(os, "US-ASCII");
                is = streamConn.openInputStream();
                isr = new InputStreamReader(is, "US-ASCII");

                this.connected = true;
                
                ConnectionForm.getInstance().updateGaugeStatus("Connection Stablished!");
                FlowManager.getInstance().showMainScreen();
            } catch (IOException ex) {
                ConnectionForm.getInstance().updateGaugeStatus("Connection error!!");
                ConnectionForm.getInstance().append(ex.getMessage());
            }

        } else {
            ConnectionForm.getInstance().updateGaugeStatus("Central not found...");
        }
    }

    public void CloseAll() {
        try {
            if (os != null) {
                os.close();
            }

            if (is != null) {
                is.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}