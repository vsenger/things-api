void setup() {
  Serial.begin(9600);
  pinMode(6,OUTPUT);
  pinMode(7,OUTPUT);
}

void loop() {
  if(Serial.available()>0) {
    byte l = Serial.read();
    if(l=='L') {
      digitalWrite(7, HIGH);
    }
    else if(l=='D') {
      digitalWrite(7, LOW);
    }
    else if(l=='l') {
      digitalWrite(6, HIGH);
    }
    else if(l=='d') {
      digitalWrite(6, LOW);
    }
  }
  
}

