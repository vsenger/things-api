#include <Servo.h> 

#define MOTOR1_P 4
#define MOTOR1_N 5
#define MOTOR1_PWM 11

#define MOTOR2_P 6
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define PING_PIN 13
#define SERVO_1 10
#define MODE_BUTTON 19 //5 analógica lida como digital...

#define MODE_DEMO 3
#define MODE_WALK 2
#define MODE_SUNSPOT 1
#define MODE_STOP 0
int estado = 0;
int sentido = 0;
long duration, inches, cm;
int reacao = 0;
int interrupt=0;
int mode=0;
Servo myservo10;  // create servo object to control a servo 


void setup() {
  setupEngines();
  setupSunSpot();
  myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
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



/*
  if(y>0) {
 sentido = 1;
 analogWrite(MOTOR1_PWM,(y == 10 ? 85 : y==20 ? 170 : 255));
 analogWrite(MOTOR2_PWM,(y == 10 ? 85 : y==20 ? 170 : 255));  
 digitalWrite(MOTOR1_P,sentido);
 digitalWrite(MOTOR1_N,!sentido);
 digitalWrite(MOTOR2_P,sentido);
 digitalWrite(MOTOR2_N,!sentido);
 }
 else if(y<0){ //para traz
 sentido = 0;
 analogWrite(MOTOR1_PWM,(y == -10 ? 85 : y==-20 ? 170 : 255));
 analogWrite(MOTOR2_PWM,(y == -10 ? 85 : y==-20 ? 170 : 255));  
 digitalWrite(MOTOR1_P,sentido);
 digitalWrite(MOTOR1_N,!sentido);
 digitalWrite(MOTOR2_P,sentido);
 digitalWrite(MOTOR2_N,!sentido);
 
 }
 */

/*
 
 
 */


