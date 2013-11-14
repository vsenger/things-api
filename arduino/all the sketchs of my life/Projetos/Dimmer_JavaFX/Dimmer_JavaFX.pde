#include <Wire.h>

int AC_pin = 3;  // Output to Opto Triac
volatile long dim = 60;    // Dimming level (0-128)  0 = on, 128 = 0ff


void setup()
{
  // start serial port at 9600 bps:
  pinMode(AC_pin, OUTPUT);	// Set the light control pin as output
  Serial.begin(9600);
  attachInterrupt(0, light, RISING);  // Attach an Interupt to pin2 (interupt 0) for Zero Cross Detection
  Wire.begin(4);		    // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event

}
void receiveEvent(int howMany)
{ 
  Serial.println("Evento");
  Serial.println(howMany);
//  dim   = howMany;

} 

void light()  // function to be fired at the zero crossing to dim the light
{
  //Serial.println("AA");

  if(dim<125) {
    long dimtime = (60*dim);  // eval the proper pause to fire the triac
    delayMicroseconds(dimtime);  // delay the dim time
    digitalWrite(AC_pin, HIGH);  // fire the Triac
    delayMicroseconds(1);	 // pause briefly to ensure the triac turned on
    digitalWrite(AC_pin, LOW);   // turn off the Triac gate (triac will not turn off until next zero cross)
  }
  else {
    digitalWrite(AC_pin, LOW);   // turn off the Triac gate (triac will not turn off until next zero cross)
  }    
}

void loop()
{
  if (Serial.available() > 0) {
    byte inByte = Serial.read();
    dim = inByte;

  }
}



