#include "DevBot.h"
#include "Servo.h"

void setup()
{
  robot.configurar(bateria_7_volts);
  robot.configurar(motor_6_volts);
  //robot.configurar(servo_comum);
  robot.configurar(programME_v2);
  robot.iniciar();
  robot.modo(0, controleRemoto);
  robot.modo(1, android);  
  robot.modo(2, testes);
  robot.modo(3, medoDoEscuro);
  robot.modo(4, desviar);  
}

void loop()
{
  robot.loop();
}

void controleRemoto() {
  robot.controleRemoto();
}

void testes() {
  robot.enviar("Idle - Parado");  
  robot.enviar("Luz");  
  robot.enviar(robot.sensorLuz());
  robot.enviar("Temperatura");  
  robot.enviar(robot.sensorTemperatura());
  robot.esperar(500);

  robot.enviar("Frente");
  robot.motores.frente(3);
  robot.esperar(3000);  
  robot.motores.parar();
  robot.esperar(2000);  

  robot.enviar("Re");
  robot.motores.re(3);
  robot.esperar(3000);  
  robot.motores.parar();
  robot.esperar(2000);  

  robot.enviar("Girar Sentido Horario");
  robot.motores.girar(3,0);
  robot.esperar(3000);  
  robot.motores.parar();
  robot.esperar(2000);  

  robot.enviar("Girar Sentido Anti-horario");
  robot.motores.girar(3,1);
  robot.esperar(3000);  
  robot.motores.parar();
  robot.esperar(10000);  


}


