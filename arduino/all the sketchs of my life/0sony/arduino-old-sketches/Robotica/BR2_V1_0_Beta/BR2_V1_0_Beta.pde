#include <Motores.h>
#include <SensorLinha.h>
#include <ElectronKernel.h>
#include <LCD.h>
#include <Wire.h>

Motores motores; //default para motores Program-ME
SensorLinha sensorLinha((unsigned char[]){3, 4, 5}, 3);
LCD lcd;

void setup(){
  Serial.begin(115200);
  lcd.iniciar();
 
  Kernel.setup();

  Kernel.registerChangeModeListener(pararMotores);

  Kernel.registerMode(0, "Idle", padrao_setup);   
  Kernel.registerTask(0, padrao);   
  
  Kernel.registerMode(1, "Calibrate Sensor"); 
  Kernel.registerTask(calibrarSensorDeLinha);   

  Kernel.registerMode("Print Sensor"); 
  
  Kernel.registerTask(imprimirSensorDeLinha);  

}

void imprimirSensores() {
  lcd.imprimir(caso());
  sensorLinha.lerSensores();  
  imprimirSensorLinha();
  Kernel.wait(250);
  
}
void loop(){
  Kernel.loop();
}
void pararMotores() {
  motores.parar();
}
void padrao() {
}
void padrao_setup() {
  lcd.imprimir("Modo Padrao - IDLE");  
}
void testeMotor() {
  lcd.imprimir("Modo Teste Motor");
  lcd.imprimir("Para Tras",1,0); 
  motores.andarParaTras(100);
  Kernel.wait(2000);
  if(Kernel.abortMode()) return;
  lcd.imprimir("Para Frente",1,0);  
  motores.andarParaFrente(100);
  Kernel.wait(2000);
  if(Kernel.abortMode()) return;
  lcd.imprimir("Girando horario",1,0);
  motores.girarSentidoHorario(100);
  Kernel.wait(2000);
  if(Kernel.abortMode()) return;
  lcd.imprimir("Girando antihorario",1,0);  
  motores.girarSentidoAntiHorario(100);
  Kernel.wait(2000);
  motores.parar();
}  

