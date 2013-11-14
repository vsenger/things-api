void setup() {
  Serial.begin(9600);
  pinMode(4, OUTPUT);
  
}

void loop() {
  int distancia = analogRead(1);
  if(distancia>40) {
    Serial.println("longe");
  }
  else {
    Serial.println("perto");
    digitalWrite(4, HIGH);
    delay(100);
    digitalWrite(4, LOW);
    
  }
  delay(500);
}



