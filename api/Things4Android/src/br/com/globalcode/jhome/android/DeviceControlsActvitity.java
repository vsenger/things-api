package br.com.globalcode.jhome.android;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;
import br.com.globalcode.jhome.android.bean.Device;
import br.com.globalcode.jhome.android.bean.DeviceType;
import br.com.globalcode.jhome.android.controller.DeviceController;
import br.com.globalcode.jhome.android.model.DeviceModel;
import br.com.globalcode.jhome.android.ws.CentralWS;
import br.com.globalcode.jhome.android.ws.DeviceWS;

public class DeviceControlsActvitity extends Activity implements android.view.View.OnClickListener, OnSeekBarChangeListener {

	public static final String DEFAULT_IP_SERVER = "192.168.1.3";

	public static final String PREF_SERVER_ADDRESS = "server";
	public static final String PREF_DEVICE_NAME = "device_name";

	private static final int DIALOG_SEARCH_DEVICES = 1;
	private static final int DIALOG_CHANGE_SERVER_IP = 2;
	private static final int DIALOG_CONNECTION_ERROR = 3;

	private boolean firstTime;
	private ProgressDialog progressDialog;
	private String serverAddress;
	private String deviceName;
	private ArrayList<Device> devices;
	private EditText ipEditText;
	private Hashtable<View, Device> deviceControls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_controls_layout);

		findViewById(R.id.devices_settingsButton).setOnClickListener(new View.OnClickListener() {			
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(DIALOG_CHANGE_SERVER_IP);
			}
		});
		
		findViewById(R.id.devices_refreshButton).setOnClickListener(new View.OnClickListener() {			
			
			public void onClick(View v) {
				searchDevices();
			}
		});
		
		loadPreferences();
//		refreshServerTextView();
		firstTime = true;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus && this.devices == null && firstTime) {
			refreshServerTextView();
			firstTime = false;
			loadDeviceList();
			if (this.devices.size() == 0) {
				showDialog(DIALOG_SEARCH_DEVICES);
			} else {
				addDeviceControls();
			}
		}
	}

	private void loadPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		this.serverAddress = prefs.getString(PREF_SERVER_ADDRESS,
				DEFAULT_IP_SERVER);
		this.deviceName = prefs.getString(PREF_DEVICE_NAME, "");
	}
	
	private void saveNewIpAddress() {
		this.serverAddress = this.ipEditText.getText().toString();
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
		editor.putString(PREF_SERVER_ADDRESS, this.serverAddress);
		editor.commit();
		refreshServerTextView();
		this.removeDeviceControls();
		DeviceModel.clearDevices(this);
		removeDialog(DIALOG_SEARCH_DEVICES);
		showDialog(DIALOG_SEARCH_DEVICES);
	}
	
	private void refreshServerTextView() {
		((TextView) findViewById(R.id.devices_serverIpTextView))
				.setText(getString(R.string.devices_server_ip_label)
						+ " " + this.serverAddress);
	}

	private void loadDeviceList() {
		ProgressDialog progressDialog = ProgressDialog.show(this, null,
				getString(R.string.devices_loading_devices_db));
		this.devices = DeviceModel.getDeviceList(this);
		progressDialog.cancel();
	}

	private void searchDevices() {
		new AsyncTask<String, Integer, String>() {

			protected void onPreExecute() {
				progressDialog = ProgressDialog.show(DeviceControlsActvitity.this, null,
						getString(R.string.devices_searching_devices));
			}

			@Override
			protected String doInBackground(String... params) {
				String result = CentralWS.discovery(params[0],
						Integer.parseInt(params[1]));
				return result;
			}
			
			protected void onPostExecute(String result) {
				progressDialog.dismiss();
				parseDeviceDiscovery(result);
			}
		}.execute(this.serverAddress, Integer.toString(8080));
	}

	private void parseDeviceDiscovery(String result) {
		if (result == null) {
			showDialog(DIALOG_CONNECTION_ERROR);
			return;
		}
		this.devices = DeviceController.parseDeviceDiscovery(result);
		DeviceModel.clearDevices(this);
		
		for (Device d : this.devices) {
			long rowid = DeviceModel.insertDevice(this, d.getDeviceName(),
					d.getDeviceAddress(), d.getDeviceType(), d.getDevicePort(),
					d.getValue());
			d.setId(rowid);
		}
		
		addDeviceControls();
	}
	
	private void addDeviceControls() {
		removeDeviceControls();
		this.deviceControls = new Hashtable<View, Device>();
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		for (Device d : this.devices) {
			View v = null;
			if (d.getDeviceType().equals(DeviceType.DIGITAL) || d.getDeviceType().equals(DeviceType.RELAY) ||
					d.getDeviceType().equals(DeviceType.LED)) {
				v = inflater.inflate(R.layout.device_digital, null);
				((TextView)v.findViewById(R.id.digital_deviceNameTextView)).setText(d.getDeviceName());
				
				ToggleButton t = (ToggleButton) v.findViewById(R.id.digital_deviceToggleButton);
				if (d.getValue() == 1) {
					t.setChecked(true);
				}
				t.setOnClickListener(this);
				this.deviceControls.put(t, d);
			} else if (d.getDeviceType().equals(DeviceType.PWM) || d.getDeviceType().equals(DeviceType.LEDPWM) ||
					d.getDeviceType().equals(DeviceType.RGB)) {
				v = inflater.inflate(R.layout.device_pwm, null);
				((TextView)v.findViewById(R.id.pwm_deviceName)).setText(d.getDeviceName());
				
				SeekBar s = (SeekBar)v.findViewById(R.id.pwm_digitalSeekBar);
				s.setProgress(d.getValue());
				s.setOnSeekBarChangeListener(this);
				this.deviceControls.put(s, d);
			} else if (d.getDeviceType().equals(DeviceType.ANALOG) || d.getDeviceType().equals(DeviceType.TEMP) ||
					d.getDeviceType().equals(DeviceType.LIGHT) || d.getDeviceType().equals(DeviceType.DISTANCE_EZ1) ||
					d.getDeviceType().equals(DeviceType.POTENCIOMETER)) {
				v = inflater.inflate(R.layout.device_analog, null);
				((TextView)v.findViewById(R.id.analog_deviceName)).setText(d.getDeviceName());
				((TextView)v.findViewById(R.id.analog_deviceValue)).setText(Integer.toString(d.getValue()));
				v.setOnClickListener(this);
				this.deviceControls.put(v, d);
			}
			
			if (v != null) {
				((LinearLayout)findViewById(R.id.devices_controlsLayout)).addView(v);
			}
		}		
	}
	
	private void removeDeviceControls() {
		((LinearLayout)findViewById(R.id.devices_controlsLayout)).removeAllViews();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if (id == DIALOG_SEARCH_DEVICES) {
			builder.setTitle(
					getString(R.string.devices_no_devices_found_dialog))
					.setMessage(
							getString(R.string.devices_automatic_scan_dialog) + " "
									+ this.serverAddress + " ?")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setPositiveButton("Scan",
							new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog,
										int which) {
									searchDevices();
								}
							})
					.setNegativeButton("Cancel", null)
					.setNeutralButton("Change Server",
							new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									showDialog(DIALOG_CHANGE_SERVER_IP);

								}
							});

		} else if (id == DIALOG_CHANGE_SERVER_IP) {
			if (this.ipEditText == null) {
				ipEditText = new EditText(this);
			}
			ipEditText.setText(this.serverAddress);
			
			builder.setTitle(getString(R.string.devices_change_server_ip_title))
			.setView(ipEditText)
			.setPositiveButton("Save", new OnClickListener() {				
				public void onClick(DialogInterface dialog, int which) {
					if (ipEditText.getText().toString() != null &&
							ipEditText.getText().toString().equals(serverAddress)) {
						dialog.dismiss();
					} else if (ipEditText.getText().length() > 0) {
						saveNewIpAddress();
					} else {
						dialog.dismiss();
						showDialog(DIALOG_CHANGE_SERVER_IP);
					}
				}
			})
			.setNegativeButton("Cancel", null);
		} else if (id == DIALOG_CONNECTION_ERROR) {
			builder.setTitle(R.string.error)
			.setMessage(getString(R.string.connection_error))
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setNeutralButton("OK", null);
		}
		return builder.create();
	}

	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//		Device d = this.deviceControls.get(seekBar);
//		sendCommandToDevice(d.getDeviceName(), seekBar.getProgress());
	}

	
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	
	public void onStopTrackingTouch(SeekBar seekBar) {
		Device d = this.deviceControls.get(seekBar);
		sendCommandToDevice(d.getDeviceName(), seekBar.getProgress());
	}

	
	public void onClick(View view) {
		Device d = this.deviceControls.get(view);
		if (d.getDeviceType().equals(DeviceType.ANALOG) || d.getDeviceType().equals(DeviceType.TEMP) ||
				d.getDeviceType().equals(DeviceType.LIGHT) || d.getDeviceType().equals(DeviceType.DISTANCE_EZ1) ||
				d.getDeviceType().equals(DeviceType.POTENCIOMETER)) {
			refreshDeviceValue(view);
			
		} else {
			ToggleButton t = (ToggleButton) view;
			int value = t.isChecked() ? 1 : 0;
			sendCommandToDevice(d.getDeviceName(), value);
		}		
	}

	private void refreshDeviceValue(View view) {
		new AsyncTask<View, Integer, String>() {

			private View view;
			
			@Override
			protected String doInBackground(View... params) {
				this.view = params[0];
				Device d = deviceControls.get(params[0]);
				String result = DeviceWS.read(serverAddress, 8080, d.getDeviceName());
				return result;
			}
			
			protected void onPostExecute(String result) {
				if (result != null) {
					Device d = deviceControls.get(view);
					if ( d.getDeviceType().equalsIgnoreCase(DeviceType.TEMP)) {
						result = Float.toString((Float.parseFloat(result) * (float)0.00488));
					}
					
					((TextView)view.findViewById(R.id.analog_deviceValue)).setText(result);
				}
			}
			
		}.execute(view);		
	}

	private void sendCommandToDevice(String deviceName, int value) {
		new AsyncTask<String, Integer, String>() {

			@Override
			protected String doInBackground(String... params) {
				String result = DeviceWS.send(serverAddress, 8080, params[0], params[1]);
				return result;
			}
			
		}.execute(deviceName, Integer.toString(value));
		
		Log.d("jHome", "Device: " + deviceName + " - Command: "+value);
	}
}