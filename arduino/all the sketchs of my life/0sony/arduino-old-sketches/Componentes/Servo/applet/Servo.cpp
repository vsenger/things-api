#include <Servo.h> 

#include "WProgram.h"
void setup();
void loop();
Servo myservo10;  // create servo object to control a servo 

int pos = 0;    // variable to store the servo position 

void setup() 
{ 
  myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
} 

void loop() 
{ 
  int val = map(analogRead(1), 0, 1023, 0, 179);     // scale it to use it with the servo (value between 0 and 180) 
  myservo10.write(val);                  // sets the servo position according to the scaled value 
  delay(15);                           // waits for the servo to get there 

} 


int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

