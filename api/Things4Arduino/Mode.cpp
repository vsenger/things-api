#include <Mode.h>
#include "HardwareSerial.h"
Mode::Mode() {
 hasSetup=0;
}

Mode::Mode(ISR mode) {
  modeFunction=mode;
  hasSetup=0;
}
Mode::Mode(ISR mode, ISR setup) {
  modeFunction=mode;
  setupFunction=setup;
  hasSetup=1;
}

void Mode::setup(){
  if(hasSetup) setupFunction();
}
void Mode::execute(){
  modeFunction();
}

