/*
 * IRremote: IRsendDemo - demonstrates sending IR codes with IRsend
 * An IR LED must be connected to Arduino PWM pin 3.
 * Version 0.1 July, 2009
 * Copyright 2009 Ken Shirriff
 * http://arcfn.com
 */

#include <IRremote.h>

IRsend irsend;
unsigned long botao_desligar = 33472575;


void setup()
{
  Serial.begin(115200);
  //irsend.enableIROut(5);
}

void loop() {

    for(int x=0;x<3;x++) {
      irsend.sendNEC(botao_desligar,32);
      delay(100);
    }

}

