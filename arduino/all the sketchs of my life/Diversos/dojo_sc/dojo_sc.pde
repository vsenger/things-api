#include <Servo.h> 
#define MOTOR1_P 13
#define MOTOR1_N 12
#define MOTOR1_PWM 5
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define DISTANCIA 1

int sentido;
Servo servoV;
Servo servoH;
int contador=0;
long distancia;
long distanciaEsquerda;
long distanciaDireita;

void setup() {
  servoH.attach(11);
  servoV.attach(10);
  Serial.begin(9600);
}

int lerPingComMedia() {
  int d1,d2,d3;
  d1 = analogRead(1);
  delay(5);
  d2 = analogRead(1);
  delay(5);
  d3 = analogRead(1);
  delay(5);
  return (d1 + d2 + d3)/3;
}

void lerPing() {
  distancia = lerPingComMedia();
}  
void lerPingFull() {
  servoH.write(30);
  delay(200);
  distanciaEsquerda = lerPingComMedia();
  servoH.write(100);
  delay(200);
  distancia = lerPingComMedia();
  servoH.write(150);
  delay(200);
  distanciaDireita = lerPingComMedia();
  servoH.write(85);
}  

void loop() {
  lerPing();
  if(random(0,1)) {
  }
  else {
  }
  while(distancia<20) {
    andarTraz(200,5);
    parar();
    lerPing();
  }
  while(distancia>20) {
    andarFrente(200,5);
    lerPing();
  }

}






void girarNoEixo(int tempo, int intensidade, int sentido1) {
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido1);
  digitalWrite(MOTOR1_N,!sentido1);
  digitalWrite(MOTOR2_P,!sentido1);
  digitalWrite(MOTOR2_N,sentido1);
  delay(tempo);
}

void parar() {  
  analogWrite(MOTOR1_PWM,0 );
  analogWrite(MOTOR2_PWM,0);
}

void andarTraz(int tempo, int intensidade) {
  sentido = 0;
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);
}
void andarFrente(int tempo,int intensidade) {
  sentido = 1;
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);

}
void virarEsquerda(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);

}
void virarDireita(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);
}

void virarEsquerdaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_P,!sentido);
  digitalWrite(MOTOR1_N,sentido);
  digitalWrite(MOTOR2_P,!sentido);
  digitalWrite(MOTOR2_N,sentido);
  delay(tempo);

}
void virarDireitaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,!sentido);
  digitalWrite(MOTOR1_N,sentido);
  digitalWrite(MOTOR2_P,!sentido);
  digitalWrite(MOTOR2_N,sentido);
  delay(tempo);
}


