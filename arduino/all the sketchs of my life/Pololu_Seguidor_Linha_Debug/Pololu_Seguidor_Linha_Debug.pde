#include <PololuQTRSensors.h>
#include <avr/pgmspace.h>

#define NUM_SENSORS   3     // number of sensors used
#define TIMEOUT       2500  // waits for 2500 us for sensor outputs to go low
#define EMITTER_PIN   QTR_NO_EMITTER_PIN     // emitter is controlled by digital pin 2

unsigned int last_proportional = 0;
long integral = 0;

// sensores 1 a 5 estao conectados nas portas digitais 5, 9, 2, 3 e 4, respectivamente
PololuQTRSensorsRC qtrrc((unsigned char[]) {2, 3, 4}, NUM_SENSORS, TIMEOUT, EMITTER_PIN); 
unsigned int sensorValues[NUM_SENSORS];

boolean calibrado = false;

void setup(){
  Serial.begin(115200);
  Serial.println("Heelooo");
  delay(20);
}

void loop(){
  seguirLinha();
  delay(100);
  
  
}



