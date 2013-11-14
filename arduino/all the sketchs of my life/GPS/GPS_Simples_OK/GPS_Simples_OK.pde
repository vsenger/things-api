#include <LiquidCrystal.h>

//LiquidCrystal lcd(4, 9, 17, 16, 15, 14);
LiquidCrystal lcd(4, 9, 57, 56, 55, 54);


void setup()
{
  Serial1.begin(4800);
  Serial.begin(9600);
  initLCD();
}


int x=0,y=0;
byte GPSByte;

void loop()
{
  while(Serial1.available()>0) {     
    GPSByte = Serial1.read();
    Serial.print(GPSByte);
    lcd.setCursor(x,y);
    lcd.print(GPSByte);
    x++;
    if(x==16 && y==0) {
      x=0; 
      y=1;
    }
    else if(x==16 && y==1) {
      x=0; 
      y=0;
    }

  }
}

void initLCD()  {
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Master I2C On");
  lcd.setCursor(0, 1);
  lcd.print("Waiting command.");
}


