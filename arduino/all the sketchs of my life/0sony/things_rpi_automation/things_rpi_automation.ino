#include <Thing.h>

void setup() {  
  thing.addComponent("red",    PWM, 3);
  thing.addComponent("green",  PWM, 6);
  thing.addComponent("blue",   PWM, 9);
  thing.addComponent("speaker",   DIGITAL, 4);
 
  thing.addComponent("light",     ANALOG, 3);
  thing.addComponent("temp_in",   ANALOG, 2);
  thing.addComponent("alcohol",   ANALOG, 0);
  thing.addComponent("temp_out",  CUSTOM,  1, temperature_out, sample_write);
  thing.addComponent("humidity",  CUSTOM,  1, humidity, sample_write);
  thing.addComponent("servoarm",     CUSTOM,  6, servo_read, servo_write_arm);
 
  thing.addMode(0, automation);
  thing.start(-1); //this mean that we dont want multiple behavior / loops here... any other number 
  Serial.begin(115200);
}

char* all() {
  
  //Serial.println(temp_out);
 // Serial.println(humidity);
  
}

void loop() {
  thing.loop();
}

void automation() {}

