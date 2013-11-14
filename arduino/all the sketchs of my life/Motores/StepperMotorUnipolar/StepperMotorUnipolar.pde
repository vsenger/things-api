#define Q2 9
#define Q3 6
#define Q4 18
#define Q5 17
#define NUM_PASSOS 4

void setup() {
  Serial.begin(9600);
  pinMode(Q2, OUTPUT);
  pinMode(Q3, OUTPUT);
  pinMode(Q4, OUTPUT);
  pinMode(Q5, OUTPUT);
  escreve_motor(false,false,false,false);
}

void loop(){
  Serial.println("passo 0");
  seleciona_passo(0);
  delay(10);

  Serial.println("passo 1");
  seleciona_passo(1);
  delay(10);
  Serial.println("passo 2");
  seleciona_passo(2);
  delay(10);
  Serial.println("passo 3");
  seleciona_passo(3);
  delay(10);


}
void escreve_motor (boolean b_q2, boolean b_q3, boolean b_q4, boolean b_q5) {
  digitalWrite(Q2, b_q2);
  digitalWrite(Q3, b_q3);
  digitalWrite(Q4, b_q4);
  digitalWrite(Q5, b_q5);
}
void seleciona_passo (int passo){
  boolean b_q2, b_q3, b_q4, b_q5;
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



