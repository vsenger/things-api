#include "Device.h"
#include "etherShield.h"
#include "ETHER_28J60.h"

Device homeDevice=Device("old-central");

void setup() {
  homeDevice.add("red",          PWM,   5);
  homeDevice.add("green",        PWM,   6);
  homeDevice.add("blue",         PWM,   9);

  homeDevice.add("relay1",       DIGITAL, 4);
  homeDevice.add("relay2",       DIGITAL, 2);
  homeDevice.add("portao1",      DIGITAL, 7);
  homeDevice.add("portao2",      DIGITAL, 8);

  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",         ANALOG,  2);
  //Starting booth communication! 
  Serial.begin(115200);
  //int ip[]={192,168,1,15};
  //homeDevice.startNetwork(ip);
}

void loop() {
  homeDevice.loop();
}


