#define RELAY1 2
#define RELAY2 4

#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 

#define BLUE  9
#define GREEN 6
#define RED   5

#define TEMPERATURE 2
#define LIGHT 3
#define PING_PIN 3

#define PORTAO_1 7
#define PORTAO_2 8

#define LED 13

long duration, inches, cm;
boolean connected = false;

void setup()
{
  Serial.begin(115200);
  pinMode(LED, OUTPUT);
  pinMode(RELAY1, OUTPUT);
  pinMode(RELAY2, OUTPUT);  
  pinMode(PORTAO_1, OUTPUT);
  pinMode(PORTAO_2, OUTPUT);  

}

void loop()
{
  if(Serial.available()) receiveCommand();
} 

void handShake() {
  //Serial.print("ID 0|bluetooth prototype device|6|A|relay|B|dimmer|C|RGB|E|temperature|F|distance|S|allsensors");
  Serial.print("ID 1|USB prototype device|6|A|lamp|B|wallsocket|C|temperature|D|light|S|allsensors|R|RGB");
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
  if(comando[0]=='R') RGB(comando);
  if(comando[0]=='D') readLight();
  if(comando[0]=='F') readDistance();
  if(comando[0]=='C') readTemperature();
  if(comando[0]=='S') readAllSensors();  
  if(comando[0]=='X') shutdown();  
}

void relayCommand(char operation, char relay) {
  if(operation=='+') {
    digitalWrite(relay=='A' ? RELAY1 : RELAY2, HIGH);
  }
  else if(operation=='-') {
    digitalWrite(relay=='A' ? RELAY1 : RELAY2, LOW);
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


void readDistance() {
  readPing();
  sendToPC(cm);
}

void sendToPC(String dado) {
  Serial.print(dado);
  Serial.flush();
}  

void readPing()
{
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  duration = pulseIn(PING_PIN, HIGH);
  inches = microsecondsToInches(duration);
  cm = microsecondsToCentimeters(duration);
  Serial.println(cm);
}

long microsecondsToInches(long microseconds)
{
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds)
{
  return microseconds / 29 / 2;
}





