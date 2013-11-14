#include <LiquidCrystal.h>
#include <Wire.h>

char comando[16];
int luz= 0;

LiquidCrystal lcd(4, 9, 14, 15, 16, 17);
// set up a new serial port

void setup()
{  
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  Wire.begin();
  Serial.begin(115200);   
  initLCD();
}  

void initLCD()  {
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Mater I2C On");
  lcd.setCursor(0, 1);
  lcd.print("Waiting command.");
}

void loop ()
{  
  if(Serial.available()>0) {
    limpaComando();

    int counter =0;
    while(Serial.available()>0) {
      comando[counter++]=Serial.read();
      delay(20);
    }
    lcd.clear();
    lcd.setCursor(0, 0);

    lcd.print(comando);
    executarComando();
    //lcd.print("Aguardando....");
    for(int x=0;x<4;x++) {
      digitalWrite(4, HIGH);
      delay(30);
      digitalWrite(4, LOW);
      delay(30);
    }
  }
}  

void executarComando() {
  lcd.setCursor(0, 1);
  lcd.print(comando[0]);
  lcd.setCursor(2, 1);
  lcd.print("commuicated...");
  Wire.beginTransmission(comando[0]);
  Wire.send(comando);	 
  Wire.endTransmission(); 
}
void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}








