void lerLuz() {
  luminosidade = analogRead(LDR);
}

void leds() {
  for(int x=0;x<5;x++) {
    digitalWrite(led[x], HIGH);
    delay(1000);
  }
  for(int x=0;x<5;x++) {
    digitalWrite(led[x], LOW);
    delay(1000);  
  }
}

