int leds[] = {14,1,2,3,4,5,8,6,13};
int ledsStatus[9];

void setup() {
  Serial.begin(9600);
  for(int x=0;x<10;x++){
    ledsStatus[x]=0;
    pinMode(leds[x], OUTPUT);
    
  }
  
}

void loop() {
  if(Serial.available()>0) 
  {
    lerSerial();
  }
}

void lerSerial() {
   while(Serial.available()>0) {
     byte incoming = Serial.read();
     delay(20);
     ledsStatus[incoming] = !ledsStatus[incoming];
     digitalWrite(leds[incoming], ledsStatus[incoming]);
     
   }  
}



