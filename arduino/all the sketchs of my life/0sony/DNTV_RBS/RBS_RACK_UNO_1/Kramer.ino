
//codigos para matrix switcher Kramer, IN-OUT, ex 11 -> In 1, Out 1.

char* kramer_write(char* args) {
  Serial.begin(9600); //kramer serial - conectar no UNO no TX1 e RX0, lembrar de soltar os fios antes de subir firmware
  int codigo = atoi(args);
  //Serial.println("vamos la");
  if(codigo==11) {
    Serial.write(0x01);
    Serial.write(0x81);
    Serial.write(0x81);
    Serial.write(0x81);

  }
  else if(codigo==12) {
    Serial.write(0x01);
    Serial.write(0x81);
    Serial.write(0x82);
    Serial.write(0x81);

  }

  else if(codigo==21) {
    Serial.write(0x01);
    Serial.write(0x82);
    Serial.write(0x81);
    Serial.write(0x81);

  }

  else if(codigo==22) {
    Serial.write(0x01);
    Serial.write(0x82);
    Serial.write(0x82);
    Serial.write(0x81);

  }

  else if(codigo==31) {
    Serial.write(0x01);
    Serial.write(0x83);
    Serial.write(0x81);
    Serial.write(0x81);

  }
  else if(codigo==32) {
    Serial.write(0x01);
    Serial.write(0x83);
    Serial.write(0x82);
    Serial.write(0x81);

  }
  else if(codigo==41) {
    Serial.write(0x01);
    Serial.write(0x84);
    Serial.write(0x81);
    Serial.write(0x81);

  }
  else if(codigo==42) {
    Serial.write(0x01);
    Serial.write(0x84);
    Serial.write(0x82);
    Serial.write(0x81);

  }
}


char* kramer_read() {
  char* r="\0";
  //while(Serial.available()) {
  // Serial.print((char) Serial.read());
  //}     
}


