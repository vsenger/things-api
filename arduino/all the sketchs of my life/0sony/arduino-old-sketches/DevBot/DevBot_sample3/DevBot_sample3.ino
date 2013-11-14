#include "DevBot.h"
#include "Servo.h"

void setup()
{
  robot.iniciar();
  robot.configurar(bateria_7_volts);
  robot.configurar(motor_6_volts);
  robot.configurar(programME_v2);
  robot.configurar(sensor_distancia_parallax);
  robot.modo(0, parado);
  robot.modo(1, teste);
  robot.modo(2, comunicacaoPC);
  robot.modo(3, medoDoEscuro);  
}

void loop()
{
  robot.loop();
}

void comunicacaoPC() {
  if(robot.recebeuDados()) {
    char* comando=robot.receber();
    delay(500);
    Serial.println("Comando aqui: ");
    Serial.println(comando);
    
  }
}
void parado() {
  robot.enviar("Idle - Parado");  
  robot.enviar("Luz");  
  robot.enviar(robot.sensorLuz());
  robot.enviar("Temperatura");  
  robot.enviar(robot.sensorTemperatura());
  robot.esperar(500);
}

void teste() {
  robot.enviar("Frente");
  robot.motores.frente(2);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.esperar(1000);  

  robot.enviar("Girar");
  robot.motores.girar(2,0);
  robot.esperar(300);  
  robot.motores.parar();
  robot.esperar(1000);  

  robot.enviar("Re");
  robot.motores.re(2);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.esperar(1000);  

  robot.enviar("Girar");
  robot.motores.girar(2,1);
  robot.esperar(300);  
  robot.motores.parar();
  robot.esperar(5000);  

}


