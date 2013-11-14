#define Q2 9
#define Q3 6
#define Q4 18



void setup() {
  Serial.begin(9600);
  pinMode(Q2, OUTPUT); //red
  pinMode(Q3, OUTPUT); //green
  pinMode(Q4, OUTPUT);
}

int red;
int green;
int blue;

void loop() {


  /*if(Serial.available()>0) 
  {
    red = byte(Serial.read());
    delay(20);
    
    green =  byte(Serial.read());
    delay(20);
    blue= byte(Serial.read());
    delay(20);
    //red = red = -1 ? 0 : red;
    //blue = blue = -1 ? 0 : blue;
    //green = green = -1 ? 0 : green;
  }
  analogWrite(Q2, red);
  //analogWrite(Q3, green);
  //digitalWrite(Q4, blue);
  */
  for(int x=0;x<255;x++) {    
    analogWrite(Q2, x);
    delay(10);
  }
  for(int x=0;x<255;x++) {    
    analogWrite(Q3, x);
    delay(10);
  }
  desligar();
  delay(500);
  ligar();
  delay(500);
  desligar();
  delay(500);
  ligar();
  delay(500);
  desligar();
  delay(500);
  ligar();
  delay(500);
  
}
void desligar() {
  analogWrite(Q2,0);
  analogWrite(Q3,0);
}
void ligar() {
  analogWrite(Q2,255);
  analogWrite(Q3,255);
}



