void setup() {
  Serial.begin(9600);
  
}

void loop(){

  int valor = analogRead(1);
  Serial.println(valor);
  Serial.println(valor*1.6);
  Serial.println("---------------");
  
  delay(300);
}

