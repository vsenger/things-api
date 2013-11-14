/*
  AC Light Control
 
 Ryan McLaughlin <ryanjmclaughlin@gmail.com>
 
 Thanks to http://www.andrewkilpatrick.org/blog/?page_id=445
 and http://www.hoelscher-hi.de/hendrik/english/dimmer.htm
 
 */
#include <Wire.h>

#define PING_PIN 4

int led13 = HIGH;
long counter = 0;
int stepStack = 0;

int AC_pin = 7;  // Output to Opto Triac
int olddim=0;
long duration, inches, cm, oldCm;
long MAX_DISTANCE = 50;

//ISR Vars
volatile boolean wasInt = false;
volatile int dim = 120;    // Dimming level (0-128)  0 = on, 128 = 0ff

void setup()			  // run once, when the sketch starts
{
  pinMode(AC_pin, OUTPUT);	// Set the light control pin as output
  attachInterrupt(0, light, RISING);  // Attach an Interupt to pin2 (interupt 0) for Zero Cross Detection
  Wire.begin(66);		  
  Wire.onReceive(receiveEvent);
  readPing(true);
  MAX_DISTANCE=cm;
  Serial.begin(9600);
  
}

void receiveEvent(int howMany)
{ 
  byte comando[16];
  int counter=0;
  while(1 < Wire.available())
  {
    byte c = Wire.receive(); 
    comando[counter++]=c;
  }
  int x = Wire.receive();  
  int val = comando[1];
  dim = map(val, 0,9,1,125);
} 

void light()  // function to be fired at the zero crossing to dim the light
{
  Serial.println("ok");
  wasInt=true;
  if(dim<125) {
    long dimtime = (60*dim);  // eval the proper pause to fire the triac
    delayMicroseconds(dimtime);  // delay the dim time
    digitalWrite(AC_pin, HIGH);  // fire the Triac
    delayMicroseconds(1);	 // pause briefly to ensure the triac turned on
    digitalWrite(AC_pin, LOW);   // turn off the Triac gate (triac will not turn off until next zero cross)
  }
  else {
    digitalWrite(AC_pin, LOW);   // turn off the Triac gate (triac will not turn off until next zero cross)
  }    
}

void pisca(int re) {
  int dimold = dim;
  for(int x=0;x<re;x++) {
    dim = 30;
    delay(50);
    dim = 120;
    delay(50);
  }
  dim = dimold;
}

void loop()
{ 
  dim=50;
  delay(500);
  dim=100;
  delay(500);
  dim=150;
  delay(500);
  dim=200;
  delay(500);
  /*
  readPing(false);
  if(cm<10) {
    delay(200);
    readPing(false);
    if(cm<10){  
      pisca(3);
      while(cm<MAX_DISTANCE) {
        long cmn = cm > 30 ? 30 : cm;
        dim = map(cmn,1,30,10,125);
        Wire.beginTransmission(60);
        char texto[10];
        itoa(map(dim,10,125,1,9),texto, 1);
        //Wire.send(texto);
        //Wire.endTransmission();
        delay(20);
        readPing(true);

      }
    }
  }
  delay(150);*/
}

void readPing(boolean disableInt)
{
  wasInt=false;

  if(disableInt) noInterrupts();
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  if(wasInt) return;
  delayMicroseconds(2);
  if(wasInt) return;
  digitalWrite(PING_PIN, HIGH);
  if(wasInt) return;
  delayMicroseconds(5);
  if(wasInt) return;
  digitalWrite(PING_PIN, LOW);
  pinMode(PING_PIN, INPUT);
  if(wasInt) return;
  duration = pulseIn(PING_PIN, HIGH);
  if(wasInt) return;
  interrupts();
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

