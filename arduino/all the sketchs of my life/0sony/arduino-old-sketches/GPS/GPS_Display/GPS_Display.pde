#include <LiquidCrystal.h>
#include <SoftwareSerial.h>
//amarelo = 2 - azul = 3
SoftwareSerial GPS = SoftwareSerial(2, 3);
LiquidCrystal lcd(4, 9, 17, 16, 15, 14);
char linea[32] = "";
int byteGPS = -1;
int conta = 0;

void setup() {
  Serial.begin(9600);
  GPS.begin(4800);
  initLCD();  

}
void resetLinha() {

  for (int i=0;i<300;i++)
  {	
    linea[i]=' ';
  }
}

void initLCD()  {
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Master I2C On");
  lcd.setCursor(0, 1);
  lcd.print("Waiting command.");
}

void loop(){
  byteGPS = GPS.read();
  if (byteGPS == -1)
  {
    delay(100);
  }
  else {
    linea[conta]=byteGPS;	  // If there is serial port data, it is put in the buffer
    conta++;
    if (byteGPS==13 || conta==32) {
      lcd.clear();
      lcd.setCursor(0,0);
      lcd.print(linea);
      Serial.println(linea);
      resetLinha();
      conta=0;
    }
  }
}





