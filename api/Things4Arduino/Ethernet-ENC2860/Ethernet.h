#ifndef ETHERNET_H
#define ETHERNET_H
#include "etherShield.h"
#include "ETHER_28J60.h"
#include "Arduino.h"
#include "Device.h"

class Ethernet  {
  private:

  public:    
    Device* device;
    Ethernet(Device* device);
    ETHER_28J60 ethernet;
    void startNetwork(int ipn[]);
    void loop();
    void discoveryNetwork();
};
#endif
