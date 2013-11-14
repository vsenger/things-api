/*
0.5 - 2.5 milisegundos funcionou bem para o servo.
Tente valores diferentes de acordo com o seu servo
*/
int servoPino = 10;        // Pino de controle para o servo1
int tempoMinimo = 500;     // Posição mínima do servomotor
int tempoMaximo = 2500;    // Posição máxima do servomotor
int posicao_servo = 0;         // posição do servo
int valor_posicao = tempoMinimo;
int i = 0;

long ultimoControle = 0;       // tempo em milisegundos em que ocorreu o último controle
long ultima_atualizacao = 0;

int intervaloControle = 20;    // intervalo de tempo entre os pulsos de controle
int tempo_movimentacao = 500;

void setup() {
  pinMode(servoPino, OUTPUT);
  posicao_servo = 0;           // Coloca o servo na posicao minima
}

void loop() {

 if (millis() - ultima_atualizacao >= tempo_movimentacao) {
   i+=5;
   i = i % 180;
   posicao_servo = i;
   ultima_atualizacao = millis();
 }
 valor_posicao = map(posicao_servo,0, 180, tempoMinimo, tempoMaximo);    // mapeia o valor de 0 a 180
                                                                      // para a faixa entre tempo Minimo e Maximo
// atualiza o servo se o intervalo de tempo para controle passou
 if (millis() - ultimoControle >= intervaloControle) {
   digitalWrite(servoPino, HIGH);          // Liga o sinal de controle
   delayMicroseconds(valor_posicao);       // Tempo em que mantem o sinal alto para controlar o servo
   digitalWrite(servoPino, LOW);           // Desliga o sinal de controle do servo
   ultimoControle = millis();              // atualiza o momento do ultimo controle (em milissegundos)
 }
}

