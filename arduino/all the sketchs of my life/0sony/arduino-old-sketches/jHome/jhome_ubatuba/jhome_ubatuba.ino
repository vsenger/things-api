#include "Device.h"

#include "etherShield.h"
#include "ETHER_28J60.h"
#include "Ethernet.h"

Device homeDevice=Device("central-device");
Ethernet ethernet=Ethernet(&homeDevice);

void setup() {
  homeDevice.add("buzz",         PWM,   6);
  homeDevice.add("red",          PWM,   9);
  homeDevice.add("green",        PWM,   5);
  homeDevice.add("blue",         PWM,   3);

  homeDevice.add("p-esq",         DIGITAL,   10);
  homeDevice.add("p-dir",         DIGITAL,   11);

  homeDevice.add("speaker",      DIGITAL, 4);
  homeDevice.add("relay2",       DIGITAL, 15);
  homeDevice.add("luz1",         DIGITAL, 19);
  homeDevice.add("luz2",         DIGITAL, 18);
  homeDevice.add("relay1",      DIGITAL, 14);
  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",         ANALOG,  2);
  //Starting booth communication! 
  Serial.begin(115200);
  int ip[]={192,168,1,3};
  ethernet.startNetwork(ip);
}

void loop() {
  homeDevice.loop();
  ethernet.loop();
}


