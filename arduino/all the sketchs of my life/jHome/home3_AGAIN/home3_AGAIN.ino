#include "Device.h"
#include "etherShield.h"
#include "ETHER_28J60.h"
#include "EEPROM.h"
#include "Ethernet.h"

Device homeDevice=Device("central-device");
Ethernet ethernet=Ethernet(&homeDevice);

void setup() {
  homeDevice.add("red",          PWM,   3);
  homeDevice.add("distance",        PING,   5);
  homeDevice.add("blue",         PWM,   6);
  homeDevice.add("fan",          PWM,   9);

  homeDevice.add("speaker",      DIGITAL, 4);
  homeDevice.add("relay1",       DIGITAL, 7);
  homeDevice.add("relay2",       DIGITAL, 8);
  homeDevice.add("relay3",       DIGITAL, 14);
  homeDevice.add("relay4",       DIGITAL, 15);
  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",         ANALOG,  2);
  //Starting booth communication! 
  Serial.begin(115200);
  int ip[]={192,168,1,15};
  ethernet.startNetwork(ip);
}

void loop() {
  homeDevice.loop();
  ethernet.loop();
}


