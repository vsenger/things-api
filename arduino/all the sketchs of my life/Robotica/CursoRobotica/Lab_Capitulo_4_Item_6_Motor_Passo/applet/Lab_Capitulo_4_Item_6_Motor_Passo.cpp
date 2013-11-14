
  #define P0 0    // Porta Digital 0   -  Chave  
  #define P1 1    // Porta Digital 1   -  Led 2
  #define P2 2    // Porta Digital 2   -  Led 3
  #define P3 3    // Porta Digital 3   -  Led 4  -  PWM  
  #define P4 4    // Porta Digital 4   -  Led 5
  #define P5 5    // Porta Digital 5   -  Led 6  -  PWM
  #define P6 6    // Porta Digital 6   -  Transistor Q3  -  PWM
  #define P7 7    // Porta Digital 7   -  Led 8
  #define P8 8    // Porta Digital 8   -  Led 7
  #define P9 9    // Porta Digital 9   -  Transistor Q2  -  PWM
  #define P10 10  // Porta Digital 10  -  Servo1  -  PWM
  #define P11 11  // Porta Digital 11  -  Servo2  -  PWM
  #define P12 12  // Porta Digital 12  -  Buzzer
  #define P13 13  // Porta Digital 13  -  Led 9
  #define P14 14  // Porta Analogica 0 -  Led 1
  #define P15 15  // Porta Analogica 1 -  JP6 - POT 1
  #define P16 16  // Porta Analogica 2 -  JP7 - POT 2
  #define P17 17  // Porta Analogica 3 -  Transistor Q5
  #define P18 18  // Porta Analogica 4 -  Transistor Q4
  #define P19 19  // Porta Analogica 5 -  LDR
  
  #define AP0 14  // Porta Analogica 0 Usando como digital -  Led 1
  #define AP1 15  // Porta Analogica 1 Usando como digital -  JP6 - POT 1
  #define AP2 16  // Porta Analogica 2 Usando como digital -  JP7 - POT 2
  #define AP3 17  // Porta Analogica 3 Usando como digital -  Transistor Q5
  #define AP4 18  // Porta Analogica 4 Usando como digital -  Transistor Q4
  #define AP5 19  // Porta Analogica 5 Usando como digital -  LDR

  #define A0 0    // Porta Analogica 0 -  Led 1
  #define A1 1    // Porta Analogica 1 -  JP6 - POT 1
  #define A2 2    // Porta Analogica 2 -  JP7 - POT 2
  #define A3 3    // Porta Analogica 3 -  Transistor Q5
  #define A4 4    // Porta Analogica 4 -  Transistor Q4
  #define A5 5    // Porta Analogica 3 -  LDR

  #define LED_1 14
  #define LED_2 1
  #define LED_3 2
  #define LED_4 3
  #define LED_5 4
  #define LED_6 5
  #define LED_7 8
  #define LED_8 7
  #define LED_9 13

  #define Q2 9    //PWM
  #define Q3 6    //PWM
  #define Q4 18
  #define Q5 17

  #define LDR 5
  #define JP6 1
  #define JP7 2
  #define CHAVE 0 
  #define BOTAO 0   
  #define BUZZER  12 
  #define SERVO_1 10
  #define SERVO_2 11

  #define TEMPO_ATIVADO 100
  #define NUM_PASSOS 4
#include "WProgram.h"
void setup ();
void escreve_motor (boolean b_q2, boolean b_q3, boolean b_q4, boolean b_q5);
void seleciona_passo ();
void avanca_passo();
void recua_passo();
void loop ();
int ja_ativou = 0;
int contando_tempo = 0;
unsigned long inicio = 0;

int passo = 0;
int anda = 0;

void setup () {

  pinMode(CHAVE, INPUT);      // configura o pino como entrada
  pinMode(Q2, OUTPUT);
  pinMode(Q3, OUTPUT);
  pinMode(Q4, OUTPUT);
  pinMode(Q5, OUTPUT);
  pinMode(LED_9, OUTPUT);
}

void escreve_motor (boolean b_q2, boolean b_q3, boolean b_q4, boolean b_q5) {

  digitalWrite(Q2, b_q2);
  digitalWrite(Q3, b_q3);
  digitalWrite(Q4, b_q4);
  digitalWrite(Q5, b_q5);

}


void seleciona_passo (){

  boolean b_q2, b_q3, b_q4, b_q5;
  
/*
  black  brown  orange  yellow
    A      A/     B       B/
    Q2     Q3     Q4      Q5  
1   1      0      0       1
2   1      0      1       0
3   0      1      1       0
4   0      1      0       1

Q2 =  A
Q3 =  A/
Q4 =  B
Q5 =  B/

*/
    switch (passo) {
      case 0:
            b_q2 = true;
            b_q3 = false;
            b_q4 = false;
            b_q5 = true;
            break;
      case 1:
            b_q2 = true;
            b_q3 = false;
            b_q4 = true;
            b_q5 = false;      
            break;
      case 2:
            b_q2 = false;
            b_q3 = true;
            b_q4 = true;
            b_q5 = false;      
            break;
      case 3:
            b_q2 = false;
            b_q3 = true;
            b_q4 = false;
            b_q5 = true;      
            break;
    }
    escreve_motor (b_q2, b_q3, b_q4, b_q5);
}

void avanca_passo(){
    
    passo++;
    passo = passo % NUM_PASSOS;
    seleciona_passo();
}

void recua_passo(){
    passo--;
    if (passo < 0) {
        passo = 3;
    }
    //passo = passo % NUM_PASSOS;
    seleciona_passo();
}


void loop () {
  
  int leitura = digitalRead(CHAVE);
  if (leitura == LOW) {
      anda = !anda;
      delay (300);
  } 
  /*else {
    delay (100);
    if (leitura == HIGH){
      //anda = 0;
    }
  }*/
  
  if (anda == 1) {
    if (inicio == 0) {
      inicio = millis();
    }
    if ((millis() - inicio) > 100) {
      avanca_passo();
      //delay(100);
      inicio = millis();
    }
  }
  
  
  /*if (leitura == LOW && ja_ativou == 0 && contando_tempo == 0) {
      digitalWrite(LED_9, HIGH);
      //digitalWrite(Q4,    HIGH);
      avanca_passo();
      ja_ativou = 1;
      contando_tempo = 1;
      inicio = millis();
      //delay (1000);
  } else {
        if (leitura == HIGH){
          ja_ativou = 0;
        }
  }
  
  if (contando_tempo == 1) {
      if ( (millis() - inicio) > TEMPO_ATIVADO ){
          contando_tempo = 0;
          digitalWrite(LED_9, LOW);
          //digitalWrite(Q4,    LOW);
      }
  }*/
}


int main(void)
{
	init();

	setup();
    
	for (;;)
		loop();
        
	return 0;
}

