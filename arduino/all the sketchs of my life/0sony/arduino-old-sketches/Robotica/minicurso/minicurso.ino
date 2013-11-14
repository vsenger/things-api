
//setup = configurar
void setup(){
  configurarMotor();
  configurarComunicacao();
}

//executar para sempre no robo
void loop(){
  imprimirTemperatura();
  delay(1000);
  imprimirLuz();
  delay(1000);
  
  
}

