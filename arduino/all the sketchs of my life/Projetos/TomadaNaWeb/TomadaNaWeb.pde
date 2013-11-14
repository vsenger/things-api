
boolean acionado = false;
void setup() {
  Serial.begin(9600);
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(13, OUTPUT);
}

void loop() {
  if(Serial.available() >0) {
    byte incoming=Serial.read();
    Serial.println("Recebendo dados");
    Serial.println(incoming, DEC);
    if(incoming==1) {
      digitalWrite(3, HIGH);
      delay(2000);
      digitalWrite(3, LOW);
    }
    else {
      digitalWrite(2, HIGH);
      delay(2000);
      digitalWrite(2, LOW);
    }      
  }
}

