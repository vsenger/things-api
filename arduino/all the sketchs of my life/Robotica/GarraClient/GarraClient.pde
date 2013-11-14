#include <Wire.h>
#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27,16,2);

void setup() {
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 1);
  lcd.print("Program-ME v2");

  Serial.begin(9600);

  Wire.begin();
  delay(1000);
  Wire.beginTransmission(65);  
  Wire.send("1+");
  Wire.endTransmission();
  Wire.beginTransmission(65);  
  Wire.send("2-");
  Wire.endTransmission();

  delay(2000);

  Wire.beginTransmission(65);  
  Wire.send("1.");
  Wire.endTransmission();
  Wire.beginTransmission(65);  
  Wire.send("2.");
  Wire.endTransmission();

  delay(2000);

  Wire.beginTransmission(65);  
  Wire.send("1-");
  Wire.endTransmission();
  delay(2000);

  Wire.beginTransmission(65);  
  Wire.send("1.");
  Wire.endTransmission();
  delay(2000);

}

void loop() {
  /*if(Serial.available()>0) Wire.beginTransmission(65);
   while(Serial.available()>0) Wire.send(Serial.read());
   Wire.endTransmission(); */

}



