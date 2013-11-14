#define BLUE 8
#define GREEN 9
#define RED 17

void setup() {
  pinMode(5, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(17, OUTPUT);
}

void loop() {
  int op = random(1,5);
  piscaMaluco(20,20);

  switch(op) {  
  case 1:
    piscaMaluco(random(50,100),random(20,50));        
    break;
  case 2:
    ligaG();
    break;
  case 3:
    desligaG();
    break;
  case 4:
    liga();
    break;
  case 5:
    comLDR();
    break;
  case 6:
    piscaMaluco(random(10,100), random(10,100));
    break;
  }

}
void comLDR() {

  analogWrite(escolheLed(), analogRead(5));
  delay(random(1000,3000));

}

int escolheLed() {
  int r = random(1,3);
  if(r==1) return 8;
  if(r==2) return 9;
  if(r==3) return 17;
}
void desligaTudo() {
  digitalWrite(RED, LOW);
  digitalWrite(GREEN, LOW);
  digitalWrite(BLUE, LOW);
}
void liga() {
  desligaTudo(); 
  int l = escolheLed();
  digitalWrite(l,HIGH);

}

void pisca(int led, int repeticoes, int d) {
  desligaTudo();
  for(int x=0;x<repeticoes;x++) {
    digitalWrite(led, HIGH);
    delay(d); 
    digitalWrite(led, LOW);
    delay(d); 
  }
}

void piscaMaluco(int repeticoes, int d) {
  desligaTudo();
  int led;
  for(int x=0;x<repeticoes;x++) {
    led = escolheLed();
    digitalWrite(led, HIGH);
    delay(d); 
    digitalWrite(led, LOW);
    delay(d);
  }
}

void ligaG() {
  desligaTudo();
  for(int x=0;x<255;x++) {
    analogWrite(9, x);

    delay(10);
    analogWrite(9,0);
  }
}

void ligaRB() {
  desligaTudo();
  digitalWrite(8, HIGH);
  digitalWrite(17, HIGH);
}

void desligaG() {
  for(int x=255;x>-1;x--) {
    analogWrite(9, x);
    delay(10);
  }
}







