#include <ElectronKernel>

void setup()
{
  Kernel.setup();
  Kernel.registerMode("blink");
  Kernel.registerTask(blinkLeds);
  Kernel.registerMode("readSensor");
  Kernel.registerTask(blinkLeds);
  Kernel.registerMode("playMusic");
  Kernel.registerTask(blinkLeds);
  
}

void loop()
{
  Kernel.loop;
}

void blinkLeds() {
  //...
}

void readSensor() {
  //...
}

void playMusic() {
  //...
}

