#include "Wire.h"
#include "hrmi_funcs.h"
#define HRMI_HOST_BAUDRATE 9600
#define HRMI_I2C_ADDR      127
#define MAX_IN_BUFFSIZE 16

#define SPEAKER 4 //em paralelo com led
#define LED_P5 4 //em paralelo com speaker
#define LED_P4 7
#define LED_P3 8
#define LED_P2 12
#define LED_P1 13

#define CHAVE_1 2

#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 
#define TRANSISTOR_Q3 3

//Analogicas
#define SENSOR_TEMPERATURA 2
#define SENSOR_LUZ 3
#define ENTRADA_1 0
#define ENTRADA_2 1

//Entradas para interrupcao
#define INT_1 3
#define INT_0 2

#define SERVO_1 5
#define SERVO_2 6

//Ponte H
#define MOTOR1_A 7 //led paralelo
#define MOTOR1_B 8 //led paralelo
#define MOTOR1_PWM 10 //led paralelo

#define MOTOR2_A 12 //led paralelo
#define MOTOR2_B 13 //led paralelo
#define MOTOR2_PWM 11 //led paralelo

char serInStr[MAX_IN_BUFFSIZE];   // Serial input string array
int numEntries = 0;               // Number of HR values to request
int numRspBytes;                  // Number of Response bytes to read
byte i2cRspArray[34];	          // I2C response array, sized to read 32 HR values
byte hrmi_addr = HRMI_I2C_ADDR;   // I2C address to use
int leds[] = {
  13, 12, 8, 7, 4};

void setup()
{
  hrmi_open();
  Serial.begin(HRMI_HOST_BAUDRATE);
  for(int x=0;x<5;x++) {
    pinMode(leds[x], OUTPUT);
  }  
}


void loop()
{
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {

    int val = map(i2cRspArray[2], 80,130,0, 150);

    Serial.print(i2cRspArray[2]);
    int numeroLedsALigar = map(i2cRspArray[2], 80, 100, 0, 4);
    //Serial.println(numeroLedsALigar);
    for(int x=0;x<=numeroLedsALigar;x++) {
      digitalWrite(leds[x], HIGH);
      delay(100);    
    }  
    for(int x=numeroLedsALigar;x>-1;x--) {
      digitalWrite(leds[x], LOW);
      delay(100);    
    }  

    //analogWrite(9, val);

    /*if(i2cRspArray[2]>100) 
     andar(0, map(i2cRspArray[2], 100, 130, 100,255));
     else parar();*/
  }
  if (++numEntries > 30) numEntries = 0;
  delay(300);
}






