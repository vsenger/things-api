void configurarComunicacao() {
  Serial.begin(115200);
}


void imprimirLuz() {
  Serial.print("Luz: ");
  Serial.println(analogRead(3));
}

void imprimirTemperatura() {
  float tempC = analogRead(2);           //read the value from the sensor
  tempC = (5.0 * tempC * 100.0)/1024.0;  //convert the analog data to temperature
  Serial.print("Temperatura: ");
  Serial.println((byte)tempC);   
}

void esperar(long mssegundos) {
  delay(mssegundos);
}

