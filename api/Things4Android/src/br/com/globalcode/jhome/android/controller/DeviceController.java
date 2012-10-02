package br.com.globalcode.jhome.android.controller;

import java.util.ArrayList;
import java.util.StringTokenizer;

import br.com.globalcode.jhome.android.bean.Device;

public class DeviceController {

	private DeviceController() {		
	}
	
	public static ArrayList<Device> parseDeviceDiscovery(String result) {
		System.out.println(result);
		ArrayList<Device> devices = new ArrayList<Device>();
		StringTokenizer tokenizer = new StringTokenizer(result, "|");
		
		String nomeDevice = tokenizer.nextToken();
		int totalDevices = Integer.parseInt(tokenizer.nextToken());
		while (tokenizer.hasMoreTokens()) {
			Device d = new Device();
			d.setDeviceName(tokenizer.nextToken());
			d.setDeviceType(tokenizer.nextToken());
			try {
			d.setDevicePort(Integer.parseInt(tokenizer.nextToken()));
			
			d.setValue(Integer.parseInt(tokenizer.nextToken()));
			}
			catch(Exception e) {}
			devices.add(d);
		}
		
		return devices;
	}
}