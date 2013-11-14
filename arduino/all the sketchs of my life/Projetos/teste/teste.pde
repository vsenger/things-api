void setup() {
  Serial.begin(9600);
  
}

void loop() {
  int luz = analogRead(5);
  Serial.println(luz);
  delay(500);
}

