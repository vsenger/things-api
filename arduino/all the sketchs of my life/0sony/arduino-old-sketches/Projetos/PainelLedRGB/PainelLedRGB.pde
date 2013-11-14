#include <Wire.h>
void setup() {
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  Serial.begin(9600);
  Wire.begin(65);		   
  Wire.onReceive(receiveEvent); 

}
int val;
byte comando[16];

void receiveEvent(int howMany)  
{
  Serial.println(howMany);
  
  int counter=0;
  while(1 < Wire.available())
  {
    byte c = Wire.receive(); 
    comando[counter++]=c;
    Serial.print(c);
  }
  char numero[3];
  numero[0]=comando[1];
  numero[1]=comando[2];
  numero[2]=comando[3];
  val = atoi(numero);

} 


void loop() {
  /*  Serial.println(  analogRead(0));
   Serial.println(  analogRead(1));
   Serial.println(  analogRead(2));
   Serial.println( "============");*/
  //analogWrite(9, map(analogRead(0),0,1024,0,255));
  //analogWrite(10, map(analogRead(1),0,1024,0,255));
  //analogWrite(11, map(analogRead(2),0,1024,0,255));
  if(val>0 && val<90) {
    analogWrite(9, map(val,0,90,0,255));
    analogWrite(10, 0);
    analogWrite(11, 0);

  }
  else if(val>90 && val<180) {
    analogWrite(10, map(val,90,180,0,255));
    analogWrite(9, 0);
    analogWrite(11, 0);

  }
  else if(val>180 && val<270) {
    analogWrite(11, map(val,180,270,0,255));
    analogWrite(10, 0);
    analogWrite(9, 0);
  }
  else if(val>270 && val<360) {
    analogWrite(9, map(val,270,360,0,255));
    analogWrite(10, map(val,270,360,0,255));
    analogWrite(11, 0);
  }

}





