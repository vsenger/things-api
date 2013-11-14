/*
  AC Light Control
 
 Ryan McLaughlin <ryanjmclaughlin@gmail.com>
 
 Thanks to http://www.andrewkilpatrick.org/blog/?page_id=445
 and http://www.hoelscher-hi.de/hendrik/english/dimmer.htm
 
 */
#include <Wire.h>

#include <avr/interrupt.h>
#include <avr/io.h>

#define PING_PIN 4

#define INIT_TIMER_COUNT 6
#define RESET_TIMER2 TCNT2 = INIT_TIMER_COUNT

#define CW     HIGH
#define CCW    LOW

// One and two phases
// 60000ms / 48steps = 1250ms/step
#define TRACK  1250

// Half steps
// 60000ms / 96steps = 625
//#define TRACK 625




int led13 = HIGH;
long counter = 0;
int stepStack = 0;


int AC_pin = 7;  // Output to Opto Triac
int olddim;
long dim = 120;    // Dimming level (0-128)  0 = on, 128 = 0ff
long duration, inches, cm, oldCm;
long MAX_DISTANCE = 50;
volatile boolean wasInt = false;
unsigned long time, timeWaiting, DEFAULT_CONFIG_PERIOD=2000;

void setup()			  // run once, when the sketch starts
{
  pinMode(AC_pin, OUTPUT);	// Set the light control pin as output
  Serial.begin(9600);
  attachInterrupt(0, light, RISING);  // Attach an Interupt to pin2 (interupt 0) for Zero Cross Detection
  Wire.begin(66);		  
  Wire.onReceive(receiveEvent);
  readPing(true);
  MAX_DISTANCE=cm;

  //Timer2 Settings: Timer Prescaler /64,
  TCCR2A |= (1<<CS22);
  TCCR2A &= ~((1<<CS21) | (1<<CS20));
  // Use normal mode
  TCCR2A &= ~((1<<WGM21) | (1<<WGM20));
  // Use internal clock - external clock not used in Arduino
  ASSR |= (0<<AS2);
  //Timer2 Overflow Interrupt Enable
  TIMSK2 |= (1<<TOIE2) | (0<<OCIE2A);
  RESET_TIMER2;
  sei();

}
void receiveEvent(int howMany)
{ 
  byte comando[16];
  int counter=0;
  while(1 < Wire.available())
  {
    byte c = Wire.receive(); 
    comando[counter++]=c;
    Serial.print(c);	   
  }
  int x = Wire.receive();  
  Serial.println(x);	   

  Serial.println("Evento");
  Serial.println(howMany);

  Serial.println("Com");
  Serial.println(comando[1], DEC);
  dim = map(comando[1], 0,9,1,120);
  Serial.println("Dim");
  Serial.println(dim);
  
} 

void light()  // function to be fired at the zero crossing to dim the light
{
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
  readPing(false);
  if(cm<10) {
    delay(200);
    readPing(false);
    if(cm<10){  
      pisca(3);
      while(cm<MAX_DISTANCE) {
        long cmn = cm > 30 ? 30 : cm;
        dim = map(cmn,1,30,10,125);
        //Wire.send(dim);
        delay(20);
        readPing(true);

      }
    }
  }
  delay(150);


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






// Aruino runs at 16 Mhz, so we have 1000 Overflows per second...
// 1/ ((16000000 / 64) / 256) = 1 / 1000
ISR(TIMER2_OVF_vect) {
  RESET_TIMER2;
  counter++;
  if(!(counter%TRACK))
  {
    // enqueue step message
  }
};




