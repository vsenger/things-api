void lerLuz() {
  luminosidade = analogRead(LDR);
}

void leds() {
    int ledsParaLigar = map(luminosidade, 0, 750, 0, 9);
  for(int x=0;x<ledsParaLigar;x++) {
    digitalWrite(led[x], HIGH);
    delay(100);
  }
  for(int x=0;x<ledsParaLigar;x++) {
    digitalWrite(led[x], LOW);
    delay(100);  
  }
}

