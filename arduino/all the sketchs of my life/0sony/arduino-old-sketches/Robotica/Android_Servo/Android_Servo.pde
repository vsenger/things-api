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

  meetAndroid.registerFunction(acelera, 'C'); 

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
  //testeLeds();  
  servoH.write(10);
  delay(1000);
  servoH.write(170);
  delay(1000);
  servoH.write(90);
  delay(1000);
  servoV.write(10);
  delay(1000);
  servoV.write(170);
  delay(1000);
  servoV.write(90);
  delay(1000);
  
}

void loop() {
  meetAndroid.receive(); 
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

void acelera(byte flag, byte numOfValues) {
  meetAndroid.getFloatValues(acelerometro);
  servoV.write(map(acelerometro[0],-10,10,0,180));
  servoH.write(map(acelerometro[1],-10,10,0,180));
  delay(5);
}




