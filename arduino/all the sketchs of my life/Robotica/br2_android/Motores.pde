#define MOTOR1_control_A 7
#define MOTOR1_control_B 8
#define MOTOR1_PWM 10
#define MOTOR2_control_A 12
#define MOTOR2_control_B 13
#define MOTOR2_PWM 11


int sentido=0;
void setupMotor() {
  pinMode(MOTOR1_control_A, OUTPUT);
  pinMode(MOTOR1_control_B, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_control_A, OUTPUT);
  pinMode(MOTOR2_control_B, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
}

void girarNoEixo(int tempo, int intensidade, int sentido1) {
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_control_A,sentido1);
  digitalWrite(MOTOR1_control_B,!sentido1);
  digitalWrite(MOTOR2_control_A,!sentido1);
  digitalWrite(MOTOR2_control_B,sentido1);
  delay(tempo);
}

void parar() {  
  analogWrite(MOTOR1_PWM,0 );
  analogWrite(MOTOR2_PWM,0);
}

void andarFrente(int tempo, int intensidade) {
  sentido = 1;
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_control_A,sentido);
  digitalWrite(MOTOR1_control_B,!sentido);
  digitalWrite(MOTOR2_control_A,sentido);
  digitalWrite(MOTOR2_control_B,!sentido);
  delay(tempo);
}
void andarTraz(int tempo,int intensidade) {
  sentido = 0;
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_control_A,sentido);
  digitalWrite(MOTOR1_control_B,!sentido);
  digitalWrite(MOTOR2_control_A,sentido);
  digitalWrite(MOTOR2_control_B,!sentido);
  delay(tempo);

}
void virarEsquerda(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_control_A,sentido);
  digitalWrite(MOTOR1_control_B,!sentido);
  digitalWrite(MOTOR2_control_A,sentido);
  digitalWrite(MOTOR2_control_B,!sentido);
  delay(tempo);

}
void virarDireita(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_control_A,sentido);
  digitalWrite(MOTOR1_control_B,!sentido);
  digitalWrite(MOTOR2_control_A,sentido);
  digitalWrite(MOTOR2_control_B,!sentido);
  delay(tempo);
}

void virarEsquerdaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_control_A,!sentido);
  digitalWrite(MOTOR1_control_B,sentido);
  digitalWrite(MOTOR2_control_A,!sentido);
  digitalWrite(MOTOR2_control_B,sentido);
  delay(tempo);

}
void virarDireitaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_control_A,!sentido);
  digitalWrite(MOTOR1_control_B,sentido);
  digitalWrite(MOTOR2_control_A,!sentido);
  digitalWrite(MOTOR2_control_B,sentido);
  delay(tempo);
}


