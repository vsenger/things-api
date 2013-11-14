void calibrarSensorDeLinha() {
  lcd.imprimir("Calibrando...");
  sensorLinha.calibrarSensores();   
  lcd.imprimir("Calibrado OK");
  sensorLinha.lerSensores();
  imprimirSensorLinha();
}  



void imprimirSensorLinha() {
  lcd.imprimir("                ",0,1);
  lcd.imprimir(sensorLinha.sensor[0],0,1);
  lcd.imprimir(sensorLinha.sensor[1],6,1);  
  lcd.imprimir(sensorLinha.sensor[2],11,1);    
}

void seguirLinha() {
  sensorLinha.lerSensores();
  imprimirSensorLinha();
  /*sensorLinha.imprimirPID();*/
  motores.andarParaFrente(40);
  
  if(sensorLinha.sensor[0]!=0) {
    motores.ajustarVelocidade(20,40);
  }
  else if(sensorLinha.sensor[1]!=1000 ) {
  }
  else if(sensorLinha.sensor[2]!=0) {
    motores.ajustarVelocidade(40,20);
  }
  
}

int caso() {
  if(sensorLinha.sensor[1]==1000 && sensorLinha.sensor[0]==0 && sensorLinha.sensor[2]==0) return 1;
  else if(sensorLinha.sensor[1]==0 && sensorLinha.sensor[0]==0 && sensorLinha.sensor[2]==0) return 2;  
  else if(sensorLinha.sensor[1]==1000 && sensorLinha.sensor[0]==1000 && sensorLinha.sensor[2]==1000) return 3;    
  else if(sensorLinha.sensor[1]==0 && sensorLinha.sensor[0]==0 && sensorLinha.sensor[2]==1000) return 4;     
  else if(sensorLinha.sensor[1]==0 && sensorLinha.sensor[0]==1000 && sensorLinha.sensor[2]==1000) return 5;     
}

void seguirLinha_setup(){ 
  lcd.imprimir("Modo Seguir Linha");  
}

