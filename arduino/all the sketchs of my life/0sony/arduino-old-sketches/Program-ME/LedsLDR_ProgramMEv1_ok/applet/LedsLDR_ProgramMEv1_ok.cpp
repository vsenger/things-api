#define LDR 5
#include "WProgram.h"
void setup();
void loop();
void lerLuz();
void leds();
int led[] = {14,1,2,3,4,5,8,6,13};
int luminosidade;


void setup() {
  for(int x=0;x<9;x++) {
    pinMode(led[x], OUTPUT);
  }
}

void loop() {
  lerLuz();
  leds();
  
}

void lerLuz() {
  luminosidade = analogRead(LDR);
}

void leds() {
    int ledsParaLigar = map(luminosidade, 0, 750, 0, 9);
  for(int x=0;x<ledsParaLigar;x++) {
    digitalWrite(led[x], HIGH);
    delay(100);
  }
  for(int x=0;x<ledsParaLigar;x++) {
    digitalWrite(led[x], LOW);
    delay(100);  
  }
}



int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

