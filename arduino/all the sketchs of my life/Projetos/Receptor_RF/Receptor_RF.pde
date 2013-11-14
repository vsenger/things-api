#include <VirtualWire.h>

#define BOTAO_PORTAO 9
#define BOTAO_INTERFONE 8
#define LED 4
int valor_anterior = -1;
int erro =0;
int recebidos = 0;

uint8_t buf[VW_MAX_MESSAGE_LEN];
uint8_t buflen = VW_MAX_MESSAGE_LEN;


void setup()
{
  Serial.begin(9600);    // Debugging only
  //Serial.println("setup");
  pinMode(LED, OUTPUT);
  pinMode(BOTAO_PORTAO, OUTPUT);
  pinMode(BOTAO_INTERFONE, OUTPUT);
  pinMode(12, INPUT);
  vw_set_rx_pin (12);
  vw_setup(2000);     // Bits per sec
  vw_rx_start(); 
}

void loop()
{
  digitalWrite(BOTAO_PORTAO, true); 
  digitalWrite(BOTAO_INTERFONE, false); 
  //Serial.println("Esperando...");
  if (vw_get_message(buf, &buflen)) // Non-blocking
  {
    digitalWrite(BOTAO_PORTAO, false); 
    digitalWrite(BOTAO_INTERFONE, true); 
    digitalWrite(LED, true);
    Serial.println("Chegou");
    delay(1000);
    switch (buf[0]){
    case '0':
      break;
    case '1':                            
      break;
    case '2':
      break;
    case '9':
      break;
    }
    digitalWrite(BOTAO_PORTAO, true);
    digitalWrite(BOTAO_INTERFONE, false);
    digitalWrite(LED, false);
    recebidos++;
  }
}



