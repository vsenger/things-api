package br.com.globalcode.jhome.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JHomeSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final String JHOME_DB_NAME = "jhomeDB";
	public static final int JHOME_DB_VERSION = 1;

	public JHomeSQLiteOpenHelper(Context context) {
		super(context, JHOME_DB_NAME, null, JHOME_DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE device " 
				+ "(_id integer PRIMARY KEY AUTOINCREMENT, "
				+ "deviceName text, "
				+ "deviceAddress text, "
				+ "deviceType text, "
				+ "devicePort integer, "
				+ "value integer);";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}