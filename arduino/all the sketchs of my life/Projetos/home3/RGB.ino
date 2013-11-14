void modo_change_rgb_red_setup() {
  lcd.setCursor(0, 1);
  lcd.print("RED");
  rgb='R';  
}
void modo_change_rgb_green_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("GREEN");
  rgb='G';  
}
void modo_change_rgb_blue_setup() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("RGB Ping");
  lcd.setCursor(0, 1);
  lcd.print("BLUE");
  rgb='B';  
}
void modo_change_rgb() {
  lcd.clear();
  lcd.setCursor(0, 0);
  readPing();
  int port = 0;
  if(rgb=='R') {
    port = RED;
    lcd.print("RGB Red");
  }
  else if(rgb=='G') {
    port = GREEN;  
    lcd.print("RGB Green");
  }
  else if(rgb=='B') {
    port = BLUE;  
    lcd.print("RGB Blue");
  }
  if(cm<10) {
    delay(200);
    readPing();
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
        readPing();
      }
    }
  }
  delay(150);
}

