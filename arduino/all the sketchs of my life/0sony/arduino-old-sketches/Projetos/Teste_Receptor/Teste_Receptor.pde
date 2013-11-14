#include <VirtualWire.h>
#include <LiquidCrystal.h>

uint8_t buf[VW_MAX_MESSAGE_LEN];
uint8_t buflen = VW_MAX_MESSAGE_LEN;

LiquidCrystal lcd(14, 17, 4, 3, 2, 18);

void setup() {
  setupReceptorRF();
  lcd.begin(16, 2);
  initLCD();
} 
void initLCD()  {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("BRoBot:");
  lcd.setCursor(0, 1);
  lcd.print("Aguardando....");
}
void setupReceptorRF() {
  pinMode(1,INPUT);
  pinMode(12, OUTPUT);
  vw_set_rx_pin (1);   //Pino que recebe os dados
  vw_setup(2000);     // Bits per sec
  vw_rx_start();       // Start the receiver PLL running
}

void loop(){
  uint8_t buf[VW_MAX_MESSAGE_LEN];
  uint8_t buflen = VW_MAX_MESSAGE_LEN;
  if (vw_get_message(buf, &buflen)) // Non-blocking
  {
    char conteudo[buflen];
    for(int x=0;x<buflen;x++) {
      conteudo[x]=buf[x];
    }
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print(conteudo);

  }
}

