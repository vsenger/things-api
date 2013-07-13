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

#define LIB 	7
#define PING    8

#define ALL     9

class Component  {
 private:
  public:    
      char* name;
      int   type;
      int   port;
      int   state;

      Component(char*, int, int);
      Component();
      char* getValue();
      char* write(char *);
      char* read();
      char* getTypeName();
      void emptyReadValue();
};
#endif



