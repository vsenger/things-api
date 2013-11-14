#include <Servo.h> 
#include <avr/pgmspace.h>
#include <MeetAndroid.h>

MeetAndroid meetAndroid;
boolean DEBUG_LIGADO = false;

int modo=1;
boolean calibrado = false;
int bussola=0;
int state=1;
float acelerometro[3];

int contador=0;

Servo servoV;
Servo servoH;

#define IDLE 0
#define DEDO_DURO 1
#define ENCONTRAR_NORTE 2
#define ANDAR 3
#define ACELEROMETRO 4
#define SEGUIR_LINHA 5

void setup(){
  setupMotor();
  delay(500);
  Serial.begin(115200);
  meetAndroid.setSerial(&Serial);
  meetAndroid.registerFunction(dedoDuro, 'A');  
  meetAndroid.registerFunction(encontraNorte, 'B'); 
  meetAndroid.registerFunction(acelera, 'C'); 
  meetAndroid.registerFunction(mudarModo, 'M');  
  meetAndroid.registerFunction(andar, 'W'); 
  modo=IDLE;
}

void loop(){
  meetAndroid.receive();  
  delay(50);
  if(modo==SEGUIR_LINHA) seguirLinha2();
  if(modo==IDLE) modoZero();
  
}
int direita;
int centro;
int esquerda;

void ler() {
  direita = RCtime(5);
  centro = RCtime(9);
  esquerda = RCtime(2);
}  
void seguirLinha2() {
  andarFrente(0,2);
  ler();
  while(esquerda<400 && direita<400 && centro>400) {
     setSpeeds(60,60);
     ler();
  }
  while(esquerda>400 && direita<400 && centro<400) { //esquerda na faixa
        parar();
        girarNoEixo(200, 1, 0);
        parar();
        ler();
  }
  while(direita>400 && esquerda<400  && centro<400) {
        parar();
        girarNoEixo(200, 1, 1);
        parar();
        ler();
  }
  while(direita<400 && esquerda<400  && centro<400) {
        parar();
        girarNoEixo(200, 1, 1);
        parar();
        ler();
  }
  while(direita>400 && esquerda>400  && centro>400) {
        parar();
        girarNoEixo(200, 1, 1);
        parar();
        ler();
  }
  
  
}

long RCtime(int sensPin){
   long result = 0;
   pinMode(sensPin, OUTPUT);       // make pin OUTPUT
   digitalWrite(sensPin, HIGH);    // make pin HIGH to discharge capacitor - study the schematic
   delay(1);                       // wait a  ms to make sure cap is discharged

   pinMode(sensPin, INPUT);        // turn pin into an input and time till pin goes low
   digitalWrite(sensPin, LOW);     // turn pullups off - or it won't work
   while(digitalRead(sensPin)){    // wait for pin to go low
      result++;
   }

   return result;                   // report results   
}  
void modoZero() {
  digitalWrite(13, HIGH);
  delay(1000);
  digitalWrite(13, LOW);
  delay(1000);
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
  calibrado = false; //para recalibrar quando ligar seguidor de linha novamente
}


