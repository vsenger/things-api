#include <Wire.h>


void setup()
{
  Serial.begin(9600);
  Wire.begin(65);		    // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event

}
void receiveEvent(int howMany)
{ 
  while(1 < Wire.available()) // loop through all but the last
  {
    char c = Wire.receive(); // receive byte as a character
    Serial.print(c);	   // print the character
  }
  int x = Wire.receive();    // receive byte as an integer
  Serial.println(x);	   // print the integer

  Serial.println("Evento");
  Serial.println(howMany);

} 

void loop()
{
  Serial.println("esperando...");
  delay(1000);
  
}



