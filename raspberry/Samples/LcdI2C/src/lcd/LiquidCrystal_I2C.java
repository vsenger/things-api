/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcd;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.GpioProviderBase;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author denm
 */
public class LiquidCrystal_I2C extends GpioProviderBase implements GpioProvider
{
  public static final String NAME = "com.pi4j.gpio.extension.lcd.LCDI2CGpioProvider";
  
  // Commands
  private static final int LCD_CLEARDISPLAY = 0x01;
  private static final int LCD_RETURNHOME = 0x02;
  private static final int LCD_ENTRYMODESET = 0x04;
  private static final int LCD_DISPLAYCONTROL = 0x08;
  private static final int LCD_CURSORSHIFT = 0x10;
  private static final int LCD_FUNCTIONSET = 0x20;
  private static final int LCD_SETCGRAMADDR = 0x40;
  private static final int LCD_SETDDRAMADDR = 0x80;
  // Flags for display entry mode
  private static final int LCD_ENTRYRIGHT = 0x00;
  private static final int LCD_ENTRYLEFT = 0x02;
  private static final int LCD_ENTRYSHIFTINCREMENT = 0x01;
  private static final int LCD_ENTRYSHIFTDECREMENT = 0x00;
  // Flags for display on/off control
  private static final int LCD_DISPLAYON = 0x04;
  private static final int LCD_DISPLAYOFF = 0x00;
  private static final int LCD_CURSORON = 0x02;
  private static final int LCD_CURSOROFF = 0x00;
  private static final int LCD_BLINKON = 0x01;
  private static final int LCD_BLINKOFF = 0x00;
  // Flags for display/cursor shift
  private static final int LCD_DISPLAYMOVE = 0x08;
  private static final int LCD_CURSORMOVE = 0x00;
  private static final int LCD_MOVERIGHT = 0x04;
  private static final int LCD_MOVELEFT = 0x00;
  // Flags for function set
  private static final int LCD_8BITMODE = 0x10;
  private static final int LCD_4BITMODE = 0x00;
  private static final int LCD_2LINE = 0x08;
  private static final int LCD_1LINE = 0x00;
  private static final int LCD_5x10DOTS = 0x04;
  private static final int LCD_5x8DOTS = 0x00;
  // Flags for backlight control
  private static final int LCD_BACKLIGHT = 0x08;
  private static final int LCD_NOBACKLIGHT = 0x00;
  
  private static final int En = 0x04;  // Enable bit
  private static final int Rw = 0x02;  // Read/Write bit
  private static final int Rs = 0x01;  // Register select bit
  
  private int nbColumn;
  private int nbRow;
  private int dotSize;
  private int backlightVal;
  private int displayControl;
  private int numLines;
  private int displayMode;
  private int displayFunction;
  
  private I2CBus bus = null;
  private I2CDevice device = null;
  
  // ---------------------------------------------------------------------------
  /**
   * Constructor 
   *
   * @param  busNumber I2C bus number, this is normally the first one : I2CBus.BUS_0
   * @param  address address of the PCF8574 is 0x27 by default
   * @return      instance of this class
   */
  public LiquidCrystal_I2C(int busNumber, int address, int col, int row) throws IOException
  {
    // Create I2C communications bus instance
    bus = I2CFactory.getInstance(busNumber);

    // Create I2C device instance
    device = bus.getDevice(address);
    
    // LCD settings
    this.nbColumn = col;
    this.nbRow = row;
    backlightVal = LCD_NOBACKLIGHT;
    dotSize=LCD_5x8DOTS;
    
    begin();
  }
  // ---------------------------------------------------------------------------        
  @Override
  public String getName() 
  {
    return(NAME);
  }
  // PUBLIC --------------------------------------------------------------------
  /**
   * Reinitialize display 
   *
   */
  public void init() throws IOException
  {
    displayFunction = LCD_4BITMODE | LCD_1LINE | LCD_5x8DOTS;
    begin();
  }
  
  /**
   * Display cursor on LCD 
   *
   * @param b Set it to true to display cursor
   */
  public void cursor(boolean b) throws IOException
  {
    if (b==true)
    {
      displayControl |= LCD_CURSORON;
	    command(LCD_DISPLAYCONTROL | displayControl);
    }
    else
    {
      displayControl &= ~LCD_CURSORON;
	    command(LCD_DISPLAYCONTROL | displayControl);
    }
  }
  
  /**
   * Display blinking cursor 
   *
   * @param b Set it to true to have blinking cursor
   */
  public void blink(boolean b) throws IOException
  {
    if (b==true)
    {
      displayControl |= LCD_BLINKON;
	    command(LCD_DISPLAYCONTROL | displayControl);
    }
    else
    {
      displayControl &= ~LCD_BLINKON;
	    command(LCD_DISPLAYCONTROL | displayControl);
    }
  }
  
  /**
   * Backlight of the display 
   *
   * @param b Set it to true to have backlight
   */
  public void backlight(boolean b) throws IOException
  {
    if (b==true)
    {
      backlightVal=LCD_BACKLIGHT;
	    expanderWrite(0);
    }
    else
    {
      backlightVal=LCD_NOBACKLIGHT;
	    expanderWrite(0);
    }
  }
  
  /**
   * Clear display 
   *
   */
  public void clear() throws IOException
  {
  	command(LCD_CLEARDISPLAY);// clear display, set cursor position to zero
  	delayMicroseconds(2000);  // this command takes a long time!
  }
  
  /**
   * Set cursor to home position 
   *
   */
  public void home() throws IOException
  {
  	command(LCD_RETURNHOME);  // set cursor position to zero
  	delayMicroseconds(2000);  // this command takes a long time!
  }
  
  /**
   * Set cursor at a certain location 
   *
   * @param col Specify the display column
   * @param row Specify the display row
   */
  public void setCursor(int col, int row) throws IOException
  {
    if (row>3) row=3;
  	int row_offsets[] = {0x00, 0x40, 0x14, 0x54};
  	if (row > numLines) 
    {
  		row = numLines-1;    // we count rows starting w/0
  	}
  	command(LCD_SETDDRAMADDR | (col + row_offsets[row]));
  }

  /**
   * Turn on or off display
   *
   * @param b Set it to true to enable display
   */
  public void display(boolean b) throws IOException 
  {
    if (b==true)
    {
  	  displayControl |= LCD_DISPLAYON;
  	  command(LCD_DISPLAYCONTROL | displayControl);
    }
    else
    {
      displayControl &= ~LCD_DISPLAYON;
  	  command(LCD_DISPLAYCONTROL | displayControl);
    }
  }
  
  /**
   * Print a string on the display 
   *
   * @param s String to display
   */
  public void print(String s) throws IOException
  {
    for (int i=0; i<s.length(); i++)
    {
      write(s.charAt(i));    
    }
    // Last character must be empty
    write(' ');
  }
  
  /**
   * Scroll display on the left without changing the RAM
   *
   */
  public void scrollDisplayLeft() throws IOException 
  {
	  command(LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVELEFT);
  }
  /**
   * Scroll display on the right without changing the RAM
   *
   */
  public void scrollDisplayRight() throws IOException 
  {
	  command(LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVERIGHT);
  }
  /**
   * This is for text that flows Left to Right
   *
   */
  public void leftToRight() throws IOException 
  {
	  displayMode |= LCD_ENTRYLEFT;
	  command(LCD_ENTRYMODESET | displayMode);
  }

  /**
   * This is for text that flows Right to Left
   *
   */
  public void rightToLeft() throws IOException 
  {
	  displayMode &= ~LCD_ENTRYLEFT;
	  command(LCD_ENTRYMODESET | displayMode);
  }
  
  /**
   * This will 'right or left justify' text from the cursor
   * 
   * @param b Set the right or left justify
   */
  public void autoScroll(boolean b) throws IOException 
  {
    if (b==true)
    {
	    displayMode |= LCD_ENTRYSHIFTINCREMENT;
	    command(LCD_ENTRYMODESET | displayMode);
    }
    else
    {
      displayMode &= ~LCD_ENTRYSHIFTINCREMENT;
	    command(LCD_ENTRYMODESET | displayMode);
    }
  }

  /**
   * Allows us to fill the first 8 CGRAM locations with custom characters
   * 
   * @param location 
   * @param charmap Contain 8 chars for each line (5x8 dots)
   */
  public void createChar(int location, int charmap[]) throws IOException 
  {
	  location &= 0x07; // We only have 8 locations 0-7
	  command(LCD_SETCGRAMADDR | (location << 3));
	  for (int i=0; i<8; i++) 
    {
		  write((char)charmap[i]);
	  }
  }


  public void loadCustomCharacter(int char_num, int rows[]) throws IOException
  {
		createChar(char_num, rows);
  }
  
  // PRIVATE -------------------------------------------------------------------
  private void begin() throws IOException
  {
    if (nbRow > 1) 
    {
		  displayFunction |= LCD_2LINE;
	  }
	  numLines = nbRow;

    // For some 1 line displays you can select a 10 pixel high font
    if ((dotSize != 0) && (nbRow == 1)) 
    {
      displayFunction |= LCD_5x10DOTS;
    }
    try 
    {
      // SEE PAGE 45/46 FOR INITIALIZATION SPECIFICATION!
      // According to datasheet, we need at least 40ms after power rises above 2.7V before sending commands.
      Thread.currentThread();
      Thread.sleep(150);
    } 
    catch (InterruptedException ex) 
    {
      Logger.getLogger(LiquidCrystal_I2C.class.getName()).log(Level.SEVERE, null, ex);
    }
  
    // Reset expanderand turn backlight off (Bit 8 =1)
    expanderWrite(backlightVal);	
    try 
    {
      Thread.currentThread();
      Thread.sleep(1000);
    } 
    catch (InterruptedException ex) 
    {
      Logger.getLogger(LiquidCrystal_I2C.class.getName()).log(Level.SEVERE, null, ex);
    }

  	// Put the LCD into 4 bit mode
	  // This is according to the hitachi HD44780 datasheet figure 24, pg 46
	
	  // We start in 8bit mode, try to set 4 bit mode
    write4bits(0x03 << 4);
    delayMicroseconds(4500); // wait min 4.1ms
   
    // Second try
    write4bits(0x03 << 4);
    delayMicroseconds(4500); // wait min 4.1ms
   
    // Third go!
    write4bits(0x03 << 4); 
    delayMicroseconds(150);
   
    // Finally, set to 4-bit interface
    write4bits(0x02 << 4); 

	  // Set # lines, font size, etc.
	  command(LCD_FUNCTIONSET | displayFunction);  
	
	  // Turn the display on with no cursor or blinking default
	  displayControl = LCD_DISPLAYON | LCD_CURSOROFF | LCD_BLINKOFF;
	  display(true);
	
	  // Clear it off
	  clear();
	
	  // Initialize to default text direction (for roman languages)
	  displayMode = LCD_ENTRYLEFT | LCD_ENTRYSHIFTDECREMENT;
	
	  // Set the entry mode
	  command(LCD_ENTRYMODESET | displayMode);
	
    // Set cursor to home position
	  home();
  }
  
  private void delayMicroseconds(int value)
  {  
    try 
    {  
      Thread.sleep(0, value*100);
    } 
    catch (InterruptedException ex) 
    {
      Logger.getLogger(LiquidCrystal_I2C.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void command(int value) throws IOException 
  {
	  send(value, 0);
  }
  
  private void send(int value, int mode) throws IOException 
  {
    int highnib=value & 0xf0;
    int lownib=(value << 4) & 0xf0;
    write4bits((highnib) | mode);
    write4bits((lownib) | mode); 
  }
  
  private void write4bits(int value) throws IOException 
  {
    expanderWrite(value);
    pulseEnable(value);
  }

  private void expanderWrite(int data) throws IOException
  {                                        
    byte val=(byte)(data | backlightVal);
    device.write(val);
  }

  private void pulseEnable(int data) throws IOException
  {
    expanderWrite(data | En);	  // En high
    delayMicroseconds(1);		    // Enable pulse must be >450ns

    expanderWrite(data & ~En);	// En low
    delayMicroseconds(50);		  // Commands need > 37us to settle
  }
  
  // Write one char to the LCD  
  private void write(char value) throws IOException 
  {
	  send(value, Rs);
  }
  
}