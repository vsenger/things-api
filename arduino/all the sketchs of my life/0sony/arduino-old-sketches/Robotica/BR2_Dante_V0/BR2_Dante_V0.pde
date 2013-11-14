#include <Motores.h>
#include <ElectronKernel.h>
#include <SensorLinha.h>

SensorLinha sensorLinha((unsigned char[]){14, 15, 3, 4, 5}, 5);

Motores motores; //default para motores Program-ME

void setup(){
  Serial.begin(115200); 
  
  Kernel.setup(INTERRUPTION);
  Kernel.registerChangeModeListener(pararMotores);

  Kernel.registerMode(0, "Idle");   
  Kernel.registerTask(0, padrao);   
  
  Kernel.registerMode(1, "Testar Motores",testarMotores); 
  Kernel.registerTask(1, padrao);   

  Kernel.registerMode(2, "Testar Seguidor",testarSeguidor); 
  Kernel.registerTask(2, padrao);   

}

void loop(){
  Kernel.loop();
}

void testarSeguidor() {
  //calibrar uma so vez
  sensorLinha.calibrarSensores();   
  //ler os sensores
  sensorLinha.lerSensores();

  Serial.print(sensorLinha.sensor[0]);
  Serial.print(" ");
  Serial.print(sensorLinha.sensor[1]);
  Serial.print(" ");
  Serial.print(sensorLinha.sensor[2]);
  Serial.print(" ");
  Serial.print(sensorLinha.sensor[3]);
  Serial.print(" ");
  Serial.print(sensorLinha.sensor[4]);
  Serial.print(" ");
  
}

void pararMotores() {
  motores.parar();
}
void padrao() {
  Serial.println("modo padrao");
  delay(500);
}
void padrao_setup() {
}
void testarMotores() {
  Serial.println("iniciando teste motores");
  motores.andarParaTras(200);
  delay(1000);
  //Kernel.wait(2000);
  //if(Kernel.abortMode()) return;
  motores.andarParaFrente(200);
  delay(1000);

  //Kernel.wait(2000);
  //if(Kernel.abortMode()) return;
  motores.girarSentidoHorario(200);
  //Kernel.wait(2000);
  //if(Kernel.abortMode()) return;
  delay(1000);
 
  motores.girarSentidoAntiHorario(200);
  //Kernel.wait(2000);
  delay(1000);
  
  motores.parar();
  //motores.ajustarVelocidade(50,100);
  
}  
void imprimirSensores() {
  sensorLinha.lerSensores();  
  imprimirSensorLinha();
  Kernel.wait(250);
  
}
int lerSensor(int numeroSensor) {
  int val = analogRead(numeroSensor);
  float volts = (5.0/1024.0) * val;
  Serial.print("Valor puro: ");
  Serial.println(val);
  Serial.print("Volts: ");
  Serial.println(volts);

  if(volts>1.3 && volts<1.5) return 10;
  if(volts>1.5 && volts<1.65) return 5;
  if(volts>1.65 && volts<2.05) return 3;
  if(volts>2.05) return 0;
}

