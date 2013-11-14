void acelera(byte flag, byte numOfValues) {
  meetAndroid.getFloatValues(acelerometro);
  if(modo==4) {
    servoV.write(map(acelerometro[0],-10,10,0,180));
    servoH.write(map(acelerometro[1],-10,10,0,180));

    if(acelerometro[0]>-3 && acelerometro[0]<3) {

      if(acelerometro[1]<3) {
        andarFrente(200,map(acelerometro[1],-3,-10,1,5));    
      }
      else if(acelerometro[1]>3) {
        andarTraz(200,map(acelerometro[1],3,10,1,5));
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
}

