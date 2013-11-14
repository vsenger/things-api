#include <Servo.h> 
#include <LiquidCrystal.h>

#define MOTOR1_P 13
#define MOTOR1_N 9
#define MOTOR1_PWM 6
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 5
#define PING_PIN 16
int  SensorTemperatura = 1 ;
int  LDR = 5;

//LiquidCrystal lcd(RS, EN, D4, D5, D6, D7);
LiquidCrystal lcd(14, 17, 4, 3, 2, 18);

long DistanciaEsquerda;
long DistanciaDireita;
long distancia;
Servo ServoV;
Servo ServoH;

int sentido;

void setup() {
  Serial.begin(9600);

  // set up the LCD's number of rows and columns: 

  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
}

void loop() {
  LerDistancias( );

  if (DistanciaEsquerda > 40 && DistanciaDireita > 40) {
    andarFrente(2);
  }

  if (DistanciaEsquerda <= 40 && DistanciaDireita > 40) {
    virarDireita(2);
  }

  if (DistanciaEsquerda > 40 && DistanciaDireita <= 40) {
    virarEsquerda(2);
  }

  if (DistanciaEsquerda <= 40 && DistanciaDireita <= 40) {
    parar();
    girarNoEixo(2);
  }

  atualizaDisplay();
  delay (100);

} 




