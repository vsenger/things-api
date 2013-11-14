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

int sentido = 0;

void setup()  {
  Serial.begin(9600);
  Serial.println("Setup feito");
  
  setupSunSpot();
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);

}

void loop() {
  //Serial.println("aaa");
  SunSpot();
  //Serial.println("BBB");
  //meetAndroid.receive(); 
  //meetAndroid.send(analogRead(1));
  //testeLeds();
  /*andarFrente(1500,3);
  parar();
  delay(1500);
  andarTraz(1500,3);
  parar();
  delay(1500);
  girarNoEixo(1500,3,0);
  parar();
  delay(1500);
  girarNoEixo(1500,3,1);
  parar();
  delay(1500);
  */

} 

