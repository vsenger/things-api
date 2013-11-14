void setup() {
  Serial.begin(9600);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
}


void loop() {
  if(Serial.available() >0) {
    int incoming=Serial.read();
    Serial.println("Recebendo dados");
    Serial.println(incoming, DEC);
    abrirPorta(incoming);
    
  }
}

void abrirPorta(int conjunto) {
    if(conjunto=65) {
      Serial.println("Abrindo porta do 22");
      digitalWrite(8, HIGH);
      delay(200);
      digitalWrite(8, LOW);
    }
    if(conjunto=66) {
      Serial.println("Abrindo porta do 34");
      digitalWrite(9, HIGH);
      delay(200);
      digitalWrite(9, LOW);
    }
}
