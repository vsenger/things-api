#define TOMADA 2
void setup() {
  pinMode(TOMADA, OUTPUT);
  Serial.begin(9600);
}

void loop() {
    digitalWrite(TOMADA, HIGH);
  delay(2000);
    digitalWrite(TOMADA, LOW);
  delay(2000);

}
