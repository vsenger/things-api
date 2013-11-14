#include <Wire.h>

#define RED 17
#define GREEN 7
#define BLUE 9


void setup()
{
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  
  //Serial.begin(9600);
  Wire.begin(67);		   
  Wire.onReceive(receiveEvent); 

}
void receiveEvent(int howMany)  
{
  char comando[16];
  int counter=0;
  while(Wire.available()>0) {
    digitalWrite(2,HIGH);
    digitalWrite(3,HIGH);
    delay(100);
    digitalWrite(2,LOW);
    digitalWrite(3,LOW);
    Wire.receive();// skyp first byte, my ID
  }
  /*Serial.println("Evento, bytes recebidos");
  Serial.println(howMany);
  
  Serial.println(Wire.receive(),DEC);
  Serial.println(Wire.receive(),DEC);
  Serial.println(Wire.receive(),DEC);
  Serial.println("Evento finalizado...");*/
  

} 

void loop()
{

}




