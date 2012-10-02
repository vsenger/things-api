package br.com.globalcode.jhome.android.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.globalcode.jhome.android.bean.Device;
import br.com.globalcode.jhome.android.sqlite.JHomeSQLiteOpenHelper;

public class DeviceModel {

	private static final String DEVICE_DB_TABLE = "device";
	
	private static final String KEY_ID = "_id";
	private static final String KEY_DEVICE_NAME = "deviceName";
	private static final String KEY_DEVICE_ADDRESS = "deviceAddress";
	private static final String KEY_DEVICE_TYPE = "deviceType";
	private static final String KEY_DEVICE_PORT = "devicePort";
	private static final String KEY_VALUE = "value";
	
	private DeviceModel() {		
	}
	
	public static long insertDevice(Context context, String deviceName, String deviceAddress,
			String deviceType, int devicePort, int value) {
		JHomeSQLiteOpenHelper helper = new JHomeSQLiteOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_DEVICE_NAME, deviceName);
		cv.put(KEY_DEVICE_ADDRESS, deviceAddress);
		cv.put(KEY_DEVICE_TYPE, deviceType);
		cv.put(KEY_DEVICE_PORT, devicePort);
		cv.put(KEY_VALUE, value);
		
		long rows = db.insert(DEVICE_DB_TABLE, null, cv);
		db.close();
		
		return rows;
	}
	
	public static int deleteDevice(Context context, int deviceId) {
		JHomeSQLiteOpenHelper helper = new JHomeSQLiteOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		int rows = db.delete(DEVICE_DB_TABLE, KEY_ID + "=" + deviceId, null);
		db.close();
		
		return rows;
	}
	
	public static int clearDevices(Context context) {
		JHomeSQLiteOpenHelper helper = new JHomeSQLiteOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		int rows = db.delete(DEVICE_DB_TABLE, null, null);
		db.close();
		
		return rows;
	}
	
	public static ArrayList<Device> getDeviceList(Context context) {
		JHomeSQLiteOpenHelper helper = new JHomeSQLiteOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		ArrayList<Device> devices = new ArrayList<Device>();
		
		Cursor c = db.query(DEVICE_DB_TABLE, new String[] { KEY_ID, KEY_DEVICE_NAME, 
				KEY_DEVICE_ADDRESS, KEY_DEVICE_TYPE, KEY_DEVICE_PORT, KEY_VALUE }, 
				null, null, null, null, null);
		c.moveToFirst();
		
		while (!c.isAfterLast()) {
			Device d = new Device();
			d.setId(c.getInt(0));
			d.setDeviceName(c.getString(1));
			d.setDeviceAddress(c.getString(2));
			d.setDeviceType(c.getString(3));
			d.setDevicePort(c.getInt(4));
			d.setValue(c.getInt(5));
			Log.d("jHome", "Device retrived: "+d.getDeviceName());
			devices.add(d);
			c.moveToNext();
		}
		c.close();
		db.close();
		
		return devices;
	}
	
	public static int updateDevice(Context context, int deviceId, String deviceName, String deviceAddress,
			String deviceType, int devicePort, int value) {
		JHomeSQLiteOpenHelper helper = new JHomeSQLiteOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_DEVICE_NAME, deviceName);
		cv.put(KEY_DEVICE_ADDRESS, deviceAddress);
		cv.put(KEY_DEVICE_TYPE, deviceType);
		cv.put(KEY_DEVICE_PORT, devicePort);
		cv.put(KEY_VALUE, value);
		
		int rows = db.update(DEVICE_DB_TABLE, cv, KEY_ID +"="+deviceId, null);
		db.close();
		
		return rows;
	}
}