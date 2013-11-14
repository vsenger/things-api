void acelera(byte flag, byte numOfValues) {
  meetAndroid.getFloatValues(acelerometro);
  if(modo!=ACELEROMETRO) return;
  servoV.write(map(acelerometro[0],-10,10,0,180));
  servoH.write(map(acelerometro[1],-10,10,0,180));

  if(acelerometro[0]>-3 && acelerometro[0]<3) {

    if(acelerometro[1]<3) {
      andarTraz(200,map(acelerometro[1],-3,-10,1,5));    
    }
    else if(acelerometro[1]>3) {
      andarFrente(200,map(acelerometro[1],3,10,1,5));
    }  
    else if(acelerometro[1]>-3 && acelerometro[1]<3) {
      parar();
    }
  }
  else if(acelerometro[0]<-3) {  //para esquerda
    if(acelerometro[1]>-3 && acelerometro[1]<3) {    //esquerda no eixo
      girarNoEixo(200, map(acelerometro[0],-3,-10,1,5),1);
    }
    else {
      virarEsquerda(200,2);
    } 
  }
  else if(acelerometro[0]>3) { //para direita
    if(acelerometro[1]>-3 && acelerometro[1]<3) {    //direita no eixo
      girarNoEixo(200, map(acelerometro[0],-3,-10,1,5),0);
    }
    else {
      virarDireita(200,2);
    } 
  }

}



void andar(byte flag, byte numOfValues) {
  int length = meetAndroid.stringLength();
  char comando[length];
  meetAndroid.getString(comando);
  if(modo!=ANDAR) return;

  if(comando[0]=='F') {
    //dance();
    parar();
  }
  else if(comando[0]=='R') {
    andarTraz(1000,3);
    parar();
  }
  else if(comando[0]=='G') {
    girarNoEixo(1000,3,0);
    parar();
  }
  else if(comando[0]=='D') {
    //dance();
  }
}

void dedoDuro(byte flag, byte numOfValues) {
  state = meetAndroid.getInt();
  if(modo!=DEDO_DURO) return;

  if(state==0) {
    for(int x=0;x<3;x++) {
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);      
      parar();
      //testeLeds();
    }
  }
}

void encontraNorte(byte flag, byte numOfValues) {
  bussola =  meetAndroid.getInt();
  if(modo!=ENCONTRAR_NORTE) return;
  //if(bussola>180) analogWrite(LED_RED, map(bussola, 180, 360, 0,255));
  //else analogWrite(LED_RED, map(bussola, 180, 0, 0,255));
  if(bussola>350 && bussola<360) {
    girarNoEixo(80,1,1);
    parar();
  }
  else if(bussola>180 && bussola<350) {
    girarNoEixo(200,1,1);
    parar();
  }
  else if(bussola>15 && bussola<180) {
    girarNoEixo(200,1,0);
    parar();  
  }
  else if(bussola>3 && bussola<15) {
    girarNoEixo(80,1,0);
    parar(); 
  }
  else if(bussola>0 && bussola<3) {
    parar();
  }
}


