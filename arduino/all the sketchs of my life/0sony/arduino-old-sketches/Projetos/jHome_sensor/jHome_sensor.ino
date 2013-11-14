#include <LiquidCrystal_I2C.h>
#include <Wire.h> 
#include <ElectronKernel.h>

#include "hrmi_funcs.h"
#define HRMI_HOST_BAUDRATE 9600
#define HRMI_I2C_ADDR      127
#define MAX_IN_BUFFSIZE 16

LiquidCrystal_I2C lcd(0x27,16,2);

#define RELAY1 7
#define RELAY2 8

#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 

#define BLUE  9
#define GREEN 6
#define RED   3

#define TEMPERATURE 2
#define LIGHT 3
#define PING_PIN 5

#define LED 13
char serInStr[MAX_IN_BUFFSIZE];   // Serial input string array
int numEntries = 0;               // Number of HR values to request
int numRspBytes;                  // Number of Response bytes to read
byte i2cRspArray[34];	          // I2C response array, sized to read 32 HR values
byte hrmi_addr = HRMI_I2C_ADDR;   // I2C address to use

long duration, inches, cm;
boolean connected = false;
long handTimer=0;

char rgb='R';
void setup()
{
  hrmi_open();

  Kernel.setup(INTERRUPTION);

  Kernel.registerMode("M1", modo_default_setup);
  Kernel.registerTask(modo_default);
  Kernel.registerMode("MH", modo_heart_monitor_setup);
  Kernel.registerTask(modo_heart_monitor);

  Kernel.registerMode("M2", modo_change_rgb_red_setup);
  Kernel.registerTask(modo_change_rgb);
  Kernel.registerMode("M3", modo_change_rgb_green_setup);
  Kernel.registerTask(modo_change_rgb);
  Kernel.registerMode("M4", modo_change_rgb_blue_setup);
  Kernel.registerTask(modo_change_rgb);

  Serial.begin(115200);
  pinMode(LED, OUTPUT);
  //pinMode(RELAY1, OUTPUT);
  //pinMode(RELAY2, OUTPUT);  
  lcd.init();
  lcd.setBacklight(0);
  lcd.setCursor(0, 0);
  lcd.print("jHome v1.0");
  lcd.setCursor(0, 1);
  lcd.print("by Globalcode");
  delay(500);
}


void loop()
{
  Kernel.loop();
} 
void modo_heart_monitor_setup() {
}  
void modo_heart_monitor() {
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {  
    int val = map(i2cRspArray[2], 80,130,0, 150);
    //for(int x=0;x<34;x++) {
    Serial.print(i2cRspArray[2],DEC);
    //}
    Serial.println(" ");
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Heart Monitor");
    lcd.setCursor(0, 1);
    //char c*[];
    //toa(i2cRspArray[2],&c);
    String n = String(i2cRspArray[2]);

    lcd.print(i2cRspArray[2],DEC);

  }
  if (++numEntries > 30) numEntries = 0;
  Kernel.wait(300);
}

void modo_change_rgb_red_setup() {
  lcd.setCursor(0, 1);
  lcd.print("RED");
  rgb='R';  
}
void modo_change_rgb_green_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("GREEN");
  rgb='G';  
}
void modo_change_rgb_blue_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("BLUE");
  rgb='B';  
}
void modo_change_rgb() {
  lcd.clear();
  lcd.setCursor(0, 0);
  readPing();
  int port = 0;
  if(rgb=='R') {
    port = RED;
    lcd.print("RGB Red");
  }
  else if(rgb=='G') {
    port = GREEN;  
    lcd.print("RGB Green");
  }
  else if(rgb=='B') {
    port = BLUE;  
    lcd.print("RGB Blue");
  }
  if(cm<10) {
    delay(200);
    readPing();
    if(cm<10){  
      blink(port, 3, 100);
      while(cm<50) {
        int cmok=cm;
        cmok = cmok>25 ? 25 : cmok;
        cmok = cmok<5 ? 5 : cmok;

        int amount = map(cmok,5,25,255,0);
        lcd.setCursor(0, 1);
        lcd.print(cm);
        lcd.setCursor(4, 1);
        lcd.print(amount);
        analogWrite(port, amount);
        delay(50);
        readPing();
      }
    }
  }
  delay(150);
}
void modo_default_setup()  { 
  lcd.setBacklight(15);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("jHome v1.0");
}
void modo_default() {

  if(Serial.available()) receiveCommand();
  if(millis()-handTimer>1000) {
    readPing();
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("jHome v1.0");
    lcd.setCursor(0, 1);
    lcd.print(cm);
    lcd.setCursor(4, 1);
    lcd.print(readTemperatureCelsius());
    handTimer=millis();    
  }

}

float readTemperatureCelsius() {
  float temperatura = (analogRead(TEMPERATURE) * 0.00488);  // 5V / 1023 = 0.00488 (precisão do A/D)
  temperatura = temperatura * 100; //Converte milivolts para graus celcius, lembrando que a cada 10mV equivalem a 1 grau celcius
  return temperatura;
}

void handShake() {
  //Serial.print("ID 0|bluetooth prototype device|6|A|relay|B|dimmer|C|RGB|E|temperature|F|distance|S|allsensors");
  Serial.print("ID 1|bluetooth board sensor|6|H|heartbeat|C|temperature-bluetooth|D|light-bluetooth|S|allsensors|R|RGB-bluetooth|F|distance");
  Serial.flush();

  long m = millis();
  while(Serial.available()<1) {
    //waiting for data
    if(millis()-m>2000) {
      //timeout
      return;
    }
  }
  if(Serial.available()>0) { 
    char c=Serial.read();
    digitalWrite(LED, true);      
    connected= true;
  }

}
void shutdown() {
  digitalWrite(LED, false); 
  connected=false;  
}

void receiveCommand() {
  char comando[16];
  int counter=0;
  while(Serial.available()>0) // loop through all but the last
  {
    char c = Serial.read(); // receive byte as a character
    delay(5);
    comando[counter++]=c;
  }
  if(comando[0]=='j' || comando[0]=='J') handShake();
  //if(comando[0]=='A') relayCommand(comando[1], 'A');
  //if(comando[0]=='B') relayCommand(comando[1], 'B');
  if(comando[0]=='R') RGB(comando);
  if(comando[0]=='D') readLight();
  if(comando[0]=='H') readHeartBeat();
  if(comando[0]=='F') readDistance();
  if(comando[0]=='C') readTemperature();
  if(comando[0]=='S') readAllSensors();  
  if(comando[0]=='X') shutdown();  
}
void readHeartBeat() {
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {  
    int val = map(i2cRspArray[2], 80,130,0, 150);
    //for(int x=0;x<34;x++) {
    Serial.print(i2cRspArray[2],DEC);
    Serial.flush();
  }
  if (++numEntries > 30) numEntries = 0;
}

void RGB(char* comando) {
  int port = 0;
  if(comando[1]=='R') port = RED;
  if(comando[1]=='G') port = GREEN;  
  if(comando[1]=='B') port = BLUE;  
  int amount = map(atoi(&comando[2]),0,9,0,255);
  analogWrite(port, amount);
}


void readLight() {
  String toSend = String(analogRead(LIGHT));
  sendToPC(toSend);  
}

void readTemperature() {
  String toSend = String(analogRead(TEMPERATURE));  
  sendToPC(toSend);
}

void readAllSensors() {
  //readLight();
  //Serial.print("|");
  //delay(2);
  readTemperature();
  Serial.print("|");
  readLight();
  Serial.print("|");

  //Serial.print("|");
  //String toSend3 = String(cm);  
  //Serial.print(cm);
  //Serial.flush();
}


void readDistance() {
  readPing();
  sendToPC(String(cm));
}

void sendToPC(String dado) {
  Serial.print(dado);
  Serial.flush();
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
}

long microsecondsToInches(long microseconds)
{
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds)
{
  return microseconds / 29 / 2;
}











void blink(int port, int n, int d) {
  pinMode(port, OUTPUT);
  for(int x=0;x<n;x++) {
    digitalWrite(port, HIGH);
    delay(d);
    digitalWrite(port, LOW);
    delay(d);
  }
}




