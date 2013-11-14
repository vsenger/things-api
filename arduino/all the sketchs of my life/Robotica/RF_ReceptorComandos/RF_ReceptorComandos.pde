#include <LiquidCrystal.h>
#include <VirtualWire.h>

#define MOTOR1_P 13
#define MOTOR1_N 9
#define MOTOR1_PWM 6

#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 5

#define SPEAKER 12

#define TX 0
#define RX 1

#define PING 15

#define LCD_RS 14
#define LCD_EN 17
#define LCD_D4 4
#define LCD_D5 3
#define LCD_D6 2
#define LCD_D7 18

#define COMANDO_TAMANHO 16          
char comando[16];
uint8_t buf[VW_MAX_MESSAGE_LEN];
uint8_t buflen = VW_MAX_MESSAGE_LEN;
char string[16];

LiquidCrystal lcd(LCD_RS, LCD_EN, LCD_D4, LCD_D5, LCD_D6, LCD_D7);

const char FRENTE[COMANDO_TAMANHO] =   "FRENTE         ";
const char RE[COMANDO_TAMANHO] =       "RE             ";
const char ESQUERDA[COMANDO_TAMANHO] = "ESQUERDA       ";
const char DIREITA[COMANDO_TAMANHO] =  "DIREITA        ";

void setup() {
  setupEngines();
  setupLCD();
  setupReceptorRF();
  pinMode(SPEAKER, OUTPUT);
}
void setupReceptorRF() {
  pinMode(RX,INPUT);
  vw_set_rx_pin (RX);   //Pino que recebe os dados
  vw_setup(2000);     // Bits per sec
  vw_rx_start();       // Start the receiver PLL running
}

void setupLCD() {
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
void setupEngines() {
  pinMode(MOTOR1_P, OUTPUT);
  pinMode(MOTOR1_N, OUTPUT);
  pinMode(MOTOR2_P, OUTPUT);
  pinMode(MOTOR2_N, OUTPUT);
  pinMode(MOTOR1_PWM, OUTPUT);
  pinMode(MOTOR2_PWM, OUTPUT);
  parar();
}  

void loop(){
  uint8_t buf[VW_MAX_MESSAGE_LEN];
  uint8_t buflen = VW_MAX_MESSAGE_LEN;
  if (vw_get_message(buf, &buflen)) // Non-blocking
  {
    int i;
    lcd.setCursor(0, 1);
    lcd.clear();
    limpaComando();
    for (i = 0; i < buflen; i++)
    {
      comando[i]=buf[i];
    }
    lcd.print(comando);
    if(strcmp(comando, FRENTE)==0)  {
      andarFrente(2000,5);
      parar();
    }
    else if(strcmp(comando, RE)==0)  {
      andarTraz(2000,5);
      parar();
    }
    if(strcmp(comando, DIREITA)==0)  {
      virarDireita(2000,5);
      parar();
    }
    if(strcmp(comando, ESQUERDA)==0)  {
      virarEsquerda(2000,5);
      parar();
    }
  }
}

void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}

/*
*/

