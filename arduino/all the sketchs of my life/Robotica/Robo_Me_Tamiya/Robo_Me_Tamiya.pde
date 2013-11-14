#include <Servo.h> 
#include <LiquidCrystal.h>

#define MOTOR1_P 10
#define MOTOR1_N 9
#define MOTOR1_PWM 6
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 5
#define PING_PIN 14
int  SensorTemperatura = 1 ;
int  LDR = 5;


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

void lerPing() {
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  long duration = pulseIn(PING_PIN, HIGH);

  distancia= duration / 29 / 2;
  Serial.print("Distancia: ");
  Serial.println(distancia);

}

void loop() {
  lerPing();

  while(distancia>25) {
    andarFrente(5);  
    delay(50);
    lerPing();
  }
  int delaying = 350;
  while(distancia>15 && distancia<25) {
    long d1 = distancia;
    girarNoEixo(5,0);
    delay(delaying);
    lerPing();
    long d2 = distancia;
    girarNoEixo(5,1);
    delay(delaying*2);
    parar();
    lerPing();
    long d3 = distancia;

    //voltando posiço original
    girarNoEixo(5,0);
    delay(delaying);
    parar();
    //tomando decisao..

    if(d1>d2 && d1>d3) {
      //nem para um lado nem para outro funcionou...
      delaying+=100;
    }
    else {
      if(d2>d3) {
        girarNoEixo(5,0);
        delay(delaying);       
      }
      else {
        girarNoEixo(5,1);
        delay(delaying);       
      }
      andarFrente(5);
      delay(250);
    }
  }
  while(distancia>5 && distancia<15) {
    girarNoEixo(5, random(10)%2==0);
    delay(300 + random(200,400));
    lerPing();
  }
} 







