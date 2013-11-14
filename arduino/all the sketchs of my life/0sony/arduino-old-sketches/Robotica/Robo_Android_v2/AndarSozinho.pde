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

void andarSozinho() {
  lerPing();

  while(distancia>25) {
    andarFrente(100,3);
    lerPing();
  if(modo!=6) return;
  }
  while(distancia>15 && distancia<25) {
    if(modo!=6) return;
    parar();
    delay(200);
    lerPingFull();
    if(distanciaEsquerda>distancia && distanciaEsquerda>distanciaDireita) {
      girarNoEixo(300,2,1);
    }
    if(distanciaDireita>distancia && distanciaDireita>distanciaEsquerda) {
      girarNoEixo(300,2,0);
    }    
    lerPing();
    if(modo!=6) return;
  }
  while(distancia>5 && distancia<15) {
    parar();
    andarTraz(500,2);
    girarNoEixo(500,2, random(10)%2==0);
    lerPing();
    if(modo!=6) return;
  }

}




