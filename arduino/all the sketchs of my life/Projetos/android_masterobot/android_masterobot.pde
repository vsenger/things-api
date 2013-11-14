/*
  Sends sensor data to Android
  (needs SensorGraph and Amarino app installed and running on Android)
*/
#include <Wire.h>

int bussolaVal =200;
boolean dataReady = false;

void setup()  
{
  Serial.begin(9600); 
  Wire.begin();
}


void loop()
{
  delay(200);
      
  Wire.requestFrom(0x12  , 2);  
  delay(50);  
  boolean timeout = true;
  for (int i = 0; i < 10; i++) {
    if (Wire.available() > 0) {
      timeout = false;
      Serial.print("Receive");
      bussolaVal=Wire.receive();
      Serial.println(bussolaVal);
      break;
    }

  }
  Serial.println("esperando");
  
}
