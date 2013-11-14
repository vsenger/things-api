void dedoDuro(byte flag, byte numOfValues) {
  state = meetAndroid.getInt();
  if(modo!=1) return;

  if(state==0) {
    for(int x=0;x<3;x++) {
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);
      girarNoEixo(200,2,0);
      girarNoEixo(200,2,1);      
      parar();
      testeLeds();
    }
  }
}

