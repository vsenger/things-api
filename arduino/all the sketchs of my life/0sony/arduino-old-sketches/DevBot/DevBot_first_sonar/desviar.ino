int distancia;
void desviar() {
  distancia = robot.sensorDistancia1();
  //esta claro
  /*while(estaClaro()) {
    robot.motores.frente(4);
    robot.esperar(100);
    if(robot.mudouModo()) return; //metodo de libertacao do while se o robo mudar de modo
    luzAtual = robot.sensorLuz();
  }*/
  robot.motores.girar(4);
  robot.esperar(500); 
  robot.motores.movimentoAleatorio(4); 
  robot.esperar(500);   
}

