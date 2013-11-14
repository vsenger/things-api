#define TRANSISTOR_Q5 9
#define TRANSISTOR_Q4 6 //servo
#define TRANSISTOR_Q2 5 //servo 
#define BLUE  9
#define GREEN 6
#define RED   5

void setup()
{
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);  
  Serial.begin(115200);
  analogWrite(RED,255);
  delay(250);
  analogWrite(RED,0);
  delay(250);
  analogWrite(GREEN,255);
  delay(250);
  analogWrite(GREEN,0);
  delay(250);
  analogWrite(BLUE,255);
  delay(250);
  analogWrite(BLUE,0);
  delay(250);
  
}

void loop()
{
  if(Serial.available()) receiveCommand();
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
  if(comando[0]=='A') relayCommand(comando[1], comando[2]);
  if(comando[0]=='B') dimmer(comando);
  if(comando[0]=='C') RGB(comando);
}

void relayCommand(char operation, char relay) {
  if(operation=='+') {
    digitalWrite(relay=='A' ? 11 : 12, HIGH);
    if(relay=='C') digitalWrite(relay=='A' ? 12 : 11, HIGH);
  }
  else if(operation=='-') {
    digitalWrite(relay=='A' ? 11 : 12, LOW);
    if(relay=='C') digitalWrite(relay=='A' ? 12 : 11, LOW);
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


void dimmer(char* comando) {}


