int distancia;
void desviar() {
  robot.mudarServo(90);
  distancia = robot.sensorDistancia1();
  if(distancia<10)
    robot.motores.girar(4);
    robot.esperar(500); 
    robot.motores.movimentoAleatorio(4); 
    robot.esperar(500);
  }   
  robot.motores.andar(4);
}

