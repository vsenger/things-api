
char* rs232_write(char* args) {
  Serial.begin(19200);
  int codigo = atoi(args);
  if(codigo==1) {
      Serial.write(0x02);
      Serial.write("PON");
      Serial.write(0x03);
  }
  else if(codigo==2) {
      Serial.write(0x02);
      Serial.write("POF");
      Serial.write(0x03);
  }
}

char* rs232_read() {
   char* r="\0";
  while(Serial.available()) {
   Serial.print((char) Serial.read());
  }     
}

