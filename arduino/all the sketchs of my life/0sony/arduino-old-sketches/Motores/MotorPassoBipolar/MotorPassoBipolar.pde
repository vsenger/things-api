#define MOTOR1_P 3
#define MOTOR1_N 4
#define MOTOR1_PWM 5

#define MOTOR2_P 7
#define MOTOR2_N 8
#define MOTOR2_PWM 6

void setup() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);

  digitalWrite(MOTOR1_PWM, HIGH);

  digitalWrite(MOTOR2_PWM, HIGH);
}

void loop() {
  step1();
  
  delay(2);
  
  step2();
  
  delay(2);
  
  step3();
  
  delay(2);
  
  step4();
  
  delay(2);
  
  
}
void step1() {
  digitalWrite(MOTOR1_P, HIGH);
  digitalWrite(MOTOR2_P, HIGH);
  digitalWrite(MOTOR1_N, LOW);
  digitalWrite(MOTOR2_N, LOW);
}

void step2() {
  digitalWrite(MOTOR1_P, LOW);
  digitalWrite(MOTOR2_P, HIGH);
  digitalWrite(MOTOR1_N, HIGH);
  digitalWrite(MOTOR2_N, LOW);
}

void step3() {
  digitalWrite(MOTOR1_P, LOW);
  digitalWrite(MOTOR2_P, LOW);
  digitalWrite(MOTOR1_N, HIGH);
  digitalWrite(MOTOR2_N, HIGH);
}
void step4() {
  digitalWrite(MOTOR1_P, HIGH);
  digitalWrite(MOTOR2_P, LOW);
  digitalWrite(MOTOR1_N, LOW);
  digitalWrite(MOTOR2_N, HIGH);
}
