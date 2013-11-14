  #include <Wire.h>


void setup()
{
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);  
  Serial.begin(9600);
  Wire.begin(65);		    // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event

}
void receiveEvent(int howMany)
{
  char comando[16];
  int counter=0;
  while(1 < Wire.available()) // loop through all but the last
  {
    char c = Wire.receive(); // receive byte as a character
    comando[counter++]=c;
    Serial.print(c);	   // print the character
  }
  int x = Wire.receive();    // receive byte as an integer
  Serial.println(x);	   // print the integer

  Serial.println("Evento");
  Serial.println(howMany);
  if(comando[1]=='+') {
    Serial.println("Ligando relê");
    digitalWrite(comando[2]=='A' ? 3 : 4, HIGH);
  }
  else if(comando[1]=='-') {
    Serial.println("Desligando relê");
    digitalWrite(comando[2]=='A' ? 3 : 4, LOW);
  }  

  /*char comando[16];
  int c=0;
  while(1 < Wire.available()) // loop through all but the last
  {
    if(c=0) Wire.receive(); //skip first byte (my ID)
    comando[c++] = Wire.receive(); // receive byte as a character

  }
  Serial.println(comando); 
  if(comando[0]=='+') {
    Serial.println("Ligando relê");
    digitalWrite(comando[1]=='A' ? 3 : 4, HIGH);
  }
  else if(comando[0]=='-') {
    Serial.println("Desligando relê");
    digitalWrite(comando[1]=='A' ? 3 : 4, LOW);
  } */   
} 

void loop()
{
  Serial.println("esperando...");
  delay(1000);
  
}



