void LerDistancias ( ){
  //posiciona cursor para leitura esquerda
  ServoV.write(135);              
  delay(150);         // waits 15ms for the servo to reach the position 
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  long duration = pulseIn(PING_PIN, HIGH);
  DistanciaEsquerda = duration / 29 / 2;


  //posiciona cursor para leitura direita
  ServoV.write(45);              
  delay(150);         // waits 15ms for the servo to reach the position 
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  duration = pulseIn(PING_PIN, HIGH);
  DistanciaDireita = duration / 29 / 2;
}

