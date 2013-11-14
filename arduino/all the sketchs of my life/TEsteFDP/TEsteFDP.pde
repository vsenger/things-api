void setup() {
  Serial.begin(115200);
}

void loop() {
  Serial.println(analogRead(2));
      float temperatura = (analogRead(2) * 0.00488);  // 5V / 1023 = 0.00488 (precisão do A/D)
    temperatura = temperatura * 100; //Converte milivolts para graus celcius, lembrando que a cada 10mV equivalem a 1 grau celcius
  
  Serial.println(temperatura);
  delay(250);
}

