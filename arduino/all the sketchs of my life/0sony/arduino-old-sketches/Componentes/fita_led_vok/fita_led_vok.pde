void setup() {
  pinMode(6,OUTPUT); //blue
  pinMode(9,OUTPUT); //verde
  pinMode(18,OUTPUT); //vermelho

}

void loop() {
  for(int x=0;x<2;x++) {
    digitalWrite(9, HIGH);
    delay(200);
    digitalWrite(9, LOW);
    delay(200);
  }

  for(int x=0;x<2;x++) {
    for(int x=0;x<250;x++) {
      analogWrite(9, x);
      delay(10);
    }
    digitalWrite(18, HIGH);
    delay(200);
    for(int x=250;x>0;x--) {
      analogWrite(9, x);
      delay(10);
    }

    digitalWrite(18, LOW);
    delay(200);
  }

}


