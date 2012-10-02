#include "Device.h"

#include "etherShield.h"
#include "ETHER_28J60.h"
#include "Ethernet.h"

Device homeDevice=Device("central-device");
Ethernet ethernet=Ethernet(&homeDevice);

void setup() {


  homeDevice.add("speaker",      DIGITAL, 4);
  homeDevice.add("relay1",       DIGITAL, 7);
  homeDevice.add("relay2",       DIGITAL, 8);
  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temperature",  ANALOG,  2);

  homeDevice.add("pwm-aux1",     PWM,   3);
  homeDevice.add("pwm-aux2",     PWM,   5);
  //Starting booth communication! 
  Serial.begin(115200);
  int ip[]={192,168,1,15};
  ethernet.startNetwork(ip);
}

void loop() {
  homeDevice.loop();
  ethernet.loop();
}


