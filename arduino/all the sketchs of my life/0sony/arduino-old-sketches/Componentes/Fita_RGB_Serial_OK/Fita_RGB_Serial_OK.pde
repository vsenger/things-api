#define RED 9
#define GREEN 10  
#define BLUE 11
#define ABRIDOR 12


void setup() {
  Serial.begin(9600);
  pinMode(RED, OUTPUT); //red
  pinMode(GREEN, OUTPUT); //green
  pinMode(BLUE, OUTPUT);
  pinMode(ABRIDOR, OUTPUT);

}

int red=200;
int green=200;
int blue=200;

void loop() {
  if(Serial.available()>0) 
  {
    lerSerial();
  }
  ligarStatus();

}
void lerSerial() {
  red = byte(Serial.read());
  delay(5);    
  green =  byte(Serial.read());
  delay(5);
  blue= byte(Serial.read());
  delay(5);

}
void ligarStatus() {
  analogWrite(RED, red);
  analogWrite(GREEN, green);
  analogWrite(BLUE, blue);
}
void desligar() {
  analogWrite(RED, 0);
  analogWrite(GREEN, 0);
  analogWrite(BLUE, 0);
}
/*
void ligar() {
 analogWrite(Q2,255);
 analogWrite(Q3,255);
 }*/




