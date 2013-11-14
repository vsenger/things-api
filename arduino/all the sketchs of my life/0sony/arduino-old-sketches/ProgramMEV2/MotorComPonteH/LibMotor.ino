int sentido=0;

void setupMotor() {
  pinMode(MOTOR1_A, OUTPUT);
  pinMode(MOTOR1_B, OUTPUT);
  pinMode(MOTOR2_A, OUTPUT);
  pinMode(MOTOR2_B, OUTPUT);
}


void girarNoEixo(int tempo, int intensidade, int sentido1) {
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_A,sentido1);
  digitalWrite(MOTOR1_B,!sentido1);
  digitalWrite(MOTOR2_A,!sentido1);
  digitalWrite(MOTOR2_B,sentido1);
  delay(tempo);
}


void parar() {  
  analogWrite(MOTOR1_PWM,0 );
  analogWrite(MOTOR2_PWM,0);
  digitalWrite(MOTOR1_A,0);
  digitalWrite(MOTOR1_B,0);
  digitalWrite(MOTOR2_A,0);
  digitalWrite(MOTOR2_B,0);

}
void andarFrente(int tempo, int intensidade) {
  sentido = 0;
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_A,sentido);
  digitalWrite(MOTOR1_B,!sentido);
  digitalWrite(MOTOR2_A,sentido);
  digitalWrite(MOTOR2_B,!sentido);
  delay(tempo);
}
void andarTraz(int tempo,int intensidade) {
  sentido = 1;
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_A,sentido);
  digitalWrite(MOTOR1_B,!sentido);
  digitalWrite(MOTOR2_A,sentido);
  digitalWrite(MOTOR2_B,!sentido);
  delay(tempo);

}
void virarEsquerda(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_A,sentido);
  digitalWrite(MOTOR1_B,!sentido);
  digitalWrite(MOTOR2_A,sentido);
  digitalWrite(MOTOR2_B,!sentido);
  delay(tempo);

}
void virarDireita(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_A,sentido);
  digitalWrite(MOTOR1_B,!sentido);
  digitalWrite(MOTOR2_A,sentido);
  digitalWrite(MOTOR2_B,!sentido);
  delay(tempo);
}

void virarEsquerdaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_A,!sentido);
  digitalWrite(MOTOR1_B,sentido);
  digitalWrite(MOTOR2_A,!sentido);
  digitalWrite(MOTOR2_B,sentido);
  delay(tempo);

}
void virarDireitaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_A,!sentido);
  digitalWrite(MOTOR1_B,sentido);
  digitalWrite(MOTOR2_A,!sentido);
  digitalWrite(MOTOR2_B,sentido);
  delay(tempo);
}


