#include <LiquidCrystal.h>
#include <Wire.h>

char comando[16];
int luz= 0;

LiquidCrystal lcd(4, 9, 14, 15, 16, 17);

void setup()
{  
  Wire.begin();
  //Wire.onReceive(receiveEvent);
  Serial.begin(115200);   
  initLCD();
}  

void receiveEvent(int howMany) {
  char string[16];
  int counter=0;
  while(Wire.available()>0) {
    string[counter++]=Wire.receive();
  }
  

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Central Updated");
  lcd.setCursor(0, 1);
  lcd.print(string);

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
  delay(5);
}  


void receiveEvent() {
  if(Wire.available()>0)    // slave may send less than requested
  {
    byte b = Wire.receive();
    while(Wire.available()) Wire.receive();
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("something new");
    lcd.setCursor(0, 1);
    lcd.print(b, DEC);


  }
}

void executarComando() {
  lcd.setCursor(0, 0);
  lcd.print("Begin with ");
  lcd.setCursor(12, 0);
  lcd.print(comando[0]);
  Wire.beginTransmission(comando[0]);
  Wire.send(comando);	 
  Wire.endTransmission(); 
  lcd.setCursor(2, 1);
  lcd.print("commuicated...");
  
}
void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}












