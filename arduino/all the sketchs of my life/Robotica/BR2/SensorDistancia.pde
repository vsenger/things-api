int lerSensor(int numeroSensor) {
  int val = analogRead(numeroSensor);
  float volts = (5.0/1024.0) * val;
  Serial.print("Valor puro: ");
  Serial.println(val);
  Serial.print("Volts: ");
  Serial.println(volts);

  if(volts>1.3 && volts<1.5) return 10;
  if(volts>1.5 && volts<1.65) return 5;
  if(volts>1.65 && volts<2.05) return 3;
  if(volts>2.05) return 0;
}




