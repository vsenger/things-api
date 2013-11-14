void setup() {
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
}

void loop() {
  digitalWrite(12, 0);
  digitalWrite(13, 1);
  analogWrite(11,200);
  delay(1000);
  analogWrite(11,0);
  delay(3000);
  digitalWrite(12, 1);
  digitalWrite(13, 0);
  analogWrite(11,200);
  delay(1000);
  analogWrite(11,0);
  delay(3000);
}



  
