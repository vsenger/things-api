/*
 * IRremote: IRsendDemo - demonstrates sending IR codes with IRsend
 * An IR LED must be connected to Arduino PWM pin 3.
 * Version 0.1 July, 2009
 * Copyright 2009 Ken Shirriff
 * http://arcfn.com
 */

#include <IRremote.h>
 
#define PanasonicAddress      0x4004     // Panasonic address (Pre data) 
#define PanasonicPower        0x112005E  // Panasonic Power button
//#define PanasonicPower          0x1120049  // Panasonic Power button

IRsend irsend;

void setup()
{
  Serial.begin(9600);
  unsigned int menu[100]={-3500,3550,-1650,500,-350,500,-1200,500,-350,500,-400,500,-350,500,-350,500,-350,500,-350,550,-350,500,-350,500,-350,500,-350,500,-400,500,-1200,500,-350,500,-350,550,-350,500,-350,500,-350,500,-350,500,-400,500,-350,500,-350,500,-1200,500,-400,500,-350,500,-350,500,-1250,500,-350,500,-350,500,-1200,500,-400,500,-350,500,-350,500,-350,500,-400,500,-350,500,-350,500,-350,500,-350,500,-400,500,-1200,500,-350,500,-1250,500,-1200,500,-1250,500,-1200,500,-350,500};
  irsend.sendRaw(menu, 100, 38);
  delay(200);
}



void loop() {

}
