#include <Thing.h>

void setup() {  
  thing.addComponent("red",    PWM, 3);
  thing.addComponent("green",  PWM, 6);
  thing.addComponent("blue",   PWM, 9);
  thing.addComponent("speaker",   DIGITAL, 4);
 
  thing.addComponent("light",     ANALOG, 3);
  thing.addComponent("temp_in",   ANALOG, 2);
  thing.addComponent("temp_out",  CUSTOM,  1, temperature_out, sample_write);
  thing.addComponent("humidity",  CUSTOM,  1, humidity, sample_write);
  thing.addComponent("servos",     CUSTOM,  5, servo_read, servos_write);
  //thing.addComponent("servob",     CUSTOM,  5, servo_read, servob_write);
  //thing.addComponent("servoc",     CUSTOM,  5, servo_read, servoc_write);
  thing.addComponent("distance",  CUSTOM,  5, distance_read, sample_write);
 
  thing.addMode(0, automation);
  thing.start();
  Serial.begin(115200);
}

void loop() {
  thing.loop();
}

void automation() {}

