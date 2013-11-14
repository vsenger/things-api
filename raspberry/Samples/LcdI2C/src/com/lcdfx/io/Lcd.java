package com.lcdfx.io;

public interface Lcd {

	public static final int BACKLIGHT_OFF = 0x00;
	public static final int BACKLIGHT_RED = 0x01;
	public static final int BACKLIGHT_GREEN = 0x02;
	public static final int BACKLIGHT_BLUE = 0x04;
	public static final int BACKLIGHT_YELLOW =  BACKLIGHT_RED +  BACKLIGHT_GREEN;
	public static final int BACKLIGHT_TEAL =  BACKLIGHT_GREEN +  BACKLIGHT_BLUE;
	public static final int BACKLIGHT_VIOLET =  BACKLIGHT_RED +  BACKLIGHT_BLUE;
	public static final int BACKLIGHT_WHITE =  BACKLIGHT_RED +  BACKLIGHT_GREEN +  BACKLIGHT_BLUE;
	public static final int BACKLIGHT_ON =  BACKLIGHT_RED +  BACKLIGHT_GREEN +  BACKLIGHT_BLUE;

	int getRowCount();
	int getColumnCount();

	void clear();

	void setCursorHome();
	void setCursorPosition(int row, int column);

	void write(String data);
	void write(int row, String data);
	void write(byte data);
	
	void setDisplay(boolean on);
	void setBacklight(int color);
	
	void shutdown();

}
