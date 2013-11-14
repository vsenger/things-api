#define CHAVE 0

#define LED1 14
#define LED2 1
#define LED3 2
#define LED4 3
#define LED5 4
#define LED6 5
#define LED7 8
#define LED8 6
#define LED9 13

#define Q3 6
#define Q2 9
#define Q4 18
#define Q5 17


#define SERVO_1 10
#define SERVO_2 11

#define SPEAKER 12

#define LDR 0

#define ANALOGICA1 1
#define ANALOGICA2 2


void setup() {
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(
}

void loop() {
  digitalWrite(4,HIGH);
  digitalWrite(5,LOW);
  delay(1500);
  digitalWrite(5,HIGH);
  digitalWrite(4,LOW);
  delay(1500);
}

  
