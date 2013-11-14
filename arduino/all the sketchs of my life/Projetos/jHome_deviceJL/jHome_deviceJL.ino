
#define RELAY1 14
#define RELAY2 15
#define RELAY3 16
#define RELAY4 17

#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 

#define BLUE  9
#define GREEN 6
#define RED   5

#define TEMPERATURE 2
#define LIGHT 3
#define PING_PIN 3

#define LED 13

long duration, inches, cm;
boolean connected = false;

void setup()
{
  Serial.begin(115200);
  pinMode(LED, OUTPUT);
  pinMode(RELAY1, OUTPUT);
  pinMode(RELAY2, OUTPUT);  
  pinMode(RELAY3, OUTPUT);
  pinMode(RELAY4, OUTPUT);  

}

void loop()
{
  if(Serial.available()) receiveCommand();
} 

void handShake() {
  //Serial.print("ID 0|bluetooth prototype device|6|A|relay|B|dimmer|C|RGB|E|temperature|F|distance|S|allsensors");
  Serial.print("ID 4|USB prototype device|4|A|wallsock1|B|wallsocket2|C|wallsock3|D|wallsock4");
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
  if(comando[0]=='A') relayCommand(comando[1], 'A');
  if(comando[0]=='B') relayCommand(comando[1], 'B');
  if(comando[0]=='C') relayCommand(comando[1], 'C');
  if(comando[0]=='D') relayCommand(comando[1], 'D');
  if(comando[0]=='X') shutdown();  
}

void relayCommand(char operation, char relay) {
  if(operation=='+') {
    digitalWrite(relay=='A' ? RELAY1 : relay=='B' ? RELAY2 : relay=='C' ? RELAY3 : RELAY4, HIGH);
  }
  else if(operation=='-') {
    digitalWrite(relay=='A' ? RELAY1 : relay=='B' ? RELAY2 : relay=='C' ? RELAY3 : RELAY4, LOW);
  }  

}  

void RGB(char* comando) {
  int port = 0;
  if(comando[1]=='R') port = RED;
  if(comando[1]=='G') port = GREEN;  
  if(comando[1]=='B') port = BLUE;  
  int amount = map(atoi(&comando[2]),0,9,0,255);
  analogWrite(port, amount);
}


void dimmer(char* comando) {
}

void readLight() {
  String toSend = String(analogRead(LIGHT));
  sendToPC(toSend);  
}

void readTemperature() {
  String toSend = String(analogRead(TEMPERATURE));  
  sendToPC(toSend);
}

void readAllSensors() {
  //readLight();
  //Serial.print("|");
  //delay(2);
  readTemperature();
  Serial.print("|");
  readLight();
  
  //Serial.print("|");
  //String toSend3 = String(cm);  
  //Serial.print(cm);
  //Serial.flush();
}




void sendToPC(String dado) {
  Serial.print(dado);
  Serial.flush();
}  

