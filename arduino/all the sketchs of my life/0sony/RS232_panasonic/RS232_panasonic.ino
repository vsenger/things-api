void setup() {
  Serial.begin(115200);
  Serial1.begin(19200);
  Serial1.print("STXPONETX");
  
}

void flash13() {
  digitalWrite(13, HIGH);
  delay(100);
  digitalWrite(13, LOW);
  delay(100);
}
  
void loop() {
   while(Serial.available()) {
    Serial1.write(Serial.read());
  }
  Serial1.flush();  
  delay(20);
  while(Serial1.available()) {
   Serial.print((char) Serial1.read());
  }   
 
}
