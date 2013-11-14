/*
 * IRremote: IRrecvDemo - demonstrates receiving IR codes with IRrecv
 * An IR detector/demodulator must be connected to the input RECV_PIN.
 * Version 0.1 July, 2009
 * Copyright 2009 Ken Shirriff
 * http://arcfn.com
 */
#include "Device.h"


#include <IRremote.h>

int RECV_PIN = 11;

IRrecv irrecv(RECV_PIN);
unsigned long botao_1 = 2640478335;
unsigned long botao_4 = 2640453855;
unsigned long botao_2 = 2640462015;
unsigned long codigo;//variável que recebe o código

decode_results results;
Device homeDevice=Device("central-device");

void setup()
{
    //homeDevice.add("speaker",      DIGITAL, 4);
  homeDevice.add("relay1",       DIGITAL, 8);
  //homeDevice.add("relay2",       DIGITAL, 9);
  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",  ANALOG,  2);

  //homeDevice.add("pwm-aux1",     PWM,   3);
  //h/omeDevice.add("pwm-aux2",     PWM,   5);
  irrecv.enableIRIn(); // Start the receiver
  Serial.begin(115200);

}

void loop() {
  if (irrecv.decode(&results)) {
    codigo = results.value;
    Serial.println(results.value, DEC);
    //Serial.println(results.value, DEC);
    irrecv.resume(); // Receive the next value
  }
  if(codigo==botao_1){
    pinMode(8, OUTPUT);
    Serial.println(codigo);
    digitalWrite(8, HIGH);
    //delay(500);
    //digitalWrite(8, LOW);
    codigo=0;
  }
  else if (codigo==botao_2){
    Serial.println(codigo);
    digitalWrite(8, LOW);
    //delay(500);
    //digitalWrite(8, LOW);
    codigo=0;

  }
  //homeDevice.loop();
}
