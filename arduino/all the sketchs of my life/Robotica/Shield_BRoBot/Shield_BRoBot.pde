#include <LiquidCrystal.h>
#include <VirtualWire.h>
//#include <Servo.h>
#define MOTOR1_P 11
#define MOTOR1_N 12
#define MOTOR1_PWM 5

#define MOTOR2_P 7
#define MOTOR2_N 8
#define MOTOR2_PWM 6
// LCD 0, 1 ,14, 17, 18, 19
// Trasmissor 12
// Receptor 13
// Motor 4, 5, 6, 7, 8 e 9

// Em uso 0 1       4 5 6 7 8 9       12 13 14       17 18 19 
// Livre       2 3              10 11          15 16
int valor_anterior = -1;ds
int erro =0;
int recebidos = 0;
int i = 0;
char comando[16];


uint8_t buf[VW_MAX_MESSAGE_LEN];
uint8_t buflen = VW_MAX_MESSAGE_LEN;
char string[16];

LiquidCrystal lcd(14, 17, 4, 3, 2, 18);

int estado = 0;
int sentido = 0;
long duration, inches, cm;
int reacao = 0;
int interrupt=0;
int mode=0;
//Servo myservo10;  // create servo object to control a servo
#define COMANDO_TAMANHO 16          
const char FRENTE[COMANDO_TAMANHO] =   "FRENTE         ";
const char RE[COMANDO_TAMANHO] =       "RE             ";
const char ESQUERDA[COMANDO_TAMANHO] = "ESQUERDA       ";
const char DIREITA[COMANDO_TAMANHO] =  "DIREITA        ";

void setup() {

  setupEngines();
  //setupReceptorRF();
  setupLCD();

  //myservo10.attach(10);  // attaches the servo on pin 10 to the servo object 
  Serial.begin(115200);  


}
void setupReceptorRF() {

  pinMode(3, OUTPUT);
  //pinMode(11, INPUT);
  digitalWrite(3, LOW);

  // Initialise the IO and ISR

  //Configura o Receptor
  vw_set_rx_pin (3);   //Pino que recebe os dados
  vw_setup(2000);     // Bits per sec
  vw_rx_start();       // Start the receiver PLL running

  //Configura o Transmissor
  //vw_set_tx_pin(12);    //Pino de transmissao de dados
  //vw_setup(2000);     // Bits per sec

}
void setupLCD() {
  // set up the LCD's number of rows and columns:
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
  //andarFrente(2000,3);
  parar();
}  

void loop(){

  uint8_t buf[VW_MAX_MESSAGE_LEN];
  uint8_t buflen = VW_MAX_MESSAGE_LEN;
  int ii;
  for(ii=0;ii<strlen(string)-1;ii++) string[ii]=' ';
  ii=0;  
  //lcd.clear();
  //lcd.print(Serial.available());
  if(Serial.available()>0) {
    while(Serial.available()>0) {
      string[ii]=Serial.read();
      delay(20);
      ii++;
    }
    lcd.clear();
    lcd.print(string);
    delay(2000);
    initLCD();    
 
  }  
 

  /*if (vw_get_message(buf, &buflen)) // Non-blocking
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
   Serial.println(comando);
   
   if(strcmp(comando, FRENTE)==0)  {
   lcd.setCursor(0,2);
   lcd.print("andando...");
   
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
   
   }*/
}
void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}








