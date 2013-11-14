#include <Servo.h> 

Servo myservo1;  // create servo object to control a servo 

void setup() 
{ 
  myservo1.attach(5);  // attaches the servo on pin 9 to the servo object 
  Serial.begin(9600);
} 

void loop() 
{
  int distancia = analogRead(1);
  Serial.println(distancia);
  int posicaoServo = map(distancia, 10, 30, 1, 170);
  myservo1.write(posicaoServo);

  

} 


