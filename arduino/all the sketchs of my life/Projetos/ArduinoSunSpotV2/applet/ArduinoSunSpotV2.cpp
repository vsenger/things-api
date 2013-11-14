#include <Servo.h> 

#define MOTOR1_P 4
#define MOTOR1_N 5
#define MOTOR1_PWM 11

#define MOTOR2_P 6
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define PING_PIN 13
#define SERVO_1 10
#define MODE_BUTTON 19 //5 anal\u00f3gica lida como digital...

#define MODE_DEMO 3
#define MODE_WALK 2
#define MODE_SUNSPOT 1
#define MODE_STOP 0
#include "WProgram.h"
void setup();
void setupEngines();
void loop();
void Demo();
void SunSpot();
void setupSunSpot();
void receiveSunSpot();
int BtoI(int start,int end, volatile boolean bits[]);
void changeServo(int servoPos);
void fullScan();
byte getLongestPosition();
void partialScan();
void Walk();
void processPing();
void girarNoEixo(int tempo, int intensidade, int sentido1);
void readPress();
void parar();
void andarFrente(int tempo, int intensidade);
void andarTraz(int tempo,int intensidade);
void virarEsquerda(int tempo,int intensidade);
void virarDireita(int tempo,int intensidade);
void readPing();
long microsecondsToInches(long microseconds);
long microsecondsToCentimeters(long microseconds);
int estado = 0;
int sentido = 0;
long duration, inches, cm;
int reacao = 0;
int interrupt=0;
int mode=0;
Servo myservo10;  // create servo object to control a servo 


void setup() {
  setupEngines();
  setupSunSpot();
  myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
  Serial.begin(9600);  
}
void setupEngines() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
}  

void loop(){
  if(digitalRead(MODE_BUTTON)==0) {
    while(digitalRead(MODE_BUTTON)==0);
    mode++;
    if(mode==4) mode=0;
  }  
  if(mode==MODE_DEMO) {
    Demo();
  }
  else if(mode==MODE_WALK) {
    Walk();
  } 
  else if(mode==MODE_SUNSPOT) {
    SunSpot();
  } 
  
}



/*
  if(y>0) {
 sentido = 1;
 analogWrite(MOTOR1_PWM,(y == 10 ? 85 : y==20 ? 170 : 255));
 analogWrite(MOTOR2_PWM,(y == 10 ? 85 : y==20 ? 170 : 255));  
 digitalWrite(MOTOR1_P,sentido);
 digitalWrite(MOTOR1_N,!sentido);
 digitalWrite(MOTOR2_P,sentido);
 digitalWrite(MOTOR2_N,!sentido);
 }
 else if(y<0){ //para traz
 sentido = 0;
 analogWrite(MOTOR1_PWM,(y == -10 ? 85 : y==-20 ? 170 : 255));
 analogWrite(MOTOR2_PWM,(y == -10 ? 85 : y==-20 ? 170 : 255));  
 digitalWrite(MOTOR1_P,sentido);
 digitalWrite(MOTOR1_N,!sentido);
 digitalWrite(MOTOR2_P,sentido);
 digitalWrite(MOTOR2_N,!sentido);
 
 }
 */

/*
 
 
 */



/*
1.5'                   3.2'
 C              E       Am 
 Fa\u00e7o c\u00f3digo como c\u00e3o;Maldito bug, que n\u00e3o encontro n\u00e3o;
 5.5'                    8' com breque
 Vem o prazo e o chef\u00e3o, o redeploy n\u00e3o da certo n\u00c3o;
 10'               13'
 G        F  C
 Ooooolha o prazo, ta demorando, ta demorando 
 15'              17'             18'
 Oooolha o teste, to implantando, to implantando
 19'             20.5     23'                   26'
 E               Am       E                    Am
 Estou esperando release, vai ficar muito mais easy
 27.8'                   30'

28'                      30'          31'
 G           F       C
 Se voc\u00ea come\u00e7a de novo, um bug novo, um bug novo
 32'                         34'          35'
 Estou implorando socorro, pro bug novo, pro bug novo
 36'
 G           F
 J\u00e1 estou penando aqui
 38'                                41'
 C                  E               Am
 Fa\u00e7o o download de um jar grand\u00e3o, mas tamb\u00e9m n\u00e3o           42'
 funciona n\u00e3o
 43'                        45'                     48'
 Chamo o arquiteto bacan\u00e3o, mas no fundo \u00e9 um man\u00e9z\u00e3o
 49.5'
 G    F C
 Olha o budget, ce ta gastando, ce gastando
 Olha o prazo, ce ta furando, ce ta furando
 E                   Am
 N\u00e3o me sinto up-to-date, eu estou \u00e9 \u201cdeprecate\u201d
 */


void Demo() {

  /*1.5'                   3.2'
   C              E       Am 
   Fa\u00e7o c\u00f3digo como c\u00e3o;Maldito bug, que n\u00e3o encontro n\u00e3o;
   5.5'                    8' com breque
   Vem o prazo e o chef\u00e3o, o redeploy n\u00e3o da certo n\u00c3o;
   */
  delay(1500);
  andarFrente(1700,4);
  andarTraz(2300,4);
  girarNoEixo(2000,3,1);
  andarTraz(1500,4);
  parar();
  delay(1500);

  /* 10'               13'
   G        F  C
   Ooooolha o prazo, ta demorando, ta demorando 
   */
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);   
  changeServo(0);
  delay(1000);   
  changeServo(90);
  andarFrente(2000,4);
  parar();

  /* 15'              17'             18'
   Oooolha o teste, to implantando, to implantando
   */
  changeServo(180);
  delay(1000);
  changeServo(0);
  delay(1000);    
  changeServo(90);
  delay(1000);    

  /* 19'             20.5     23'                   27'
   E               Am       E                    Am
   Estou esperando release, vai ficar muito mais easy
   */
  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);      
  changeServo(0);
  changeServo(90);


/*28'                      30'          31'
 G           F       C
 Se voc\u00ea come\u00e7a de novo, um bug novo, um bug novo
 32'                         34'          35'
 Estou implorando socorro, pro bug novo, pro bug novo
 36'*/

  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1000);
  andarFrente(2000,3);
  parar();
  
  /* Ja estou penando aqui */
  
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1500);
  
  /* Faco download de um jar grandao */  
  andarFrente(1700,4);
  /* Mas tamb\u00e9m n\u00e3o funciona n\u00e3o */
  andarTraz(2300,4);
  /*Chamo o arquiteto bacanao */
  girarNoEixo(2000,3,1);
  /*Mas no fundo \u00e9 um manezao */
  andarTraz(2000,4);
  
  parar();
  delay(2000);

  /* Olha o budget */
  girarNoEixo(2000,3,0);
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);   
  changeServo(0);
  delay(1000);   
  changeServo(90);
 /* Olha o prazo */
  girarNoEixo(2000,3,1);
  changeServo(180);
  delay(500);
  changeServo(0);
  delay(500);
  changeServo(90);
  delay(1000);   
  
  /* Nao me sinto uptodate, eu estou deprecated */
   girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);      
  changeServo(0);
  changeServo(90);
 

  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1000);
  andarFrente(2000,3);
  parar();
  andarTraz(2000,3);
  parar();
  
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(500);      
  changeServo(90);
  delay(200000000);  

}  



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

#define DIRECAO_FRENTE 0
#define DIRECAO_FRENTE_ESQUERDA   1
#define DIRECAO_FRENTE_DIREITA 2
#define DIRECAO_TRAZ 3
#define DIRECAO_TRAZ_ESQUERDA 4
#define DIRECAO_TRAZ_DIREITA 5
#define DIRECAO_PARADO 6
#define PRESS_ESQUERDA 0
#define PRESS_DIREITA 2

int move_history[128];
int move_count = 0;
int contaFrente= 0;
boolean pressEsquerda = false;
boolean pressDireita = false;
int servoPos =0;
byte distanceArray[18];
byte pscan=0;
byte frontRead=17;
boolean initScan=false;
int longestPos=0;
int distanceLongestPos = 0;
int distanceLongestPosCorrect =  0;

void changeServo(int servoPos) {
  myservo10.write(servoPos);                  // sets the servo position according to the scaled value 
  delay(15);
}

void fullScan() {
  for(byte x=0;x<18;x++){ 
    changeServo(x*10);
    Serial.print("Servo pos:");
    Serial.println(x*10);
    readPing();
    distanceArray[x]=cm;
  }
  changeServo(90);
}  

byte getLongestPosition() {
  byte value = 0;
  int bigValue=0;
  for(byte x=0;x<18;x++) {
    if(distanceArray[x]>bigValue) {
      value=x;
      bigValue=distanceArray[x];
    }
  }
  return value;
}
void partialScan() {
  changeServo(pscan*10);
  readPing();
  distanceArray[pscan]=cm;
  if(++pscan==18) pscan=0;
}  

void Walk() {
 readPing();
 processPing();
 
}

void processPing() {
  if(cm<20) {
    contaFrente=0;
    parar();
    int contaGiro=0;
    while(cm<20 && contaGiro++<10) {
      girarNoEixo(random(200,500),3,random(0,1));
      readPing();
    }
    if(cm<20) { 
      andarTraz(1,3);
      if(random(0,1)) {
        virarDireita(random(500,random(200,400)),random(2,4));
      }
      else {
        virarEsquerda(random(500,random(200,400)),random(2,4));
      }
    }
  }
  else if(cm>20 && cm<45) {
    int cmAnterior=cm;
    //parar();
    /*int tenta=0;
     while(cmAnterior<cm && tenta++<20) {
     girarNoEixo(50, 4, 1);
     cmAnterior=cm;
     readPing();
     }*/
    if(random(0,1)) {
      virarDireita(random(50,120),random(2,4));
      int cmAnterior=cm;
      readPing();
      if(cm>cmAnterior) {
        while(cm>cmAnterior) {
          virarDireita(50,2);
          cmAnterior=cm;
          readPing();
        }
      }

    }
    else {
      virarEsquerda(random(50,120),random(2,4));
      int cmAnterior=cm;
      if(cm>cmAnterior) {
        while(cm>cmAnterior) {
          virarEsquerda(50,2);
          cmAnterior=cm;
          readPing();
        }
      }

    }

  }
  else {
    contaFrente++;    
    andarFrente(50,cm<100 ?2 : cm>100 && cm<150 ? 3 : 4);
  }
  if(contaFrente>30) {
    contaFrente=0;
    andarTraz(random(150,450),3);
    if(random(0,1)) {
      virarDireita(random(150,random(200,400)),random(2,4));
    }
    else {
      virarEsquerda(random(150,random(200,400)),random(2,4));
    }

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
void readPress() {
  pressEsquerda = !analogRead(PRESS_ESQUERDA)==0;
  pressDireita = !analogRead(PRESS_DIREITA)==0;

}

void parar() {  
  analogWrite(MOTOR1_PWM,0 );
  analogWrite(MOTOR2_PWM,0);
}

void andarFrente(int tempo, int intensidade) {
  sentido = 0;
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);
}
void andarTraz(int tempo,int intensidade) {
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
  analogWrite(MOTOR2_PWM,50*intensidade);
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


void readPing()
{
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  duration = pulseIn(PING_PIN, HIGH);
  inches = microsecondsToInches(duration);
  cm = microsecondsToCentimeters(duration);
  Serial.println(cm);
}

long microsecondsToInches(long microseconds)
{
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds)
{
  return microseconds / 29 / 2;
}

int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

