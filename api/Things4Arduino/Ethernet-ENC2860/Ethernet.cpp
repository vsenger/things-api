#include "etherShield.h"
#include "ETHER_28J60.h"
#include "Ethernet.h"
#include "Arduino.h"

static uint8_t mac[6] = {0x54,0x55,0x58,0x10,0x00,0x24}; 
static uint8_t ip[4] = {192,168,1,15};
static uint16_t port = 80; 
Ethernet::Ethernet(Device* device1) {
  device=device1;
}
void Ethernet::startNetwork(int ipn[]) {
  ip[0]=ipn[0];
  ip[1]=ipn[1];
  ip[2]=ipn[2];
  ip[3]=ipn[3];
  ethernet.setup(mac, ip, port);
}

void Ethernet::discoveryNetwork() {
  ethernet.print(device->name);
  ethernet.print("|");
 
  ethernet.print(device->numberOfComponents);
  ethernet.print("|");
  for(int x=0;x<device->numberOfComponents;x++) {
    ethernet.print(device->components[x].name);
    ethernet.print("|");
    ethernet.print(device->components[x].getTypeName());
    ethernet.print("|");
    ethernet.print(device->components[x].port);
    ethernet.print("|");

    ethernet.print(device->components[x].getValue());
    ethernet.print("|");
  }
}
void Ethernet::loop() {
    char* param;
    if (param = ethernet.serviceRequest())
    { 

      if(param[0]=='\0') {
        discoveryNetwork();
      }
      else if(strcmp("discovery", param)==0) {
        discoveryNetwork();
      }
      else {
        ethernet.print(device->execute(param));
      }
      ethernet.respond();
    }
}
