

#include "WProgram.h"
void setup();
void loop();
int luminosidade;

int potenciometro;
void setup() {
  Serial.begin(9600);
  pinMode(5, OUTPUT);
}

void loop() {
  // entrada de informa\u00e7\u00e3o na vari\u00e1vel
  // analogRead(0) representa o sensor de luz do Program-ME
  luminosidade = analogRead(0);
  potenciometro = analogRead(1);
  analogWrite(5, potenciometro / 8); 
  if(luminosidade<500) {
    Serial.println("Esta claro!");
  }
  else {
    Serial.println("Esta escuro!");
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

