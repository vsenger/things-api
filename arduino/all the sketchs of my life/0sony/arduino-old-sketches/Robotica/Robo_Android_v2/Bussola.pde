void encontraNorte(byte flag, byte numOfValues) {
  bussola =  meetAndroid.getInt();
  if(modo!=2) return;
  if(bussola>180) analogWrite(LED_RED, map(bussola, 180, 360, 0,255));
  else analogWrite(LED_RED, map(bussola, 180, 0, 0,255));
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


