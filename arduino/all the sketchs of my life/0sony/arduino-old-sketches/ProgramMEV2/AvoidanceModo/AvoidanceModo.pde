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
long distancia;
long distanciaEsquerda;
long distanciaDireita;

void setup(){ 
  setupMotor();  
  servoH.attach(5);
  servoV.attach(6);
  
}

void loop() {
  lerPing();

  while(distancia>25) {
    andarFrente(200,3);
    lerPing();
  }
  while(distancia>15 && distancia<25) {
    parar();
    delay(200);
    lerPingFull();
    if(distanciaEsquerda>distancia && distanciaEsquerda>distanciaDireita) {
      girarNoEixo(300,2,1);
    }
    if(distanciaDireita>distancia && distanciaDireita>distanciaEsquerda) {
      girarNoEixo(300,2,0);
    }    
    lerPing();
  }
  while(distancia>5 && distancia<15) {
    parar();
    andarTraz(500,2);
    girarNoEixo(500,2, random(10)%2==0);
    lerPing();
  }

}




