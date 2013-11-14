int distancia;
void desviar() {
  robot.mudarServo(90);
  distancia = robot.sensorDistancia(1);
  if(distancia>0 && distancia<15) {
    robot.motores.girar(3);
    robot.esperar(700); 
    robot.motores.movimentoAleatorio(3); 
    robot.esperar(500);
  }   
  robot.motores.frente(3);
}
