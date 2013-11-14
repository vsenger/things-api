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

long distanciaEsquerda;
long distanciaDireita;
long distancia;
Servo servoV;
Servo servoH;
int modo = 1;
int sentido;

void setup() {
  Serial.begin(115200);
  servoH.attach(10);
  servoV.attach(11);
  meetAndroid.setSerial(&Serial);
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

}

void loop() {
  meetAndroid.receive(); 
  delay(50);
  if(modo==6) andarSozinho();
  //meetAndroid.send(analogRead(1));
  //testeLeds();
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


int led_status=0;
void changeServo(int a) {

  digitalWrite(LED_RED, led_status);
  led_status = led_status==0 ? 1 : 0;
}

