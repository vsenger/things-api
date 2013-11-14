#include "Device.h"

#include "etherShield.h"
#include "ETHER_28J60.h"
#include "Ethernet.h"
#include "Servo.h"

Device homeDevice=Device("central-device");
Ethernet ethernet=Ethernet(&homeDevice);
Servo servo;
void setup(){
  servo.attach(6);
  //homeDevice.add("buzz",         PWM,   6);
  //homeDevice.add("red",          PWM,   9);
  // homeDevice.add("green",        PWM,   5);
  //homeDevice.add("blue",         PWM,   3);


  //homeDevice.add("speaker",      DIGITAL, 4);
  //homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("pot1",        ANALOG, 0);
  //homeDevice.add("temp",         ANALOG,  2);
  //Starting booth communication! 
  Serial.begin(115200);
  int ip[]={192,168,1,5};
  ethernet.startNetwork(ip);
}

void loop() {
  homeDevice.loop();
  ethernet.loop();
  servo.write(map(analogRead(0), 0, 1023, 0, 179));
}


