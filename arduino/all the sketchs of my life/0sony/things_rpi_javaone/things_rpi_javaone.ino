#include <Thing.h>

void setup() {  
  thing.addComponent("speaker",   DIGITAL, 4); 
  thing.addMode(0, automation);
  thing.start();
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

