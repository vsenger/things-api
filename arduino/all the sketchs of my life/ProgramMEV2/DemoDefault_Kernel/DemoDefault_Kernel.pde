#include <ElectronKernel.h>

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

int leds[] = {
  13, 12, 8, 7, 4};

void setup() {
  Serial.begin(9600);
  Kernel.setup(INTERRUPTION);
  
  Kernel.registerChangeModeListener(beep);
  Kernel.registerMode("Modo 1", modo1_setup);
  Kernel.registerTask(modo1_loop);
  Kernel.registerMode("Modo 2", modo1_setup);
  Kernel.registerTask(modo2_loop);
  
}

void beep() {
  Serial.println("Mudou modo");
  pinMode(4, OUTPUT);
  digitalWrite(4, HIGH);
  delay(5);
  digitalWrite(4, LOW);
}

void modo1_setup() {
  for(int x=0;x<5;x++) {
    pinMode(leds[x], OUTPUT);
  }  
}

void modo1_loop(){ 
  Serial.println("Sensor modo1 : ");
  Serial.println(analogRead(SENSOR_LUZ));
  int numeroLedsALigar = map(analogRead(SENSOR_LUZ), 300, 900, 0, 4);
  Serial.println(numeroLedsALigar);

  for(int x=0;x<=numeroLedsALigar;x++) {
    digitalWrite(leds[x], HIGH);
    Kernel.wait(100);    
  }  
  for(int x=numeroLedsALigar;x>-1;x--) {
    digitalWrite(leds[x], LOW);
        Kernel.wait(100);    
  } 
}

void modo2_loop(){ 
  Serial.println("Sensor modo 2: ");
  Serial.println(analogRead(SENSOR_LUZ));
  int numeroLedsALigar = map(analogRead(SENSOR_LUZ), 300, 900, 0, 4);
  Serial.println(numeroLedsALigar);
  for(int x=numeroLedsALigar;x>=-1;x--) {
    digitalWrite(leds[x], HIGH);
    delay(100);    
  }  
  for(int x=0;x<=numeroLedsALigar;x++) {
    digitalWrite(leds[x], LOW);
    delay(100);    
  }  
}

void loop() {
  Kernel.loop();
}



