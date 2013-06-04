#include "Device.h"
#include "Arduino.h" // Arduino 1.0 
#include "Component.h"

Device::Device() {
  componentIndex=0;
  serial=true;
  network=false;
  found=false;
  name = "noname1";
}

Device::Device(char* name1) {
  componentIndex=0;
  serial=true;
  network=false;
  found=false;
  name = name1;
}
char command1[10];
char parameter1[10];
char return1[10];

void Device::add(char* name, int type, int port) {
  Component c1 = Component(name, type,port);
  components[numberOfComponents++]= c1;
}

void Device::add(char* name, int type, int port, char*(*readFunction)(), void(*writeFunction)(char*)) {
  Component c1 = Component(name, type,port);
  components[numberOfComponents++]= c1;
}

int Device::findComponent(char* name) {
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

void Device::loop() {
  if(serial && Serial.available()) {
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



char* Device::execute(char* command) {
  split(command);
  Component &c = components[findComponent(command1)];
  if(!found) {
    //Serial.println("component not found");
    return "\n";
  }
  if(parameter1[0]=='\0') {
    //Serial.println("reading");
    char* r=c.read();
    return r;
  }
  else {
    //Serial.println("writing");
    return c.write(parameter1);
  }




  
}

void Device::serialServer() {
  char command[15];
  int counter=0;
  while(Serial.available()>0) 
  {
    char c = Serial.read(); 
    delay(1);
    command[counter++]=c;
  }
  if(counter>0) {
     command[counter]='\0';

     if(strcmp("discovery",command)==0) {
        //Serial.println("discovery serial");
        discoverySerial();
        return;
     }
     //Serial.println("executing command...");
     //Serial.println(command);

     char* r = execute(command);
     //Serial.println("executed command. returned value:");
     //Serial.println(r);	
     if(r[0]!='\0') {
       Serial.print(r);
       //Serial.print('\0');
       Serial.flush();
     }
  }  
}

void Device::discoverySerial() {
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
