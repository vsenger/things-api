void setup() {
  Serial.begin(9600);
}

void loop() {
  int luz = analogRead(3);
  if(luz>700) {
    Serial.println("apagada");
  }
  else {
    Serial.println("acesa");
  }
  delay(500);
}



