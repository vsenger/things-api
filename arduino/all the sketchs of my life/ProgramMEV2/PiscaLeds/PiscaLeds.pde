int leds[] = {
  13, 12, 8, 7, 4};

void setup() {
  Serial.begin(9600);
  for(int x=0;x<5;x++) {
    pinMode(leds[x], OUTPUT);
  }  
}

void loop() {
  for(int x=0;x<=4;x++) {
    digitalWrite(leds[x], HIGH);
    delay(100);    
    digitalWrite(leds[x], LOW);
    delay(100);    
  }  
}
