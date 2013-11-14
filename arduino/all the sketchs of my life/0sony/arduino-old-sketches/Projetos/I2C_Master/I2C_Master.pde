#include <Wire.h>


char comando[16];
int luz= 0;


void setup()
{  
  Wire.begin();
  Serial.begin(9600);
}  

void loop ()
{  
  executarComando();
  delay(1000);

}  

void executarComando() {
  Serial.println("Enviando");
  Wire.beginTransmission(65); // transmit to device #4
  Wire.send(luz++);	  // sends six bytes
  Wire.endTransmission();    // stop transmitting

}
void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}








