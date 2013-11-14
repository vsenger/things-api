
long lastDebounce=0;
long debounceDelay=50;

void setupInit() {
  //attachInterrupt(0, mudarModo, LOW);
}

void mudarModo() {
  if(millis()-lastDebounce>debounceDelay) {
    modoReset();
    modo = modo>5? 1 : modo + 1;
    lastDebounce=millis();
  } 
}
void modoReset() {
  parar();
}

void modoZero() {
  piscaLed13(1000);
}

void piscaLed13(long tempo) {
  digitalWrite(13, HIGH);
  delay(tempo);
  digitalWrite(13, LOW);
  delay(tempo);
}
