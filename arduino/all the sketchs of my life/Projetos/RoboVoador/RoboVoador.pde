#include <Servo.h> 

#define MOTOR1 11
#define SERVO1 10
#define LDR 0
Servo myservo; 
 
void setup() 
{ 
  Serial.begin(9600);
  myservo.attach(SERVO1);
} 
 
void loop() 
{ 
  myservo.write(1);                  
  funcionaMotor(1500);  
  delay(5000); 
  myservo.write(90);                  
  funcionaMotor(1500);  
  delay(5000);                           
  myservo.write(178);                  
  funcionaMotor(1500);  
  delay(5000);                        
} 

void funcionaMotor(int tempo) {
  analogWrite(MOTOR1,80);
  delay(tempo);
  analogWrite(MOTOR1,0);
}
