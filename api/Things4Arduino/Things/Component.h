#ifndef COMPONENT_H
#define COMPONENT_H
#include "Arduino.h"
#define DIGITAL 0
#define ANALOG 	1
#define PWM 	2

#define RELAY   3
#define LIGHT   4
#define TEMP    5
#define SERIAL 	6

#define PING    7

#define ALL     8
#define CUSTOM  9
class Component  {
 private:
   typedef char* (*customRead)();
   typedef char* (*customWrite)(char*);
   customRead meuCustomRead;
   customWrite meuCustomWrite;
  public:    
      char* name;
      int   type;
      int   port;
      int   state;

      Component(char*, int, int);
      Component(char*, int, int, customRead, customWrite);

      Component();
      char* getValue();
      char* write(char *);
      char* read();
      char* getTypeName();
      void emptyReadValue();
};
#endif



