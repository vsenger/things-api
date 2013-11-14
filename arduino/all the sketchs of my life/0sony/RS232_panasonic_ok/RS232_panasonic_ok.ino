void setup() {
  Serial.begin(115200);
  Serial1.begin(19200); 
}

void loop() {
  if(!Serial.available()) return;
  Serial.println("Comeco");
  Serial1.write(0x02);
  
   while(Serial.available()) {
    char c = Serial.read();
    Serial.print(c);
    if(c!='\n') Serial1.write(c);
    delay(2);
  }
  Serial1.write(0x03);
  Serial1.flush(); 
  while(Serial1.available()) {
   Serial.print((char) Serial1.read());
  }   
  Serial.println("Fim");
}
