#include "Thing.h"
#include "Arduino.h" // Arduino 1.0 
#include "Component.h"

volatile long lastDebounce=0;
volatile long debounceDelay=50;
volatile long ntime=0;
char command1[15];
char parameter1[15];
char return1[15];

Thing thing;

Thing::Thing() {
  componentIndex=0;
  serial=true;
  network=false;
  found=false;
  name = "t1";
  pins.speaker=4;
}

Thing::Thing(char* name1) {
  componentIndex=0;
  serial=true;
  network=false;
  found=false;
  name = name1;
  pins.speaker=4;
}

void Thing::addComponent(char* name, int type, int port) {
  Component c1 = Component(name, type,port);
  components[numberOfComponents++]= c1;
}

void Thing::addComponent(char* name, int type, int port, char*(*readFunction)(), char*(*writeFunction)(char*)) {
  Component c1 = Component(name, type,port, readFunction, writeFunction);
  components[numberOfComponents++]= c1;
}
void Thing::addMode(int modeNumber, void(*mode1)()) {
  Mode newMode(mode1);
  modes[modeNumber]=newMode;
  modeCounter++;
}

int Thing::findComponent(char* name) {
  int n = 0;
  found=false;
  //Serial.println("searching component...");
  for(int x=0;x<numberOfComponents;x++) {
    //Serial.print(x);
    //Serial.print(" - Name: ");
    //Serial.println(name);

    if(strcmp(name,components[x].name)==0) {
      n = x;
      found=true;
      break;
    }
  }
  return n;
}

void Thing::loop() {
  if(lastMode!=mode) {
    lastMode = mode;
    beep(mode+1);
    //modoMudou=1;
    modes[mode].setup();
  }
  modeChanged1=0;
  modes[mode].execute();
  if(serial && Serial.available()) {
    //debug 
    //Serial.println("chamando serial server");
    serialServer();
  }
}


void split(char* command) {
  for(int x=0;x<15;x++) {
    command1[x]='\0';
    parameter1[x]='\0';
  }
  int cv=0;
  int cp=0;
  for(int x=0;x<15;x++) {
    if(command[x]!='?' && command[x]!='\0') {
      command1[cv++]=command[x];
    } else {
      cp = command[x]=='?' ? x : 0;
      break;
    }
  }

  if(cp>0) {
    for(int x=cp+1;x<15;x++) {
      parameter1[x-cp-1]=command[x];
      if(command[x]=='\0') break;
    }
  } 
}



char* Thing::execute(char* command) {
  //debug 
  //Serial.println("antes do split");
  //debug 
  //Serial.println(command);
  split(command);
  //debug Serial.println("depois do split");

  Component &c = components[findComponent(command1)];
  if(!found) {
    //debug 
    //Serial.println("component not found");
    return "\n";
  }
  if(parameter1[0]=='\0') {
    return c.read();
    //debug  
    //Serial.println("reading");
    //char* r=c.read();
    //Serial.print("read ");
    //Serial.println(r);

  }
  else {
    //debug 
    //Serial.println("writing");
    //Serial.println(parameter1);
    
    return c.write(parameter1);
  }




  
}

void Thing::serialServer() {
  char command[15];
  int counter=0;
  char c;
  while(Serial.available()>0) 
  {
    command[counter++]=Serial.read(); 
    delay(2);
  }

  if(counter>0) {
     command[counter]='\0';

     if(strcmp("discovery",command)==0) {
        //Serial.println("discovery serial");
        discoverySerial();
        return;
     }
     //debug 
     //Serial.println("executing command...");
     //debug 
     //Serial.println(command);

     char* r = execute(command);
     //debug Serial.println("executed command. returned value:");
     //debug Serial.println(r);	
     if(r[0]!='\0') {
       Serial.print(r);
       Serial.flush();
     }
  }  
}

void Thing::discoverySerial() {
  Serial.print(name);
  Serial.print("|");
  Serial.print(numberOfComponents);
  Serial.print("|");
  for(int x=0;x<numberOfComponents;x++) {
    Serial.print(components[x].name);
    Serial.print("|");
    Serial.print(components[x].getTypeName());
    Serial.print("|");
    Serial.print(components[x].port);
    Serial.print("|");
    Serial.print(components[x].getValue());
    Serial.print("|");
  }
}
byte Thing::modeChanged() { return modeChanged1;}

void Thing::wait(long milis) { 
  modeChanged1=0;
  if(mode!=lastMode) {
    modeChanged1=1;
    return;
  }
  if(milis<10) delay(milis);
  else {
    for(long x=0;x<milis/10;x++) {
      delay(10);
      //checarMudancaModo();
      if(mode!=lastMode) {
        modeChanged1=1;
        return;
      }
    }
    delay(milis%10);
  }
}


void Thing::beep(int ntime) {
  for(int x=0;x<ntime;x++) {
    digitalWrite(pins.speaker, HIGH);
    delay(100);
    digitalWrite(pins.speaker, LOW);
    delay(100);
  }

}
void changeModeViaButton() {

  if(millis()-lastDebounce>debounceDelay && ntime++>=100) {
    thing.lastMode=thing.mode;
    thing.mode = thing.mode==thing.modeCounter-1 ? 0 : thing.mode + 1;
    ntime=0;
    thing.modeChanged1=1;
    lastDebounce=millis();
  }
}


void Thing::start() {
   
  attachInterrupt(0, changeModeViaButton, LOW);
  pinMode(pins.speaker, OUTPUT);
  beep(1);
}

void Thing::start(int intNumber) {
  if(intNumber>-1) attachInterrupt(intNumber, changeModeViaButton, LOW);
  pinMode(pins.speaker, OUTPUT);
  beep(1);
}
