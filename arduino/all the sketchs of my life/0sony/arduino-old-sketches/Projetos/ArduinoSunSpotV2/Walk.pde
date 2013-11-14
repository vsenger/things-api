#define DIRECAO_FRENTE 0
#define DIRECAO_FRENTE_ESQUERDA   1
#define DIRECAO_FRENTE_DIREITA 2
#define DIRECAO_TRAZ 3
#define DIRECAO_TRAZ_ESQUERDA 4
#define DIRECAO_TRAZ_DIREITA 5
#define DIRECAO_PARADO 6
#define PRESS_ESQUERDA 0
#define PRESS_DIREITA 2

int move_history[128];
int moveCount = 0;
int contaFrente= 0;
boolean pressEsquerda = false;
boolean pressDireita = false;
int servoPos =0;
byte distanceArray[18];
byte pscan=0;
byte frontRead=17;
boolean initScan=false;
int longestPos=0;
int distanceLongestPos = 0;
int distanceLongestPosCorrect =  0;

void changeServo(int servoPos) {
  servoHorizontal.write(servoPos);                  // sets the servo position according to the scaled value 
  delay(15);
}

void fullScan() {
  for(byte x=0;x<18;x++){ 
    changeServo(x*10);
    Serial.print("Servo pos:");
    Serial.println(x*10);
    readPing();
    distanceArray[x]=cm;
  }
  changeServo(90);
}  

byte getLongestPosition() {
  byte value = 0;
  int bigValue=0;
  for(byte x=0;x<18;x++) {
    if(distanceArray[x]>bigValue) {
      value=x;
      bigValue=distanceArray[x];
    }
  }
  return value;
}
void partialScan() {
  changeServo(pscan*10);
  readPing();
  distanceArray[pscan]=cm;
  if(++pscan==18) pscan=0;
}  

void Walk() {
  changeServo(80);
  
  readPing();
  if(cm<35) {
    parar();
    fullScan(); 
    if(getLongestPosition()<9) virarDireitaRe(500,3);
    else virarEsquerdaRe(500,3);
    readPing();
    while(cm<30) {
      if(random(1,100)<=50) {
        girarNoEixo(300,3,random(0,1));
      }
      else {
        andarTraz(400,3);
      }      
      readPing();
    }
  }
  else if(cm>35 && cm<50) {
    if(moveCount==0 || moveCount==10)  {
      fullScan();
      moveCount=1;
    } 
    moveCount++;
    if(getLongestPosition()<9) virarDireita(200,3);
    else virarEsquerda(200,3);
  }
  else {
    andarFrente(300,3);
    moveCount++;
  }    


}

void processPing() {
  if(cm<20) {
    contaFrente=0;
    parar();
    int contaGiro=0;
    while(cm<20 && contaGiro++<10) {
      girarNoEixo(random(200,500),3,random(0,1));
      readPing();
    }
    if(cm<20) { 
      andarTraz(1,3);
      if(random(0,1)) {
        virarDireita(random(500,random(200,400)),random(2,4));
      }
      else {
        virarEsquerda(random(500,random(200,400)),random(2,4));
      }
    }
  }
  else if(cm>20 && cm<45) {
    int cmAnterior=cm;
    //parar();
    /*int tenta=0;
     while(cmAnterior<cm && tenta++<20) {
     girarNoEixo(50, 4, 1);
     cmAnterior=cm;
     readPing();
     }*/
    if(random(0,1)) {
      virarDireita(random(50,120),random(2,4));
      int cmAnterior=cm;
      readPing();
      if(cm>cmAnterior) {
        while(cm>cmAnterior) {
          virarDireita(50,2);
          cmAnterior=cm;
          readPing();
        }
      }

    }
    else {
      virarEsquerda(random(50,120),random(2,4));
      int cmAnterior=cm;
      if(cm>cmAnterior) {
        while(cm>cmAnterior) {
          virarEsquerda(50,2);
          cmAnterior=cm;
          readPing();
        }
      }

    }

  }
  else {
    contaFrente++;    
    andarFrente(50,cm<100 ?2 : cm>100 && cm<150 ? 3 : 4);
  }
  if(contaFrente>30) {
    contaFrente=0;
    andarTraz(random(150,450),3);
    if(random(0,1)) {
      virarDireita(random(150,random(200,400)),random(2,4));
    }
    else {
      virarEsquerda(random(150,random(200,400)),random(2,4));
    }

  }
}

void girarNoEixo(int tempo, int intensidade, int sentido1) {
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido1);
  digitalWrite(MOTOR1_N,!sentido1);
  digitalWrite(MOTOR2_P,!sentido1);
  digitalWrite(MOTOR2_N,sentido1);
  delay(tempo);
}
void readPress() {
  pressEsquerda = !analogRead(PRESS_ESQUERDA)==0;
  pressDireita = !analogRead(PRESS_DIREITA)==0;

}

void parar() {  
  analogWrite(MOTOR1_PWM,0 );
  analogWrite(MOTOR2_PWM,0);
}

void andarFrente(int tempo, int intensidade) {
  sentido = 0;
  analogWrite(MOTOR1_PWM,intensidade*50);
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);
}
void andarTraz(int tempo,int intensidade) {
  sentido = 1;
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);

}
void virarEsquerda(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);

}
void virarDireita(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,sentido);
  digitalWrite(MOTOR1_N,!sentido);
  digitalWrite(MOTOR2_P,sentido);
  digitalWrite(MOTOR2_N,!sentido);
  delay(tempo);
}

void virarEsquerdaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,intensidade*50 );
  analogWrite(MOTOR2_PWM,10*intensidade);
  digitalWrite(MOTOR1_P,!sentido);
  digitalWrite(MOTOR1_N,sentido);
  digitalWrite(MOTOR2_P,!sentido);
  digitalWrite(MOTOR2_N,sentido);
  delay(tempo);

}
void virarDireitaRe(int tempo,int intensidade) {
  analogWrite(MOTOR1_PWM,10*intensidade );
  analogWrite(MOTOR2_PWM,intensidade*50);
  digitalWrite(MOTOR1_P,!sentido);
  digitalWrite(MOTOR1_N,sentido);
  digitalWrite(MOTOR2_P,!sentido);
  digitalWrite(MOTOR2_N,sentido);
  delay(tempo);
}


void readPing()
{
  pinMode(PING_PIN, OUTPUT);
  digitalWrite(PING_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(PING_PIN, HIGH);
  delayMicroseconds(5);
  digitalWrite(PING_PIN, LOW);

  pinMode(PING_PIN, INPUT);
  duration = pulseIn(PING_PIN, HIGH);
  inches = microsecondsToInches(duration);
  cm = microsecondsToCentimeters(duration);
  Serial.println(cm);
}

long microsecondsToInches(long microseconds)
{
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds)
{
  return microseconds / 29 / 2;
}



