#include <LiquidCrystal.h>
#include <VirtualWire.h>
//#include <Servo.h> 

#define MOTOR1_P 12
#define MOTOR1_N 11
#define MOTOR1_PWM 5

#define MOTOR2_P 8
#define MOTOR2_N 7
#define MOTOR2_PWM 6
LiquidCrystal  lcd(14, 15, 16, 17, 18, 19);

//Servo servoHorizontal;  // create servo object to control a servo 
//Servo servoVertical;  // create servo object to control a servo 


void setup() {
  setupReceptorRF();

  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(14, OUTPUT);
  pinMode(15, OUTPUT);
  pinMode(16, OUTPUT);
  pinMode(17, OUTPUT);
  pinMode(18, OUTPUT);
  pinMode(19, OUTPUT);

  //servoHorizontal.attach(10);  // attaches the servo on pin 9 to the servo object 
  //servoVertical.attach(11);
  Serial.begin(9600);
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("BRoBot:");
  lcd.setCursor(0, 1);
  lcd.print("Aguardando....");  
}
void setupReceptorRF() {
  pinMode(12, OUTPUT);
  digitalWrite(12, LOW);
  //Configura o Receptor
  vw_set_rx_pin (12);   //Pino que recebe os dados
  vw_setup(2000);     // Bits per sec
  vw_rx_start();       // Start the receiver PLL running

}
void loop() {

  //Serial.println(analogRead(0)/4);
  /*if(Serial.available()>0) {

    while(Serial.available()>0) {

      int dado = int(Serial.read());
      Serial.println(dado,DEC);
      //servoVertical.write(dado);
      int dado1 = int(Serial.read());
      Serial.println(dado1,DEC);
      //servoHorizontal.write(dado1);

    }
  }*/
  analogWrite(MOTOR1_PWM,250);
  //digitalWrite(MOTOR1_PWM, HIGH);
  digitalWrite(MOTOR1_P, HIGH);
  digitalWrite(MOTOR1_N, LOW);  
  analogWrite(MOTOR2_PWM, 250);
  //digitalWrite(MOTOR2_PWM, HIGH);
  digitalWrite(MOTOR2_P, HIGH);
  digitalWrite(MOTOR2_N, LOW);
  delay(2000);

  /*uint8_t buf[VW_MAX_MESSAGE_LEN];
   uint8_t buflen = VW_MAX_MESSAGE_LEN;
   Serial.println("Esperando...");
   lcd.clear();
   lcd.setCursor(0, 0);
   lcd.print("BRoBot:");
   lcd.setCursor(0, 1);
   
   if (vw_get_message(buf, &buflen)) // Non-blocking
   {
   int i;
   Serial.println("cHEGOU...");
   lcd.setCursor(0, 1);
   lcd.print("Recebendo Dados...");
   
   for (i = 0; i < buflen-1; i++)
   {
   lcd.clear();
   lcd.setCursor(0, 0);
   if(buf[i]='a') {
   lcd.print("Vou andar");  
   
   analogWrite(MOTOR1_PWM, 250);
   digitalWrite(MOTOR1_P, !HIGH);
   digitalWrite(MOTOR1_N, !LOW);  
   analogWrite(MOTOR2_PWM, 250);
   digitalWrite(MOTOR2_P, !HIGH);
   digitalWrite(MOTOR2_N, !LOW);  
   delay(1000);
   }
   Serial.print(buf[i]);
   }
   lcd.clear();
   
   }*/

}



