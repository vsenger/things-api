boolean DEBUG_LIGADO = true;

#define IDLE 1
#define SEGUIR_LINHA 2
#define DEMONSTRACAO 3
#define CAMPEONATO 4

int modo=IDLE;

void setup(){
  Serial.begin(115200);
  setupMotor();
  setupGarra();
  setupInit();
  
}

void loop(){
  andarFrente(
  /*if(modo==IDLE) modoZero();
  if(modo==SEGUIR_LINHA) seguirLinha();
  if(modo==CAMPEONATO) campeonato();
  if(modo==DEMONSTRACAO) demo1();*/
}




