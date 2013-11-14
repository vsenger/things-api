#include <Servo.h> 

#define MOTOR1_P 13
#define MOTOR1_N 9
#define MOTOR1_PWM 6

#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 5

#define SERVO_1 10
#define SERVO_2 11

#define MODE_DEMO 3
#define MODE_WALK 2
#define MODE_SUNSPOT 1
#define MODE_STOP 0
int estado = 0;
int sentido = 0;
long duration, inches, cm;
int reacao = 0;
int interrupt=0;
int mode=3;
Servo myservo10;  // create servo object to control a servo 


void setup() {
  setupEngines();

  setupComponents();
  myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
  Serial.begin(9600);  
}
void setupComponents() {
  pinMode(9, OUTPUT);
}

void setupEngines() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
  //andarFrente(2000,5);


}  

void loop(){
  if(mode==MODE_DEMO) {
    //Demo();
    //return;
  }
  else if(mode==MODE_WALK) {
    //Walk();
  } 
}





