#include <Servo.h> 

#define MOTOR1_P 13
#define MOTOR1_N 12
#define MOTOR1_PWM 5
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define PING_PIN 1
#define LED_RED 9
#define LED_BLUE 17
#define LED_GREEN 18
#define SERVO_1 11

Servo myservo10;  // create servo object to control a servo 


void setup() {
  setupEngines();
  myservo10.attach(SERVO_1);  // attaches the servo on pin 10 to the servo object 
  Serial.begin(9600);  
}

void setupEngines() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
}  

void loop(){
    Demo();
}

