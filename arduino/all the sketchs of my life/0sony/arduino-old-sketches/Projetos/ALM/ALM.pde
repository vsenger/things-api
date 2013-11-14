void setup() {
  Serial.begin(9600);
  pinMode(7, OUTPUT);
}

void loop() {
  if(Serial.available() >0) {
    byte incoming=Serial.read();
    if(incoming==65) {
      digitalWrite(7, HIGH);
      delay(5000);
      digitalWrite(7, LOW);
    }
  }
}

