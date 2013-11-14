#include <ElectronKernel.h>
#include <Wire.h>
/*
 Arduinos -> Arduino -> Modes  -> Mode -> Tasks -> Task
                     -> Components -> Component
                     -> Events -> Event
       
 */
void setup() {
  Kernel.setup();
  Kernel.registerMode(1, "Blinking Leds", piscarLedSetup);  
  Kernel.registerTask(1, piscarLed);  
  Kernel.registerMode(2, "Blinking Leds");  
  Kernel.registerTask(2, nada);  
  
}

void loop() {
  Kernel.loop();
}

void nada() {}
void piscarLed() {
}
void piscarLedSetup() {
}
