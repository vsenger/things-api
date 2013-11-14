#include <Servo.h> 

#define MOTOR1_P 4
#define MOTOR1_N 5
#define MOTOR1_PWM 11

#define MOTOR2_P 6
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define PING_PIN 9
#define SERVO_H 10
#define SERVO_V 11

#define MODE_BUTTON 19 //5 anal�gica lida como digital...

#define MODE_DEMO 2
#define MODE_WALK 3
#define MODE_SUNSPOT 1
#define MODE_STOP 0
int estado = 0;
int sentido = 0;
long duration, inches, cm;
int reacao = 0;
int interrupt=0;
int mode=0;
Servo servoHorizontal;  // create servo object to control a servo 
Servo servoVertical;  // create servo object to control a servo 


void setup() {
  setupEngines();
  setupSunSpot();
  servoHorizontal.attach(SERVO_H); 
  //servoVertical.attach(SERVO_V); 
  Serial.begin(9600);  
}


void setupEngines() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
  analogWrite(MOTOR1_PWM,0);
  analogWrite(MOTOR2_PWM,0);
  
}  

void loop(){
  
  if(digitalRead(MODE_BUTTON)==0) {
    while(digitalRead(MODE_BUTTON)==0);
    mode++;
    if(mode==4) mode=0;
  }  
  if(mode==MODE_DEMO) {
    Demo();
  }
  else if(mode==MODE_WALK) {
    Walk();
  } 
  else if(mode==MODE_SUNSPOT) {
    SunSpot();
  } 
  
}





