#include "DevBot.h"
#include <Servo.h>

void setup()
{
  robot.configurar(programME_v2);
  robot.configurar(bateria_7_volts);
  robot.configurar(motor_6_volts);
  robot.configurar(servo_comum);
  robot.configurar(sensor_distancia_parallax);
  robot.portas.red=10;
  robot.portas.green=5;
  robot.portas.blue=9;
  
  robot.iniciar();

  robot.modo(0, comunicacaoPC);
  robot.modo(1, sensores);
  robot.modo(2, medoDoEscuro);  
  robot.modo(3, desviar);    
}

void loop()
{
  robot.loop();
}

void comunicacaoPC() 
{
  robot.controleRemoto();
}
void sensores() 
{
  robot.enviar("Idle - Parado");  
  robot.enviar("Luz");  
  robot.enviar(robot.sensorLuz());
  robot.enviar("Temperatura");  
  robot.enviar(robot.sensorTemperatura());
  robot.enviar("Distancia 1");  
  robot.enviar(robot.sensorDistancia(1));
  robot.enviar("Distancia 2");  
  robot.enviar(robot.sensorDistancia(2));
 
  robot.esperar(500);
}

void teste() {
  robot.mudarServo(90);
  robot.enviar("Frente");
  robot.motores.frente(4);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.esperar(1000);

  robot.mudarServo(0);
  robot.enviar("Girar");
  robot.motores.girar(4,0);
  robot.esperar(300);  
  robot.motores.parar();
  robot.esperar(1000);  

  robot.mudarServo(178);
  robot.enviar("Re");
  robot.motores.re(4);
  robot.esperar(1000);  
  robot.motores.parar();
  robot.esperar(1000);  

  robot.enviar("Girar");
  robot.motores.girar(4,1);
  robot.esperar(300);  
  robot.motores.parar();
  robot.esperar(5000);  

}



