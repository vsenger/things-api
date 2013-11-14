int escuridao = 700; //quanto menor o numero, maior a iluminacao do ambiente.
int luzAtual;

boolean estaClaro() {
  return luzAtual<escuridao;
}

void medoDoEscuro() {
  luzAtual = robot.sensorLuz();
  //esta claro
  while(estaClaro()) {
    robot.motores.frente(4);
    robot.esperar(100);
    luzAtual = robot.sensorLuz();
  }
  //ficou escuro...
  while(!estaClaro()) {
    robot.motores.re(4);
    robot.esperar(100);
    luzAtual = robot.sensorLuz();
  }
  //no esta mais escuro..
  robot.motores.girar(4);
  robot.esperar(500); 
}


