#include "Device.h"
#include <SPI.h>
#include <Ethernet.h>
#include <IRremote.h>

Device homeDevice=Device("central-device");
byte mac[] = {0x90, 0xA2, 0xDA, 0x0D, 0xEC, 0x09 }; //Atentar ao MAC unico de cada placa!
IPAddress ip(192,168,0, 203);
EthernetServer server(80);

void setup() {
  Serial.begin(115200);  
  homeDevice.add("LED", DIGITAL, 13);
  homeDevice.add("L1F", DIGITAL, 6); //Luz 1 frontal
  homeDevice.add("L1T", DIGITAL, 7); // Luz 1 tras
  homeDevice.add("L2F", DIGITAL, 8); // Luz 2 frontal
  homeDevice.add("L2T", DIGITAL, 9); // Luz 2 tras
  
  //homeDevice.add("light",  ANALOG,  3);
  //homeDevice.add("temp",   ANALOG,  2);
  //homeDevice.add("ir",     CUSTOM,  3, irSender_read  , irSender_write);
  //homeDevice.add("panasonic",  CUSTOM,  0, rs232_read, rs232_write);
  //homeDevice.add("rs2321",  CUSTOM,  0, rs232_read, rs232_write);


  Ethernet.begin(mac, ip);
  server.begin();
}

void loop() {
  //Para aceitar requisições por Serial
  //homeDevice.loop();  
  //Para aceitar requisições por TCP/IP
  ethernet();
}

