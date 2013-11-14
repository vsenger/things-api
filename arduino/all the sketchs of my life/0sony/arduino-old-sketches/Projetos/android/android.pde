/*
  Sends sensor data to Android
  (needs SensorGraph and Amarino app installed and running on Android)
*/
#include <LiquidCrystal.h>
#include <MeetAndroid.h>
#include <Wire.h>

MeetAndroid meetAndroid;
int sensor = 5;
int bussolaVal =200;

LiquidCrystal lcd(4, 9, 14, 15, 16, 17);

void setup()  
{
  // use the baud rate your bluetooth module is configured to 
  // not all baud rates are working well, i.e. ATMEGA168 works best with 57600
  Serial.begin(115200); 
  Wire.begin(0x12);
  Wire.onRequest(sendBussola);
  initLCD();
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0); 
  lcd.print("Device Ready"); 
  // we initialize pin 5 as an input pin
  pinMode(sensor, INPUT);
  meetAndroid.registerFunction(bussola, 'A');
}

void sendBussola() {
   Wire.send(bussolaVal);
   
}

void bussola(byte flag, byte numOfValues) {
  int b[numOfValues];
  meetAndroid.getIntValues(b);
  bussolaVal=b[0];
  
  lcd.setCursor(0, 1);
  lcd.print(b[0]);
}

void initLCD()  {
}
void loop()
{
  meetAndroid.receive(); // you need to keep this in your loop() to receive events
  
  // read input pin and send result to Android
  meetAndroid.send(analogRead(sensor));
  
  // add a little delay otherwise the phone is pretty busy
  delay(200);
}
