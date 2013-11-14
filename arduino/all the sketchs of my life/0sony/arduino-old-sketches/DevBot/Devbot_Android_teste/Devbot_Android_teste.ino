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
  //meetAndroid.setSerial(&Serial);
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
  //if(modo==SEGUIR_LINHA) seguirLinha2();
  if(modo==IDLE) modoZero();
  
}
int direita;
int centro;
int esquerda;




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


