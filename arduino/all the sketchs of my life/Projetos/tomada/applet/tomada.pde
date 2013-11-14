#define TOMADA 18
#define POTENCIOMETRO_1 1
#define LDR 0
void setup() {
  pinMode(POTENCIOMETRO_1, INPUT);
  pinMode(TOMADA, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  int ldr = analogRead(LDR);
  if(ldr>analogRead(POTENCIOMETRO_1)) {
    digitalWrite(TOMADA, HIGH);
  }
  else {
    digitalWrite(TOMADA, LOW);
  }    
  Serial.println(ldr);
  delay(200);

}
