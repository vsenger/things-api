#include <Wire.h>
#define RED 9
#define GREEN 7
#define BLUE 7
int red=2,green=2,blue=2;

void setup()
{
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  Serial.begin(9600);
  Wire.begin(67);		    // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event

}
void receiveEvent(int howMany)
{
  char comando[16];
  int counter=0;
  while(1 < Wire.available()) // loop through all but the last
  {
    char c = Wire.receive(); // receive byte as a character
    comando[counter++]=c;
  }
  char c = Wire.receive(); // receive byte as a character

  red = comando[1];
  green = comando[2];
  blue = comando[3];
  Serial.println("comando");
  Serial.println(comando);

} 

void loop()
{
  analogWrite(RED, red==1 ? 0 : red);
  analogWrite(BLUE, blue==1 ? 0 : blue);
  analogWrite(GREEN, green==1 ? 0 : green);
  Serial.println("Red");
  Serial.println(red);
  Serial.println("Green");
  Serial.println(green);
  Serial.println("Blue");
  Serial.println(blue);

  Serial.println("esperando...");
  delay(1000);

}





