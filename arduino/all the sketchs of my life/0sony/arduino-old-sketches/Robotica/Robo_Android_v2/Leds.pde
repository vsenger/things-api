void leds(byte flag, byte numOfValues) {
  if(modo!=5) return;
  int length = meetAndroid.stringLength();
  char comando[length];
  meetAndroid.getString(comando);

  if(comando[0]=='R') {
    pinMode(LED_RED,INPUT);
    int ligado = digitalRead(LED_RED);
    pinMode(LED_RED,OUTPUT);
    digitalWrite(LED_RED,!ligado);

  }
  else if(comando[0]=='G') {
    pinMode(LED_GREEN,INPUT);
    int ligado = digitalRead(LED_GREEN);
    pinMode(LED_GREEN,OUTPUT);
    digitalWrite(LED_GREEN,!ligado);
  }
  else if(comando[0]=='B') {
    pinMode(LED_BLUE,INPUT);
    int ligado = digitalRead(LED_BLUE);
    pinMode(LED_BLUE,OUTPUT);
    digitalWrite(LED_BLUE,!ligado);
  }

}
void testeLeds() {
  digitalWrite(LED_RED, HIGH);
  delay(200);
  digitalWrite(LED_RED, LOW);
  delay(200);
  digitalWrite(LED_BLUE, HIGH);
  delay(200);
  digitalWrite(LED_BLUE, LOW);
  delay(200);
  digitalWrite(LED_GREEN, HIGH);
  delay(200);
  digitalWrite(LED_GREEN, LOW);
  delay(200);
}

