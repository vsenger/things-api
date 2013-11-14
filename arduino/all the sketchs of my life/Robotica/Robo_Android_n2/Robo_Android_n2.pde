#include <Servo.h> 
#include <LiquidCrystal.h>
#include <MeetAndroid.h>

MeetAndroid meetAndroid;
#define MOTOR1_P 13
#define MOTOR1_N 12
#define MOTOR1_PWM 5
#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 3
#define PING_PIN 1
#define LED_RED 9
#define LED_BLUE 17
#define LED_GREEN 18

int SensorTemperatura = 1 ;
int LDR = 5;
int bussola=0;
int state=1;
float acelerometro[3];

int contador=0;
//LiquidCrystal lcd(14, 3, 16, 17, 18, 19);

long DistanciaEsquerda;
long DistanciaDireita;
long distancia;
Servo servoV;
Servo servoH;
int modo = 1;
int sentido;


void setup() {
  Serial.begin(115200);
  servoH.attach(10);
  servoV.attach(11);
  meetAndroid.registerFunction(dedoDuro, 'A');  
  meetAndroid.registerFunction(acelera, 'C'); 
  meetAndroid.registerFunction(encontraNorte, 'B'); 
  meetAndroid.registerFunction(andar, 'W');  
  meetAndroid.registerFunction(leds, 'L');
  meetAndroid.registerFunction(mudarModo, 'M');

  /* set up the LCD's number of rows and columns: 
   lcd.begin(16, 2);
   lcd.setCursor(0, 0);
   lcd.print("Aguardando");*/

  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  testeLeds();  
  andarFrente(2000,5);
  parar();
  andarTraz(2000,5);
  parar();
  girarNoEixo(2000,5,0);
  parar();
  girarNoEixo(2000,5,1);
  parar();

}

void loop() {
  //meetAndroid.receive(); 
  //delay(50);
  //meetAndroid.send(analogRead(1));
  //testeLeds();
} 
void leds(byte flag, byte numOfValues) {
  int length = meetAndroid.stringLength();
  char comando[length];
  meetAndroid.getString(comando);

  if(comando[0]=='R') {
    pinMode(LED_RED,INPUT);
    int ligado = digitalRead(LED_RED);
    pinMode(LED_RED,OUTPUT);
    digitalWrite(LED_RED,!ligado);

  }
  else if(comando[0]=='G') {
    pinMode(LED_GREEN,INPUT);
    int ligado = digitalRead(LED_GREEN);
    pinMode(LED_GREEN,OUTPUT);
    digitalWrite(LED_GREEN,!ligado);
  }
  else if(comando[0]=='B') {
    pinMode(LED_BLUE,INPUT);
    int ligado = digitalRead(LED_BLUE);
    pinMode(LED_BLUE,OUTPUT);
    digitalWrite(LED_BLUE,!ligado);
  }

}
void testeLeds() {
  digitalWrite(LED_RED, HIGH);
  delay(200);
  digitalWrite(LED_RED, LOW);
  delay(200);
  digitalWrite(LED_BLUE, HIGH);
  delay(200);
  digitalWrite(LED_BLUE, LOW);
  delay(200);
  digitalWrite(LED_GREEN, HIGH);
  delay(200);
  digitalWrite(LED_GREEN, LOW);
  delay(200);
}
void mudarModo(byte flag, byte numOfValues) {
  int length = meetAndroid.stringLength();

  // define an array with the appropriate size which will store the string
  char comando[length];

  // tell MeetAndroid to put the string into your prepared array
  meetAndroid.getString(comando);
  char c[1];
  c[0]=comando[0];
  modo = atoi(c);
  testeLeds();
}

void acelera(byte flag, byte numOfValues) {
  meetAndroid.getFloatValues(acelerometro);
  if(modo==4) {
    servoV.write(map(acelerometro[0],-10,10,0,180));
    servoH.write(map(acelerometro[1],-10,10,0,180));

    if(acelerometro[0]>-3 && acelerometro[0]<3) {

      if(acelerometro[1]<3) {
        andarTraz(200,map(acelerometro[1],-3,-10,1,5));    
      }
      else if(acelerometro[1]>3) {
        andarFrente(200,map(acelerometro[1],3,10,1,5));
      }  
      else if(acelerometro[1]>-3 && acelerometro[1]<3) {
        parar();
      }
    }
    else if(acelerometro[0]<-3) {  //para esquerda
      if(acelerometro[1]>-3 && acelerometro[1]<3) {    //esquerda no eixo
        girarNoEixo(200, map(acelerometro[0],-3,-10,1,5),1);
      }
      else {
        virarEsquerda(200,2);
      } 
    }
    else if(acelerometro[0]>3) { //para direita
      if(acelerometro[1]>-3 && acelerometro[1]<3) {    //direita no eixo
        girarNoEixo(200, map(acelerometro[0],-3,-10,1,5),0);
      }
      else {
        virarDireita(200,2);
      } 
    }
  }
  else if(modo==6){
    servoV.write(map(acelerometro[0],-10,10,0,180));
    servoH.write(map(acelerometro[1],-10,10,0,180));
    delay(200);
  }
}



void andar(byte flag, byte numOfValues) {
  int length = meetAndroid.stringLength();
  char comando[length];
  meetAndroid.getString(comando);
  if(modo!=3) return;

  if(comando[0]=='F') {
    dance();
    parar();
  }
  else if(comando[0]=='R') {
    andarTraz(1000,3);
    parar();
  }
  else if(comando[0]=='G') {
    girarNoEixo(1000,3,0);
    parar();
  }
  else if(comando[0]=='D') {
    dance();
  }
}

void dedoDuro(byte flag, byte numOfValues) {
  state = meetAndroid.getInt();
  if(modo!=1) return;

  if(state==0) {
    for(int x=0;x<3;x++) {
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);      
      parar();
      testeLeds();
    }
  }
}

void encontraNorte(byte flag, byte numOfValues) {
  bussola =  meetAndroid.getInt();
  if(modo!=2) return;
  if(bussola>180) analogWrite(LED_RED, map(bussola, 180, 360, 0,255));
  else analogWrite(LED_RED, map(bussola, 180, 0, 0,255));
  if(bussola>350 && bussola<360) {
    girarNoEixo(80,1,1);
    parar();
  }
  else if(bussola>180 && bussola<350) {
    girarNoEixo(200,1,1);
    parar();
  }
  else if(bussola>15 && bussola<180) {
    girarNoEixo(200,1,0);
    parar();  
  }
  else if(bussola>3 && bussola<15) {
    girarNoEixo(80,1,0);
    parar(); 
  }
  else if(bussola>0 && bussola<3) {
    parar();
  }
}

/*void limpaComando() {
 for(int x=0;x<15;x++) {
 comando[x]=' ';
 }
 }*/

/*
1.5'                   3.2'
 C              E       Am 
 Fa�o c�digo como c�o;Maldito bug, que n�o encontro n�o;
 5.5'                    8' com breque
 Vem o prazo e o chef�o, o redeploy n�o da certo n�o;
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
 Se voc� come�a de novo, um bug novo, um bug novo
 32'                         34'          35'
 Estou implorando socorro, pro bug novo, pro bug novo
 36'
 G           F
 J� estou penando aqui
 38'                                41'
 C                  E               Am
 Fa�o o download de um jar grand�o, mas tamb�m n�o           42'
 funciona n�o
 43'                        45'                     48'
 Chamo o arquiteto bacan�o, mas no fundo � um man�z�o
 49.5'
 G    F C
 Olha o budget, ce ta gastando, ce gastando
 Olha o prazo, ce ta furando, ce ta furando
 E                   Am
 N�o me sinto up-to-date, eu estou � �deprecate�
 */
int led_status=0;
void changeServo(int a) {

  digitalWrite(LED_RED, led_status);
  led_status = led_status==0 ? 1 : 0;
}

void dance() {

  /*1.5'                   3.2'
   C              E       Am 
   Fa�o c�digo como c�o;Maldito bug, que n�o encontro n�o;
   5.5'                    8' com breque
   Vem o prazo e o chef�o, o redeploy n�o da certo n�o;
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
   Se voc� come�a de novo, um bug novo, um bug novo
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
  /* Mas tamb�m n�o funciona n�o */
  andarTraz(2300,4);
  /*Chamo o arquiteto bacanao */
  girarNoEixo(2000,3,1);
  /*Mas no fundo � um manezao */
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
















