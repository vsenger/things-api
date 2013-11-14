
int lerPingComMedia() {
  int d1,d2,d3;
  d1 = analogRead(1);
  delay(5);
  d2 = analogRead(1);
  delay(5);
  d3 = analogRead(1);
  delay(5);
  return (d1 + d2 + d3)/3;
}

void lerPing() {
  distancia = lerPingComMedia();
}  
void lerPingFull() {
  servoH.write(30);
  delay(200);
  distanciaEsquerda = lerPingComMedia();
  servoH.write(100);
  delay(200);
  distancia = lerPingComMedia();
  servoH.write(150);
  delay(200);
  distanciaDireita = lerPingComMedia();
  servoH.write(85);
}  

