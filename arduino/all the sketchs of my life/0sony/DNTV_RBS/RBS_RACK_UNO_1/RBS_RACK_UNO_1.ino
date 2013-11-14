#include "Device.h"
#include <SPI.h>
#include <Ethernet.h>
#include <IRremote.h>

Device homeDevice=Device("central-device");
byte mac[] = {0x90, 0xA2, 0xDA, 0x0D, 0xEC, 0x0F };
IPAddress ip(192,168,0, 210);
EthernetServer server(80);

void setup() {
  //Serial.begin(115200);  
  //homeDevice.add("LED", DIGITAL, 6);
  //homeDevice.add("relay1", DIGITAL, 6);
  //homeDevice.add("relay2", DIGITAL, 7);
  //homeDevice.add("light",  ANALOG,  3);
  //homeDevice.add("temp",   ANALOG,  2);
  homeDevice.add("teac1",     CUSTOM,  3, irSender_read  , irSender_write);
  //homeDevice.add("teac2",     CUSTOM,  3, irSender_read  , irSender_write);
  homeDevice.add("kramer",  CUSTOM,  0, kramer_read, kramer_write); //kramer na serial - TX1/RX1
  //homeDevice.add("polycom",  CUSTOM,  0, polycom_read, polycom_write); //polycom na serial2


  Ethernet.begin(mac, ip);
  server.begin();
}

void loop() {
  //Para aceitar requisições por Serial
  //homeDevice.loop();  
  //Para aceitar requisições por TCP/IP
  ethernet();
}

