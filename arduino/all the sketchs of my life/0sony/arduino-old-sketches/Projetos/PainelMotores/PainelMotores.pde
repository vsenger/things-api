// Controlling a servo position using a potentiometer (variable resistor) 
// by Michal Rinott <http://people.interaction-ivrea.it/m.rinott> 

#include <Servo.h> 

Servo myservo;  // create servo object to control a servo 
Servo myservo1;  // create servo object to control a servo 

int potpin = 1;  // analog pin used to connect the potentiometer
int val;    // variable to read the value from the analog pin 

void setup() 
{ 
  Serial.begin(9600);
  pinMode(9, OUTPUT);
  pinMode(6, OUTPUT);

  myservo.attach(10);  // attaches the servo on pin 9 to the servo object 
  myservo1.attach(11);  // attaches the servo on pin 9 to the servo object 
} 

void loop() 
{ 
  myservo.write(map(analogRead(2), 0, 1023, 0, 179));
  myservo1.write(map(analogRead(0), 0, 1023, 0, 179));
  analogWrite(9,map(analogRead(1), 0, 1023, 0, 255));
  analogWrite(6,map(analogRead(1), 0, 1023, 0, 255));
  delay(50);        
        

  Serial.println(  analogRead(0));
  
  Serial.println(  analogRead(1));
  Serial.println(  analogRead(2));
  Serial.println( "============");
  /*if(Serial.available()>0) {

    while(Serial.available()>0) {

      int dado = int(Serial.read());
      Serial.println(dado,DEC);
      myservo.write(dado);
      int dado1 = int(Serial.read());
      Serial.println(dado1,DEC);
      myservo1.write(dado1);
      
    }
  }*/

  /*val = analogRead(potpin);            // reads the value of the potentiometer (value between 0 and 1023) 
  //Serial.println(val);
  val = map(val, 0, 1023, 0, 179);     // scale it to use it with the servo (value between 0 and 180) 
  //myservo.write(analogRead(1)/4);                  // sets the servo position according to the scaled value 
  //myservo1.write(analogRead(2)/4);                  // sets the servo position according to the scaled value */
  delay(10);                           // waits for the servo to get there 
} 

