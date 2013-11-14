#include <TimerOne.h>
#include <MsTimer2.h>
//define portas associadas ao display 7seg
//int displaySeg[] = {a, b, c, d, e, f, g, pd};
int displaySeg[] = {6, 9, 17, 15, 14, 5, 18, 12};
//int displayPorts[] = {Anodo1, Anodo2, Anodo3};
int displayPort[] = {7, 4, 3};

// define padroes do display 7SEG, de 0 - 9 e apagado
// 1 = segmento apagado, 0 = segmento aceso
byte seven_seg_digits[11][8] =  {
  { 0,0,0,0,0,0,1,1 },  // = 0
  { 1,0,0,1,1,1,1,1 },  // = 1
  { 0,0,1,0,0,1,0,1 },  // = 2
  { 0,0,0,0,1,1,0,1 },  // = 3
  { 1,0,0,1,1,0,0,1 },  // = 4
  { 0,1,0,0,1,0,0,1 },  // = 5
  { 0,1,0,0,0,0,0,1 },  // = 6
  { 0,0,0,1,1,1,1,1 },  // = 7
  { 0,0,0,0,0,0,0,1 },  // = 8
  { 0,0,0,1,1,0,0,1 },   // = 9
  { 1,1,1,1,1,1,1,1 }   // = blank
};

int sensorDistancia = 2;  // porta associada ao sensor distancia IR
int triacGatePin = 10 ;  // porta associada ao gate do TRIAC

int distancia = 0 ;  // variavel para armazenar distancia
int power= 0 ;  //inicializa variavel que controla potencia na lampada com 0 (lampada apagada)
int centena = 10 ;  // armazena informacao a ser apresentada no display de centena (inicializa com 10 = apagado)
int dezena = 10 ;  // armazena informacao a ser apresentada no display de dezena (inicializa com 10 = apagado)
int unidade = 10 ;  // armazena informacao a ser apresentada no display de unidade (inicializa com 10 = apagado)

void setup() {  
  Serial.begin(19200);
  pinMode(triacGatePin, OUTPUT);
  for (int i=0 ; i<=3 ; i++) {  
  pinMode(displayPort[i], OUTPUT);  
  }
  for (int j=0 ; j<=7 ; j++) {  
  pinMode(displaySeg[j], OUTPUT);  
  }
  attachInterrupt(0, zeroCrossInterrupt, CHANGE);  //associa interrupcao INT0 com funcao "zeroCrossInterrupt"

  MsTimer2::set(200, leSensor); // inicializa Timer2 com 200 milisegundos (tempo entre leituras do sensor de dintancia)
  MsTimer2::start();  // start do timer2
}

void loop() {
  atualizaDisplay(unidade, dezena, centena);  // atualiza informacoes nos displays de 7 segmentos
//*
  //verifica se veio comando remoto pela serial
  if (Serial.available() > 0) {
    power = Serial.read();
    Serial.print(power, BYTE);
  }  
  //finaliza aqui a verificacao de dados na serial

}

void atualizaDisplay(int uni, int dez, int cen) {
  for (int k = 0; k < 8; ++k) {
    digitalWrite(displaySeg[k], seven_seg_digits[uni][k]);
  }
  digitalWrite(displayPort[2], HIGH);
  delay (1);
  digitalWrite(displayPort[2], LOW);
  
  for (int k = 0; k < 8; ++k) {
    digitalWrite(displaySeg[k], seven_seg_digits[dez][k]);
  }
  digitalWrite(displayPort[1], HIGH);
  delay (1);
  digitalWrite(displayPort[1], LOW);
  
  for (int k = 0; k < 8; ++k) {
    digitalWrite(displaySeg[k], seven_seg_digits[cen][k]);
  }
  digitalWrite(displayPort[0], HIGH);
  delay (1);
  digitalWrite(displayPort[0], LOW);
  
}

void zeroCrossInterrupt(){  // trata interrupcao INT0 
  if(power > 0) {
  long dimtime = int(map(power,0,100,8000,150));  // calcula o tempo de delay para o disparo do TRIAC
  Timer1.initialize (dimtime);  // inicializa o TIMER1 com o delay calculado
  Timer1.attachInterrupt(gateTRIAC);  //associa a funcao gateTRIAC com  Interrupcao do TIMER1
  Timer1.start();  // inicia contagem TIMER1
  }  else {
      digitalWrite(triacGatePin, LOW);   // mantem gate do TRIAC desativado.  
      Timer1.stop();
  }
}

void gateTRIAC () {  // trata interrupcao do TIMER1 gerando pulso no gate do TRIAC
    digitalWrite(triacGatePin, HIGH);  // dispara o Triac
    delayMicroseconds(15);  // aguarda 15 microsegundos para garantir disparo do TRIAC
    digitalWrite(triacGatePin, LOW);   // desabibilta gate do TRIAC 
    Timer1.stop();
}

void leSensor() {
  distancia = analogRead(sensorDistancia);  // le sensor de distancia
  //Serial.println(distancia);
  distancia = map(distancia,70,600,120,0);  // mapeia distancia lida para uma faixa de potencia entre 0 e 120
  if (distancia < 0) {
    distancia = 0;
  }

  if (distancia >=0 && distancia <= 100) {  // se distancia <= a 100 atualiza potencia
    power=distancia;
  }
  

  
    centena = int(power / 100);  // atualiza variaveis que contem informacoes dos displays
    dezena = int((power - (centena*100)) / 10);
    unidade = int(power - (centena*100)-(dezena*10));
    if (centena == 0 && dezena == 0){ 
      centena = 10;
      dezena = 10;
    }
    if (centena == 0){
      centena = 10;
    }
}

