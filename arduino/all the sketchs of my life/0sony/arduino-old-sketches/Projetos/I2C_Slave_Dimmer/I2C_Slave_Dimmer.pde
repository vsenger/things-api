#include <Wire.h>

int AC_pin = 7; 
volatile long dim = 100;


void setup()
{
  pinMode(AC_pin, OUTPUT);
  Serial.begin(9600);
  attachInterrupt(0, light, RISING);
  Wire.begin(66);		  
  Wire.onReceive(receiveEvent);

}
void receiveEvent(int howMany)
{ 
  char comando[16];
  int counter=0;
  while(1 < Wire.available())
  {
    char c = Wire.receive(); 
    comando[counter++]=c;
    Serial.print(c);	   
  }
  int x = Wire.receive();  
  Serial.println(x);	   

  Serial.println("Evento");
  Serial.println(howMany);
  //value, fromLow, fromHigh, toLow, toHigh)
  dim = map(comando[1], 48,57,10,120);
  
} 

void light()
{
  Serial.println("int");
  
  if(dim<125) {
    long dimtime = (60*dim);  // eval the proper pause to fire the triac
     Serial.println(dimtime);
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



