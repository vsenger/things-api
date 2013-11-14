#include "ElectronKernel.h"

#include <Servo.h> 
Servo servo;

void setup()
{
  Serial.begin(9600);
  setupMotor();
  //servo.attach(5);  
  Kernel.setup(INTERRUPTION);
  Kernel.registerMode("idle");
  Kernel.registerTask(idle);
  Kernel.registerMode("test");
  Kernel.registerTask(test);
  Kernel.registerMode("avoidance");
  Kernel.registerTask(avoidance);
}

void loop()
{

  Kernel.loop();
}

void idle() { 
  //Serial.println("blink mode");
  Kernel.wait(300);
  
}

void test() { 
  /*for(int x=20;x<255;x++) {
    andarFrente(2000,x);
    Serial.println(x);
  }*/
    for(int x=0;x<6;x++) {
    andarFrente(2000,x);
    Serial.println(x);
  }
}

void avoidance() { Serial.println("play music mode");
}



