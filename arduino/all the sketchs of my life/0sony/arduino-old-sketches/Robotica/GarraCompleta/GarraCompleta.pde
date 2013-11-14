#include <MotorDC.h>
#include <Wire.h>

MotorDC motorBase1(5, 6, 7); //branco
MotorDC motorBase2(3, 2, 4); //amarelo
MotorDC motorBase3(11, 12, 13); //azul
MotorDC motorGarra(10, 9, 8); //preto
MotorDC motor= motorBase1; //motor para trabalhar via I2C

void setup(){
  Serial.begin(9600); 
  Wire.begin(65);
  Wire.onReceive(receiveEvent);
  motorBase1.andarParaFrente(255);
  delay(2000);
  motorBase1.parar();
}

void loop() {
  Serial.println("nir");
  delay(2000);
  
}

void receiveEvent(int howMany) {
  char comando[16];
  int counter=0;
  while(Wire.available()>0)
  {
    comando[counter++]=Wire.receive();
  }

  Serial.print("Evento: ");
  Serial.println(comando);
  Serial.print("N Motor: ");
  Serial.println(comando[0]);
  Serial.print("Comando: ");
  Serial.println(comando[1]=='+' ? "avançar" : comando[1]=='.' ? "parar" : "retroceder");
  if(comando[0]=='1') motor = motorBase1;
  else if(comando[0]=='2') motor = motorBase2;
  else if(comando[0]=='3') motor = motorBase3;
  else if(comando[0]=='4') motor = motorGarra;
  else return;
  //Serial.println("agora vai");
  if(comando[1]=='+') motor.andarParaFrente(255);
  else if(comando[1]=='-') motor.andarParaTras(255); 
  else if(comando[1]=='.') motor.parar();   
  //delay(500);
  //motor.parar();
  
}

