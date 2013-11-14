#include <Servo.h> 

void setup() {
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(2, INPUT);
  Serial.begin(9600);
}

void loop(){
  analogWrite(3, analogRead(2));
  analogWrite(4, analogRead(2)/4);
  analogWrite(9, analogRead(2)/4);
  analogWrite(5, analogRead(2));
  pos = analogRead(2) / 7;

  Serial.println(analogRead(2));
  delay(100);
  
}


