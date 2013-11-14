#include <Servo.h> 

#define SPEAKER 4 //em paralelo com led
#define LED_P5 4 //em paralelo com speaker
#define LED_P4 7
#define LED_P3 8
#define LED_P2 12
#define LED_P1 13

#define CHAVE_1 2

#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 
#define TRANSISTOR_Q3 3

//Analogicas
#define SENSOR_TEMPERATURA 2
#define SENSOR_LUZ 3
#define ENTRADA_1 0
#define ENTRADA_2 1

//Entradas para interrupcao
#define INT_1 3
#define INT_0 2

#define SERVO_1 5
#define SERVO_2 6

//Ponte H
#define MOTOR1_A 7 //led paralelo
#define MOTOR1_B 8 //led paralelo
#define MOTOR1_PWM 10 //led paralelo

#define MOTOR2_A 12 //led paralelo
#define MOTOR2_B 13 //led paralelo
#define MOTOR2_PWM 11 //led paralelo

Servo servoV;
Servo servoH;

void setup(){ 
  setupMotor();  
  servoH.attach(5);
  servoV.attach(6);
  
}

void loop() { 
  andarFrente(1000,2);
  parar();
  andarTraz(1000,2);
  parar();
  girarNoEixo(1000,2,0);
  parar();
  girarNoEixo(1000,2,1);
  parar();
  return;
}


