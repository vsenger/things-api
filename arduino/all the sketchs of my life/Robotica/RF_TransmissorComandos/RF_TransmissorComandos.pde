#include <VirtualWire.h>

void setup()
{
  Serial.begin(9600);
  pinMode(3, OUTPUT);
  vw_set_tx_pin(3);   
  vw_setup(2000); 
}

void loop()
{
  char c[3];
  int counter = 0;
  for(int x=0;x<300;x++) {
    itoa(x,c,10);
    Serial.println(c);   
    vw_send((uint8_t *) c,strlen(c));
    delay(500);
  }
}

