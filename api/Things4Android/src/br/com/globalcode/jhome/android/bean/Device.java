package br.com.globalcode.jhome.android.bean;

public class Device {

	private long id;
	private String deviceName;
	private String deviceAddress;
	private String deviceType;
	private int devicePort;
	private int value;
	
	public Device() {
	}

	public Device(int id, String deviceName, String deviceAddress,
			String deviceType, int devicePort, int value) {
		this.id = id;
		this.deviceName = deviceName;
		this.deviceAddress = deviceAddress;
		this.deviceType = deviceType;
		this.devicePort = devicePort;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long rowid) {
		this.id = rowid;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(int devicePort) {
		this.devicePort = devicePort;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}