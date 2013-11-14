#include "Device.h"
#include <SPI.h>
#include <Ethernet.h>
#include <IRremote.h>

Device homeDevice=Device("central-device");
byte mac[] = {0x90, 0xA2, 0xDA, 0x0D, 0xEC, 0x26 }; //Atentar ao MAC unico de cada placa!
IPAddress ip(192,168,0, 201);
EthernetServer server(80);

void setup() {  
  
  homeDevice.add("tela1",     CUSTOM,  3, irSender_read  , irSender_write);
  //panasonic com max232 - 19200
  Serial.begin(19200);
  homeDevice.add("pana1",  CUSTOM,  0, rs232_read1, rs232_write1);


  Ethernet.begin(mac, ip);
  server.begin();
}

void loop() {
  //Para aceitar requisições por Serial
  //homeDevice.loop();  
  //Para aceitar requisições por TCP/IP
  ethernet();
}

