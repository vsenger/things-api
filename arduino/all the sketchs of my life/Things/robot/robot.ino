#include "Device.h"

Device myThing=Device("robot-javaone");

void setup() {
  homeDevice.add("pwm-aux1",     PWM,   3);
  homeDevice.add("pwm-aux2",     PWM,   5);
  homeDevice.add("buzz",         PWM,   6);
  homeDevice.add("fan",          PWM,   9);

  homeDevice.add("speaker",      DIGITAL, 4);
  homeDevice.add("relay1",       DIGITAL, 14);
  homeDevice.add("relay2",       DIGITAL, 15);
  homeDevice.add("relay3",       DIGITAL, 7);
  homeDevice.add("relay4",       DIGITAL, 8);
  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",         ANALOG,  2);
  //Starting booth communication! 
  Serial.begin(115200);

}

void loop() {
  homeDevice.loop();
}


