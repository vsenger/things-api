// Controlling a servo position using a potentiometer (variable resistor) 
// by Michal Rinott <http://people.interaction-ivrea.it/m.rinott> 

#include <Servo.h> 

Servo myservo;  // create servo object to control a servo 
Servo myservo1;  // create servo object to control a servo 

int potpin = 1;  // analog pin used to connect the potentiometer
int val;    // variable to read the value from the analog pin 

void setup() 
{ 
  Serial.begin(9600);
  pinMode(9, OUTPUT);
  pinMode(6, OUTPUT);

  myservo.attach(10);  // attaches the servo on pin 9 to the servo object 
  myservo1.attach(11);  // attaches the servo on pin 9 to the servo object 
} 

void loop() 
{ 
  /*for(int x=45;x<145;x++) {
   if(x%10==0) {
   myservo.write(x);
   myservo1.write(x);
   }
   delay(300);
   }
   for(int x=145;x>45;x--) {
   if(x%10==0) {
   myservo.write(x);
   myservo1.write(x);
   }
   delay(300);
   }*/
  myservo.write(30);
  myservo1.write(30);
  delay(500);
  myservo.write(70);
  myservo1.write(70);
  delay(500);
  

} 


