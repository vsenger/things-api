#define LED 6
boolean connected;

void setup(){
  setupMotor();
  Serial.begin(115200);
}

void loop(){
  if(Serial.available()) receiveCommand();
  
}
void handShake() {
  Serial.print("ID 2|robot|4|f|frente|t|tras|d|direita|e|esquerda");
  Serial.flush();

  long m = millis();
  while(Serial.available()<1) {
    //waiting for data
    if(millis()-m>2000) {
      //timeout
      return;
    }
  }
  if(Serial.available()>0) { 
    char c=Serial.read();
    digitalWrite(LED, true);      
    connected= true;
  }

}
void shutdown() {
  digitalWrite(LED, false); 
  connected=false;  
}
  
void receiveCommand() {
  char comando[16];
  int counter=0;
  while(Serial.available()>0) // loop through all but the last
  {
    char c = Serial.read(); // receive byte as a character
    delay(5);
    comando[counter++]=c;
  }
  if(comando[0]=='j' || comando[0]=='J') handShake();
  if(comando[0]=='f') frente();
  if(comando[0]=='t') tras();
  if(comando[0]=='d') direita();
  if(comando[0]=='e') esquerda();
  if(comando[0]=='X') shutdown();  
}

void frente() {
   andarFrente(2000, 3);
   parar();
}
void tras() {
   andarTras(2000, 3);
   parar();   
}
void esquerda() {
   virarEsquerda(2000, 3);
   parar();   
}
void direita() {
   virarDireita(2000, 3);
   parar();   
}

