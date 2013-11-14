//#ifndef THING_H
//#define THING_H
#include "Component.h"
#include "Mode.h"
#include "Arduino.h"

#define MAX_COMPONENTS 10
#define MAX_MODES 2
#define I2C 99

struct PinMapping {
  byte motor1A;
  byte motor1B;
  byte motor1PWM;
  byte motor2A;
  byte motor2B;
  byte motor2PWM;
  byte servo;
  byte red;
  byte green;
  byte blue;

  byte speaker;
};



class Thing {

private:
  bool debug;
  bool found;
  int componentIndex;

public:
  Thing();
  Thing(char*);

  bool serial;
  bool network;

  int mode;
  int lastMode;
  int modeCounter;
  byte modeChanged1;
  char* name;
  Mode modes[MAX_MODES];

  int numberOfComponents;
  Component components[MAX_COMPONENTS];

  PinMapping pins;
  void start();
  void start(int noInt);

  void beep(int);
  void addComponent(char*, int, int);
  void addComponent(char*, int, int,char*(*readFunction)(), char*(*writeFunction)(char*));
  void loop();
  void addMode(int modeNumber, void(*modeFunction)());
  byte modeChanged();

  String discoveryString();
  int findComponent(char*) ;
  void serialServer();
  char* execute(char *);
  void printComponents(int type);
  void discoverySerial();
  void wait(long milis);
};

extern Thing thing;
//#endif

