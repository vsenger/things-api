#include <Servo.h> 

#define P0 0    // Porta Digital 0  -  Led 1  -  
#define P1 1    // Porta Digital 1  -  Led 2
#define P2 2    // Porta Digital 2  -  Led 3  -  PWM
#define P3 3    // Porta Digital 3  -  Led 4  
#define P4 4    // Porta Digital 4  -  Led 5  -  PWM
#define P5 5    // Porta Digital 5  -  Led 6  -  PWM
#define P6 6    // Porta Digital 6  -  Led 7
#define P7 7    // Porta Digital 7  -  Led 8
#define P8 8    // Porta Digital 8  -  Transistor Q3
#define P9 9    // Porta Digital 9  -  Transistor Q2  -  PWM
#define P10 10  // Porta Digital 10 -  Servo1  -  PWM
#define P11 11  // Porta Digital 11 -  Servo2  -  PWM
#define P12 12  // Porta Digital 12 -  Buzzer
#define P13 13  // Porta Digital 13 -  Led 9
#define AP0 14  // Porta Analogica 0
#define AP1 15  // Porta Analogica 1
#define AP2 16  // Porta Analogica 2
#define AP3 17  // Porta Analogica 3
#define AP4 18  // Porta Analogica 4
#define AP5 19  // Porta Analogica 5

#define A0 0    // Porta Analogica 0 - LDR
#define A1 1    // Porta Analogica 1 - POT 1
#define A2 2    // Porta Analogica 2 - POT 2
#define A3 3    // Porta Analogica 3 - Transistor Q5
#define A4 4    // Porta Analogica 4 - Transistor Q4
#define A5 5    // Porta Analogica 3 - Chave

#define LED_1 0
#define LED_2 1
#define LED_3 2
#define LED_4 3
#define LED_5 4
#define LED_6 5
#define LED_7 6
#define LED_8 7
#define LED_9 13

#define Q2 9
#define Q3 8
#define Q4 17
#define Q5 18

#define LDR  0
#define POT1 1
#define POT2 2
#define CHAVE 5 
int pos = 0;    // variable to store the servo position 

int transistorPin1 = P8;
int transistorPin2 = P9;
int transistorPin3 = AP3;
int transistorPin4 = AP4;

int inPin = AP5;

int val = 0;

Servo myservo9;  // create servo object to control a servo 

void setup() {
 // myservo9.attach(10);  // attaches the servo on pin 9 to the servo object 

  pinMode(2, INPUT);
  pinMode(5, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  Serial.begin(9600);
}

void loop(){
  analogWrite(3, analogRead(2));
  analogWrite(4, analogRead(2)/4);
  analogWrite(9, analogRead(2)/4);
  //analogWrite(9, 128);
  //digitalWrite (9, HIGH);
  pos = analogRead(2) / 7;
  //myservo9.write(pos);              // tell servo to go to position in variable 'pos' 

  analogWrite(5, analogRead(2));
  Serial.println(analogRead(2));
  delay(100);
  
}


