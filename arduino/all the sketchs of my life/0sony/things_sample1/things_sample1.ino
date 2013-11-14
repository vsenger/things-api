#include <Thing.h>

void setup() {  
  thing.addComponent("agua",    DIGITAL, 6);
  thing.addComponent("luzfria",    DIGITAL, 7);
  thing.addComponent("luzquente",    DIGITAL, 8);
  thing.addComponent("vento",    DIGITAL, 9);
  //thing.addComponent("mySensor",  CUSTOM,  10, mySensorRead, mySensorWrite);
  thing.addComponent("light",     ANALOG, 3);
  thing.addComponent("temp",      ANALOG,  2);
  
  thing.addMode(0, slave);
  thing.addMode(1, avoidance);
  thing.start();
  Serial.begin(115200);
}

void loop() {
  thing.loop();
  //Serial.println(222);
  

}
