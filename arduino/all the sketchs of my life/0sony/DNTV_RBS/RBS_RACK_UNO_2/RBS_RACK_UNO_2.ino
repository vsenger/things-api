#include "Device.h"
#include <SPI.h>
#include <Ethernet.h>
#include <IRremote.h>

Device homeDevice=Device("central-device");
byte mac[] = {0x90, 0xA2, 0xDA, 0x0D, 0xEC, 0x0A };
IPAddress ip(192,168,0, 211);
EthernetServer server(80);

void setup() {

  homeDevice.add("teac2",     CUSTOM,  3, irSender_read  , irSender_write);
 
  //homeDevice.add("polycom",  CUSTOM,  0, polycom_read, polycom_write); //polycom ainda nao esta sendo controlado


  Ethernet.begin(mac, ip);
  server.begin();
}

void loop() {
  //Para aceitar requisições por Serial
  //homeDevice.loop();  
  //Para aceitar requisições por TCP/IP
  ethernet();
}

