#include <Thing.h>

void setup() {  
  thing.addComponent("red",    PWM, 3);
  thing.addComponent("green",  PWM, 6);
  thing.addComponent("blue",   PWM, 9);
  thing.addComponent("speaker",   DIGITAL, 4);
 
  thing.addComponent("light",     ANALOG, 3);
  thing.addComponent("temp_in",   ANALOG, 2);
  thing.addComponent("temp_celsius",     CUSTOM,  2, temp_read, temp_write );
  thing.addComponent("alcohol",   ANALOG, 0);
  thing.addComponent("servo",     CUSTOM,  6, servo_read, servo_write);
 
  thing.addMode(0, automation);
  thing.start();
  Serial.begin(115200);
}

void loop() {
  thing.loop();
}

void automation() {}

