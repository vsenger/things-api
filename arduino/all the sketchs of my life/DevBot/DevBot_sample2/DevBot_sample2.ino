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

}

void loop()
{
  robot.loop();
}

void parado() {}
void teste() {
  robot.enviar("Idle - Parado");  
  robot.enviar("Luz");  
  robot.enviar(robot.sensorLuz());
  robot.enviar("Temperatura");  
  robot.enviar(robot.sensorTemperatura());
  robot.esperar(500);
  robot.motores.frente(1);
  robot.esperar(1000);  
  robot.motores.frente(2);
  robot.esperar(1000);  
  robot.motores.frente(3);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.motores.re(2);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.esperar(10000);    
}

