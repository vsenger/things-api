/*void atualizaDisplay() {
  lcd.clear();

  //atualiza temperatura  
  //lcd.setCursor(col,row);
  lcd.setCursor(8,1);
  lcd.print(analogRead(SensorTemperatura)/2);
  lcd.print("oC");

  Serial.print("Temperatura= ");
  Serial.println(analogRead(SensorTemperatura)/2);

  //atualiza luminosidade  
  //lcd.setCursor(col,row);
  lcd.setCursor(0,1);
  lcd.print(map((analogRead(LDR)), 0, 1023, 100,0));
  lcd.print(" %");

  Serial.print("Luminosidade= ");
  Serial.println(map((analogRead(LDR)), 0, 1023, 100,0));
  Serial.println(" ");


  //lcd.setCursor(col,row);
  lcd.setCursor(0,0);
  lcd.print(DistanciaEsquerda);
  lcd.print(" cms");

  //atualiza distancia esquerda  
  Serial.print("Distancia Esquerda= ");
  Serial.println(DistanciaEsquerda);

  //atualiza distancia direita  
  //lcd.setCursor(col,row);
  lcd.setCursor(8,0);
  lcd.print(DistanciaDireita);
  lcd.print(" cms");

  Serial.print("Distancia Direita= ");
  Serial.println(DistanciaDireita);



}
*/

