#ifndef DEVICE_H
#define DEVICE_H
#include "Component.h"
#include "Arduino.h"

#define MAX 15
#define I2C 99


class Device {

private:
  bool debug;
  bool found;
  int componentIndex;

public:
  Device();
  Device(char*);

  int numberOfComponents;
  bool serial;
  bool network;
  Component components[MAX];
  void add(char*, int, int);
  void add(char*, int, int,char*(*readFunction)(), void(*writeFunction)(char*));
  void loop();
  String discoveryString();
  int findComponent(char*) ;
  void serialServer();
  char* execute(char *);
  char* name;
  void printComponents(int type);
  void discoverySerial();
};
#endif
