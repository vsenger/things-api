#include <SoftwareSerial.h>
#include <LiquidCrystal.h>

SoftwareSerial GPS = SoftwareSerial(2, 3);
LiquidCrystal lcd(4, 9, 17, 16, 15, 14);

byte GPSByte;

void setup()
{
  GPS.begin(4800);
  Serial.begin(9600);
  //initLCD();
}
int x=0,y=0;
void loop()
{
  // Get the GPS data
  GPSByte = GPS.read();
  // Print GPS data
  lcd.setCursor(x,y);
  lcd.print(GPSByte);
  x++;
  if(x==16 && y==0) {
    x=0; y=1;
  }
  else if(x==16 && y==1) {
    x=0; y=0;
  }
  Serial.print(GPSByte);
}

void initLCD()  {
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Master I2C On");
  lcd.setCursor(0, 1);
  lcd.print("Waiting command.");
}

