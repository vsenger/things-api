/*
 * IRremote: IRrecvDemo - demonstrates receiving IR codes with IRrecv
 * An IR detector/demodulator must be connected to the input RECV_PIN.
 * Version 0.1 July, 2009
 * Copyright 2009 Ken Shirriff
 * http://arcfn.com
 */

#include <IRremote.h>

int RECV_PIN = 11;

IRrecv irrecv(RECV_PIN);
unsigned long botao_1 = 2640478335;
unsigned long botao_4 = 2640453855;
unsigned long botao_2 = 2640462015;

unsigned long codigo;//variável que recebe o código

decode_results results;

void setup()
{
  Serial.begin(115200);
  irrecv.enableIRIn(); // Start the receiver
}

void loop() {
  if (irrecv.decode(&results)) {
    codigo = results.value;
    Serial.println(results.value, DEC);
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
}
