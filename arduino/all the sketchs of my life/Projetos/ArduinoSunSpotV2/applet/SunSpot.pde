#define SUN_SPOT 8
#define SUN_SPOT_ACK 12
#define SUN_SPOT_INTERRUPT 0 //PORTA digital 2

volatile int data;
volatile boolean bit_array[16];
volatile int contador;
int x;
int y;

void SunSpot() {
  if(x==0 && y==0) {
    analogWrite(MOTOR1_PWM,0);
    analogWrite(MOTOR2_PWM,0);  
  }
  if(y>0) sentido = 0; //Para FRENTE  
  else if(y<0) sentido = 1;// Para Traz
  //Configurando o sentido do trem.. quero dizer do robot
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);

  if(y!=0 && x!=0) { //Curva frente ou traz...
    int coeficientePotencia = map(x<0 ? x*-1 : x,0,30,0,255);
    analogWrite(MOTOR1_PWM, x< 0 ? coeficientePotencia : coeficientePotencia/2);
    analogWrite(MOTOR2_PWM, x< 0 ? coeficientePotencia/2 : coeficientePotencia);
  }
  else { //reto
    analogWrite(MOTOR1_PWM,map(y<0 ? y*-1 : y,0,30,0,255));
    analogWrite(MOTOR2_PWM,map(y<0 ? y*-1 : y,0,30,0,255));
  }      
  //Girando no eixo
  if(y==0 && x!=0) {
    sentido = x<0;
    digitalWrite(MOTOR1_P,sentido);
    digitalWrite(MOTOR2_P,!sentido);
    digitalWrite(MOTOR1_N,!sentido);
    digitalWrite(MOTOR2_N,sentido);
    if(x>0) { 
      Serial.print("Girando x>0");
      Serial.println(map(x,0,30,40,255));
      analogWrite(MOTOR1_PWM, map(x,0,30,40,255) );      
      analogWrite(MOTOR2_PWM , map(x,0,30,40,255) ); 
    }
    else {
      Serial.print("Girando <0");
      Serial.println(map(x>0 ? x : x*-1,0,30,40,255));

      analogWrite(MOTOR2_PWM, map(x*-1,0,30,40,255) );
      analogWrite(MOTOR1_PWM, map(x*-1,0,30,40,255) );       
    }
  }
  Serial.print("x = ");
  Serial.println(x);
  Serial.print("y = ");
  Serial.println(y);

  delay(200);  
}
void setupSunSpot() {
  attachInterrupt(0, receiveSunSpot, RISING);
  pinMode(SUN_SPOT,INPUT);
  pinMode(SUN_SPOT_ACK,OUTPUT);
}

void receiveSunSpot() {
  digitalWrite(SUN_SPOT_ACK,LOW);
  bit_array[contador++]=digitalRead(SUN_SPOT);
  digitalWrite(SUN_SPOT_ACK,HIGH);
  if(contador==16) {
    Serial.println("Chegou Novos 2 bytes");
    contador=0;
    x = BtoI(0,7,bit_array);

    y = BtoI(8,15,bit_array);

    for(int clean=0;clean<16;clean++) bit_array[clean]=false;

  }
}

int BtoI(int start,int end, volatile boolean bits[]){
  boolean negative=bits[start];
  start++;
  unsigned long integer=0;
  unsigned long mask=1;
  int r;
  for (int i = end; i >= start; i--) {
    if(negative) {
      if (!bits[i]) integer |= mask;
    }
    else {
      if (bits[i]) integer |= mask;
    }
    mask = mask << 1;
  }
  r = (int) integer;
  if(negative) r= ~r;
  return r;
}
