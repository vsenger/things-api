#include <VirtualWire.h>

char comando[16]; 

void setup()
{
  Serial.begin(9600);
  pinMode(3, OUTPUT);
  vw_set_tx_pin(3);    
  vw_setup(2000); 
}

void loop()
{
  int counter=0;
  if(Serial.available()>0) {
    limpaComando();
    Serial.print("Lendo ");
    while(Serial.available()>0 && counter<=16 && comando[counter]!='\n') {
      comando[counter]=Serial.read();   
      Serial.print(comando[counter]);
      counter++;
      delay(10);
    }
    Serial.print("  - ");
    Serial.print(counter-1);
    Serial.println("bytes lidos");
    Serial.print("Enviando ");
    Serial.print(sizeof(comando));
    Serial.print(" bytes=");
    Serial.println(comando);
    enviar(comando);
    //processar(comando);
  }
}

void limpaComando() {
  for(int x=0;x<15;x++) {
    comando[x]=' ';
  }
}
void enviar(char* comando) {
  vw_send((uint8_t *) comando,strlen(comando));
}




