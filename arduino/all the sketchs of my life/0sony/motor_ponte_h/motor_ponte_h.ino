#define MOTOR1_A 7
#define MOTOR1_B 8
#define MOTOR1_PWM 10

void setup() {
  pinMode(MOTOR1_A, OUTPUT);
  pinMode(MOTOR1_B, OUTPUT);
  
}

void loop() {
  digitalWrite(MOTOR1_A, HIGH);
  digitalWrite(MOTOR1_B, LOW);
  funcionarMotor();  
  digitalWrite(MOTOR1_A, LOW);
  digitalWrite(MOTOR1_B, HIGH);
  funcionarMotor();
}

void funcionarMotor() {
  analogWrite(MOTOR1_PWM, 150);
  delay(2000);
  analogWrite(MOTOR1_PWM, 250);
  delay(2000);
  analogWrite(MOTOR1_PWM, 0);
  delay(2000);
}
