  #define P0 0    // Porta Digital 0   -  Chave  
  #define P1 1    // Porta Digital 1   -  Led 2
  #define P2 2    // Porta Digital 2   -  Led 3
  #define P3 3    // Porta Digital 3   -  Led 4  -  PWM  
  #define P4 4    // Porta Digital 4   -  Led 5
  #define P5 5    // Porta Digital 5   -  Led 6  -  PWM
  #define P6 6    // Porta Digital 6   -  Transistor Q3  -  PWM
  #define P7 7    // Porta Digital 7   -  Led 8
  #define P8 8    // Porta Digital 8   -  Led 7
  #define P9 9    // Porta Digital 9   -  Transistor Q2  -  PWM
  #define P10 10  // Porta Digital 10  -  Servo1  -  PWM
  #define P11 11  // Porta Digital 11  -  Servo2  -  PWM
  #define P12 12  // Porta Digital 12  -  Buzzer
  #define P13 13  // Porta Digital 13  -  Led 9
  #define P14 14  // Porta Analogica 0 -  Led 1
  #define P15 15  // Porta Analogica 1 -  JP6 - POT 1
  #define P16 16  // Porta Analogica 2 -  JP7 - POT 2
  #define P17 17  // Porta Analogica 3 -  Transistor Q5
  #define P18 18  // Porta Analogica 4 -  Transistor Q4
  #define P19 19  // Porta Analogica 5 -  LDR
  
  #define AP0 14  // Porta Analogica 0 Usando como digital -  Led 1
  #define AP1 15  // Porta Analogica 1 Usando como digital -  JP6 - POT 1
  #define AP2 16  // Porta Analogica 2 Usando como digital -  JP7 - POT 2
  #define AP3 17  // Porta Analogica 3 Usando como digital -  Transistor Q5
  #define AP4 18  // Porta Analogica 4 Usando como digital -  Transistor Q4
  #define AP5 19  // Porta Analogica 5 Usando como digital -  LDR

  #define A0 0    // Porta Analogica 0 -  Led 1
  #define A1 1    // Porta Analogica 1 -  JP6 - POT 1
  #define A2 2    // Porta Analogica 2 -  JP7 - POT 2
  #define A3 3    // Porta Analogica 3 -  Transistor Q5
  #define A4 4    // Porta Analogica 4 -  Transistor Q4
  #define A5 5    // Porta Analogica 3 -  LDR

  #define LED_1 14
  #define LED_2 1
  #define LED_3 2
  #define LED_4 3
  #define LED_5 4
  #define LED_6 5
  #define LED_7 8
  #define LED_8 7
  #define LED_9 13

  #define Q2 9    //PWM
  #define Q3 6    //PWM
  #define Q4 18
  #define Q5 17

  #define LDR 5
  #define JP6 1
  #define JP7 2
  #define CHAVE 0 
  #define BOTAO 0   
  #define BUZZER  12 
  #define SERVO_1 10
  #define SERVO_2 11

#include "WProgram.h"
void setup();
void loop();
int led[] = { LED_1, LED_2, LED_3, LED_4, LED_5, LED_6, LED_7, LED_8, LED_9 }; // array com a sequencia dos leds
int num_pins = 9;                  // tamanho do array


 /*
    Memsic2125
    
    Read the Memsic 2125 two-axis accelerometer.  Converts the
    pulses output by the 2125 into milli-g's (1/1000 of earth's
    gravity) and prints them over the serial connection to the
    computer.
    
    The circuit:
 	* X output of accelerometer to digital pin 2
 	* Y output of accelerometer to digital pin 3
 	* +V of accelerometer to +5V
 	* GND of accelerometer to ground
   
    http://www.arduino.cc/en/Tutorial/Memsic2125
    
    created 6 Nov 2008
    by David A. Mellis
    modified 30 Jun 2009
    by Tom Igoe

  */

 // these constants won't change:
 const int xPin = 2;		// X output of the accelerometer
 const int yPin = 3;		// Y output of the accelerometer

 void setup() {
   // initialize serial communications:
   Serial.begin(9600);
   // initialize the pins connected to the accelerometer
   // as inputs:
   pinMode(xPin, INPUT);
   pinMode(yPin, INPUT);
 }

 void loop() {
   // variables to read the pulse widths:
   int pulseX, pulseY;
   // variables to contain the resulting accelerations
   int accelerationX, accelerationY;

   // read pulse from x- and y-axes:
   pulseX = pulseIn(xPin,HIGH);  
   pulseY = pulseIn(yPin,HIGH);

   // convert the pulse width into acceleration
   // accelerationX and accelerationY are in milli-g's: 
   // earth's gravity is 1000 milli-g's, or 1g.
   accelerationX = ((pulseX / 10) - 500) * 8;
   accelerationY = ((pulseY / 10) - 500) * 8;

   // print the acceleration
   Serial.print("X= ");
   Serial.print(accelerationX);
   // print a tab character:
   Serial.print("\t");
   Serial.print("Y= ");
   Serial.print(accelerationY);
   Serial.println();

   delay(100);
 }


int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

