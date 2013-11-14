/*  #define P0 0    // Porta Digital 0   -  Chave  
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

int led[] = { LED_1, LED_2, LED_3, LED_4, LED_5, LED_6, LED_7, LED_8, LED_9 }; // array com a sequencia dos leds
int num_pins = 9;                  // tamanho do array

*/
/*
/////////////////////////////////
Htachi HM55B Compass
parallax (#)

AUTHOR:   kiilo kiilo@kiilo.org
License:  http://creativecommons.org/licenses/by-nc-sa/2.5/ch/

http://parallax.com/Store/Microcontrollers/BASICStampModules/tabid/134/txtSearch/hm55b/List/1/ProductID/98/Default.aspx?SortField=ProductName%2cProductName
http://sage.medienkunst.ch/tiki-index.php?page=HowTo_Arduino_Parallax_HM55B_Kompass
http://arduino.cc/playground/HM55B

/////////////////////////////////
*/
#include <math.h> // (no semicolon)
//// VARS
byte CLK_pin = 8;
byte EN_pin = 9;
byte DIO_pin = 10;

int X_Data = 0;
int Y_Data = 0;
int angle;

//// FUNCTIONS

void ShiftOut(int Value, int BitsCount) {
  for(int i = BitsCount; i >= 0; i--) {
    digitalWrite(CLK_pin, LOW);      // pino de clock vai para 0
    if ((Value & 1 << i) == ( 1 << i)) {    //testa o bit a ser enviado
      digitalWrite(DIO_pin, HIGH);
      //Serial.print("1");
    }
    else {
      digitalWrite(DIO_pin, LOW);
      //Serial.print("0");
    }
    digitalWrite(CLK_pin, HIGH);
    delayMicroseconds(1);
  }
//Serial.print(" ");
}

int ShiftIn(int BitsCount) {
  int ShiftIn_result;
    ShiftIn_result = 0;
    pinMode(DIO_pin, INPUT);
    for(int i = BitsCount; i >= 0; i--) {
      digitalWrite(CLK_pin, HIGH);
      delayMicroseconds(1);
      if (digitalRead(DIO_pin) == HIGH) {
        ShiftIn_result = (ShiftIn_result << 1) + 1; 
        //Serial.print("x");
      }
      else {
        ShiftIn_result = (ShiftIn_result << 1) + 0;
        //Serial.print("_");
      }
      digitalWrite(CLK_pin, LOW);
      delayMicroseconds(1);
    }
  //Serial.print(":");

// below is difficult to understand:
// if bit 11 is Set the value is negative
// the representation of negative values you
// have to add B11111000 in the upper Byte of
// the integer.
// see: http://en.wikipedia.org/wiki/Two%27s_complement
  if ((ShiftIn_result & 1 << 11) == 1 << 11) {
    ShiftIn_result = (B11111000 << 8) | ShiftIn_result; 
  }


  return ShiftIn_result;
}

void HM55B_Reset() {
  pinMode(DIO_pin, OUTPUT);
  digitalWrite(EN_pin, LOW);
  ShiftOut(B0000, 3);
  digitalWrite(EN_pin, HIGH);
}

void HM55B_StartMeasurementCommand() {
  pinMode(DIO_pin, OUTPUT);
  digitalWrite(EN_pin, LOW);
  ShiftOut(B1000, 3);
  digitalWrite(EN_pin, HIGH);
}

int HM55B_ReadCommand() {
  int result = 0;
  pinMode(DIO_pin, OUTPUT);
  digitalWrite(EN_pin, LOW);
  ShiftOut(B1100, 3);
  result = ShiftIn(3);
  return result;
}


void setup() {
  Serial.begin(115200);
  //Serial.begin(9600);
  pinMode(EN_pin, OUTPUT);
  pinMode(CLK_pin, OUTPUT);
  pinMode(DIO_pin, INPUT);

  HM55B_Reset();
}

void loop() {
  HM55B_StartMeasurementCommand(); // necessary!!
  delay(40); // the data is 40ms later ready
  Serial.print(HM55B_ReadCommand()); // read data and print Status
  Serial.print(" ");  
  X_Data = ShiftIn(11); // Field strength in X
  Y_Data = ShiftIn(11); // and Y direction
  Serial.print(X_Data); // print X strength
  Serial.print(" ");
  Serial.print(Y_Data); // print Y strength
  Serial.print(" ");
  digitalWrite(EN_pin, HIGH); // ok deselect chip
  angle = 180 * (atan2(-1 * Y_Data , X_Data) / M_PI); // angle is atan( -y/x) !!!
  Serial.print(angle); // print angle
  Serial.println("");

}



