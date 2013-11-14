#include <MeetAndroid.h>

MeetAndroid meetAndroid;

int modoAndroid=1;

void android() {
  meetAndroid.registerFunction(androidSensorProximidade, 'A');  
  meetAndroid.registerFunction(androidAcelerometro, 'C'); 
  meetAndroid.registerFunction(androidBussola, 'B'); 
  meetAndroid.registerFunction(androidMudarModo, 'M');

  while(!robot.mudouModo()) {
    meetAndroid.receive(); 
    //delay(50);
  }
}
void androidMudarModo(byte flag, byte numOfValues) {
  int length = meetAndroid.stringLength();

  // define an array with the appropriate size which will store the string
  char comando[length];

  // tell MeetAndroid to put the string into your prepared array
  meetAndroid.getString(comando);
  char c[1];
  c[0]=comando[0];
  modoAndroid = atoi(c);
  //if(modoAndroid<0 || modoAndroid>5) modoAndroid=1;
}

void androidAcelerometro(byte flag, byte numOfValues) {
  float acelerometro[3];
  meetAndroid.getFloatValues(acelerometro);
  if(modoAndroid==1) {

    if(acelerometro[0]>-3 && acelerometro[0]<3) {

      if(acelerometro[1]<3) {
        robot.motores.re(map(acelerometro[1],-3,-10,1,5));    
      }
      else if(acelerometro[1]>3) {
        robot.motores.frente(map(acelerometro[1],3,10,1,5));
      }  
      else if(acelerometro[1]>-3 && acelerometro[1]<3) {
        robot.motores.parar();
      }
    }
    else if(acelerometro[0]<-3) {  //para esquerda
      if(acelerometro[1]>-3 && acelerometro[1]<3) {    //esquerda no eixo
        robot.motores.girar(map(acelerometro[0],-3,-10,1,5),0);
      }
      else {
        robot.motores.esquerda(3);
      } 
    }
    else if(acelerometro[0]>3) { //para direita
      if(acelerometro[1]>-3 && acelerometro[1]<3) {    //direita no eixo
        robot.motores.girar(map(acelerometro[0],-3,-10,1,5),1);
      }
      else {
        robot.motores.direita(3);
      } 
    }
  }
}


void androidSensorProximidade(byte flag, byte numOfValues) {
  int state = meetAndroid.getInt();
  if(modoAndroid!=3) return;

  if(state==0) {
    for(int x=0;x<3;x++) {
      robot.motores.girar(4,0);
      delay(250);
      robot.motores.girar(4,1);
      delay(250);
      robot.motores.girar(4,0);
      delay(250);
      robot.motores.girar(4,1);
      delay(250);

      robot.motores.parar();
    }
  }
}

void androidBussola(byte flag, byte numOfValues) {
  int bussola =  meetAndroid.getInt();
  if(modoAndroid!=2) return;
  if(bussola>350 && bussola<360) {
    robot.motores.girar(3,1);
    delay(80);
    robot.motores.parar();
  }
  else if(bussola>180 && bussola<350) {
    robot.motores.girar(3,1);
    delay(200);
    robot.motores.parar();
  }
  else if(bussola>15 && bussola<180) {
    robot.motores.girar(3,0);
    delay(200);
    robot.motores.parar();
  }
  else if(bussola>3 && bussola<15) {
    robot.motores.girar(3,0);
    delay(80);
    robot.motores.parar();
  }
  else if(bussola>0 && bussola<3) {
    robot.motores.parar();
  }
}



