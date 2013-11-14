char rgb='R';
long cm;
void RGB(char* comando) {
  int port = 0;
  if(comando[1]=='R') port = 1;
  if(comando[1]=='G') port = 1;  
  if(comando[1]=='B') port = 1;  
  int amount = map(atoi(&comando[2]),0,9,0,255);
  analogWrite(port, amount);
}

void rgb_red_setup() {
  lcd.setCursor(0, 1);
  lcd.print("RED");
  rgb='R';  
}
void rgb_green_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("GREEN");
  rgb='G';  
}
void rgb_blue_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("BLUE");
  rgb='B';  
}
void mode_rgb() {
  lcd.clear();
  lcd.setCursor(0, 0);
  //readPing();
  //homeDevice.execute("distance");
  int port = 0;
  if(rgb=='R') {
    port = 3;
    lcd.print("RGB Red");
  }
  else if(rgb=='G') {
    port = 6;  
    lcd.print("RGB Green");
  }
  else if(rgb=='B') {
    port = 9;  
    lcd.print("RGB Blue");
  }
  if(cm<10) {
    delay(200);
    //cm = long(homeDevice.execute("distance"));
    //readPing();
    if(cm<10){  
      blink(port, 3, 100);
      while(cm<50) {
        int cmok=cm;
        cmok = cmok>25 ? 25 : cmok;
        cmok = cmok<5 ? 5 : cmok;

        int amount = map(cmok,5,25,255,0);
        lcd.setCursor(0, 1);
        lcd.print(cm);
        lcd.setCursor(4, 1);
        lcd.print(amount);
        analogWrite(port, amount);
        delay(50);
        //cm = atoi(homeDevice.execute("distance"));
      }
    }
  }
  delay(150);
}

void blink(int port, int n, int d) {
  pinMode(port, OUTPUT);
  for(int x=0;x<n;x++) {
    digitalWrite(port, HIGH);
    delay(d);
    digitalWrite(port, LOW);
    delay(d);
  }
}

