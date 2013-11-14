int escuridao = 800; //quanto menor o numero, maior a iluminacao do ambiente.
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
    if(robot.mudouModo()) return; //metodo de libertacao do while se o robo mudar de modo
    luzAtual = robot.sensorLuz();
  }
  //ficou escuro...
  while(!estaClaro()) {
    robot.motores.re(4);
    robot.esperar(100);
    if(robot.mudouModo()) return;    
    luzAtual = robot.sensorLuz();
  }
  //no esta mais escuro..
  robot.motores.girar(4);
  robot.esperar(500); 
  robot.motores.movimentoAleatorio(4); 
  robot.esperar(500); 
}


