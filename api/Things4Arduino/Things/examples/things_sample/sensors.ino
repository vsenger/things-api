#define dht_dpin A1

byte bGlobalErr;
byte dht_dat[5];
byte initSensor = 0;
char retorno[6];  

char* temperature_out() {
  //if(!initSensor)  
  InitDHT();
  ReadDHT();
  if(!bGlobalErr) {
    itoa(dht_dat[2],retorno,10);
    retorno[2]='.';
    char conv2[3];
    itoa(dht_dat[3],conv2,10);
    retorno[3]=conv2[0];
    retorno[4]=conv2[1];
    retorno[5]='\0';
    return retorno;
  }
  else { 
    return "0"; 
  }
}

char* humidity() {
  //if(!initSensor) 
  InitDHT();
  ReadDHT();
  if(!bGlobalErr) {
    itoa(dht_dat[0],retorno,10);
    retorno[2]='.';
    char conv2[3];
    itoa(dht_dat[1],conv2,10);
    retorno[3]=conv2[0];
    retorno[4]=conv2[1];
    retorno[5]='\0';
    return retorno;
  }
  else { 
    return "0"; 
  }
}


void InitDHT(){
  initSensor=1;
  pinMode(dht_dpin,OUTPUT);
  digitalWrite(dht_dpin,HIGH);
}

void ReadDHT(){
  bGlobalErr=0;
  byte dht_in;
  byte i;
  digitalWrite(dht_dpin,LOW);
  delay(20);
  digitalWrite(dht_dpin,HIGH);
  delayMicroseconds(40);
  pinMode(dht_dpin,INPUT);
  //delayMicroseconds(40);
  dht_in=digitalRead(dht_dpin);
  if(dht_in){
    bGlobalErr=1;
    return;
  }
  delayMicroseconds(80);
  dht_in=digitalRead(dht_dpin);
  if(!dht_in){
    bGlobalErr=2;
    return;
  }
  delayMicroseconds(80);
  for (i=0; i<5; i++)
    dht_dat[i] = read_dht_dat();
  pinMode(dht_dpin,OUTPUT);
  digitalWrite(dht_dpin,HIGH);
  byte dht_check_sum =
    dht_dat[0]+dht_dat[1]+dht_dat[2]+dht_dat[3];
  if(dht_dat[4]!= dht_check_sum)
  {
    bGlobalErr=3;
  }
};

byte read_dht_dat(){
  byte i = 0;
  byte result=0;
  for(i=0; i< 8; i++){
    while(digitalRead(dht_dpin)==LOW);
    delayMicroseconds(30);
    if (digitalRead(dht_dpin)==HIGH)
      result |=(1<<(7-i));
    while (digitalRead(dht_dpin)==HIGH);
  }
  return result;
}

char* sample_write(char* args) {
  int codigo = atoi(args);
  if(codigo==11) {
  }
  else if(codigo==12) {
  }
  else if(codigo==21) {
  }
  else if(codigo==22) {
  }
}

