#define MOTOR1_P 12
#define MOTOR1_N 11
#define MOTOR1_PWM 5
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 6

#define FRENTE 1
#define RE 0
#define LUZRGB 9
#define DIREITA 1
#define ESQUERDA 0

void setup() {
  pinMode(MOTOR1_P,OUTPUT);
  pinMode(MOTOR2_P,OUTPUT);
  pinMode(MOTOR1_N,OUTPUT);
  pinMode(MOTOR2_N,OUTPUT);
}

void girar(int intensidade,int direcao)
{
  if(direcao==DIREITA) {
    
  
  digitalWrite(MOTOR1_P,FRENTE);
  digitalWrite(MOTOR1_N,!FRENTE);
  digitalWrite(MOTOR2_P,RE);
  digitalWrite(MOTOR2_N,!RE);
  analogWrite(MOTOR1_PWM, intensidade * 50);
  analogWrite(MOTOR2_PWM, intensidade * 50);
  delay(1000);
  
  }
  else if(direcao==ESQUERDA)
{
    
  
  digitalWrite(MOTOR1_P,RE);
  digitalWrite(MOTOR1_N,!RE);
  digitalWrite(MOTOR2_P,FRENTE);
  digitalWrite(MOTOR2_N,!FRENTE);
  analogWrite(MOTOR1_PWM, intensidade * 50);
  analogWrite(MOTOR2_PWM, intensidade * 50);
  delay(1000);
  
  }
}

void piscar(int vezes) {
  for(int x=0;x<vezes;x++) {
    digitalWrite(LUZRGB, 1);
    delay(40);
    digitalWrite(LUZRGB, 0);
  }

}  
void andar(int intensidade, int sentido) {
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  analogWrite(MOTOR1_PWM, intensidade * 50);
  analogWrite(MOTOR2_PWM, intensidade * 50);

}
void parar() {
  analogWrite(MOTOR1_PWM,0);
  analogWrite(MOTOR2_PWM,0);
}

void loop() {
 
  andar(5,FRENTE);
  while (analogRead(1) <= 20) {
    parar();
    girar(5,DIREITA);
    
  }
}







