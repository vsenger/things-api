char* rs232_write1(char* args) {
  Serial.begin(19200);
  int codigo = atoi(args);
  if(codigo==1) {
      Serial.write(0x02); //Power On
      Serial.write("PON");
      Serial.write(0x03);
  }
  else if(codigo==2) { //Power Off
      Serial.write(0x02);
      Serial.write("POF");
      Serial.write(0x03);
  }
  else if(codigo==3) { //Volume UP
      Serial.write(0x02);
      Serial.write("AUU");
      Serial.write(0x03);
  }
  else if(codigo==4) { //Volume Down
      Serial.write(0x02);
      Serial.write("AUD");
      Serial.write(0x03);
  }
}

char* rs232_read1() {
   char* r="\0";
  //while(Serial.available()) {
  // Serial.print((char) Serial.read());
  //}     
}

