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
int direcaoServo=0;

void setup() {
  servoH.attach(11);
  servoV.attach(10);
  Serial.begin(9600);
  servoH.write(1);
  delay(1500);
  servoH.write(180);
  delay(1500);
  servoH.write(90);
  delay(1500);
  
}
void loop() {
  Serial.println(analogRead(1));
  delay(10);
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

