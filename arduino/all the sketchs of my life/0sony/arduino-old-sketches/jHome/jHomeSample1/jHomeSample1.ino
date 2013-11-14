#include "jHomeEthernet.h"

jHomeEthernet ethernet=jHomeEthernet();
//jHome home=jHome();

void setup() {
  //setup IP and Mac Address
  ethernet.setup();
  home.add("relay1",     "RELAY", 5);
  home.add("relay2",     "RELAY", 6);
  home.add("light",      "LIGHT", 3);
  home.add("temperature","TEMP",  2);
  
}

void loop() {
  home.loop();  
}
