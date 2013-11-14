#define T1 3
#define T2 9
#define T3 11
#define T4 12

void setup() {
  pinMode(T1,OUTPUT);
  pinMode(T2,OUTPUT);
  pinMode(T3,OUTPUT);
  pinMode(T4,OUTPUT);
}

void loop() {
  digitalWrite(T1, HIGH);
  digitalWrite(T2, HIGH);
  digitalWrite(T3, HIGH);
  digitalWrite(T4, HIGH);
  delay(1000);
  digitalWrite(T1, LOW);
  digitalWrite(T2, LOW);
  digitalWrite(T3, LOW);
  digitalWrite(T4, LOW);
  delay(1000);

  digitalWrite(T1, HIGH);
  delay(1000);
  digitalWrite(T1, LOW);
  delay(1000);
  digitalWrite(T2, HIGH);
  delay(1000);
  digitalWrite(T2, LOW);
  delay(1000);
  digitalWrite(T3, HIGH);
  delay(1000);
  digitalWrite(T3, LOW);
  delay(1000);
  digitalWrite(T4, HIGH);
  delay(1000);
  digitalWrite(T4, LOW);
  delay(1000);
  
  
}
