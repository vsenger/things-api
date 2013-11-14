#include <Servo.h> 

Servo myservo9;  // create servo object to control a servo 
Servo myservo10;  // create servo object to control a servo 

int pos = 0;    // variable to store the servo position 

void setup() 
{ 
  myservo9.attach(11);  // attaches the servo on pin 9 to the servo object 
  myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
} 

void loop() 
{ 
    myservo10.write(95);
    delay(3000);
  
    myservo10.write(110);
    delay(3000);
  /*
  for(int x=85;x<105;x+=5) {
    myservo10.write(x);
    delay(2500);
  }
  
  for(int x=110;x>80;x-=5) {
    myservo10.write(x);
    delay(2500);
  }*/
} 

