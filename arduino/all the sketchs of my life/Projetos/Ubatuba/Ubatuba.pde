void setup() {
  for(int x=0;x<20;x++) {
     pinMode(x, OUTPUT);
  }
}

void loop() {
  for(int x=0;x<20;x++) {
     digitalWrite(x, HIGH);
  }
  delay(1000);
  for(int x=0;x<20;x++) {
     digitalWrite(x, LOW);
  }
  delay(1000);  
}
  
