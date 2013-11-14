#include "Device.h"
#include <IRremote.h>//biblioteca do controle
int RECV_PIN = 6; // colocar o Shield IR receiver module na porta 11
IRrecv irrecv(RECV_PIN);
decode_results results;
unsigned long codigo;//variável que recebe o código

Device homeDevice=Device();

void setup() {
  homeDevice.add("relay1", "RELAY", 14);
  homeDevice.add("relay2", "RELAY", 15);
  homeDevice.add("relay3", "RELAY", 16);
  homeDevice.add("relay4", "RELAY", 17);
  homeDevice.add("light",  "LIGHT", 3);
  homeDevice.add("temperature",  "TEMP", 2);
  Serial.begin(115200);
  irrecv.enableIRIn(); // inicia o recebimento de dados


}

void print() {
  Serial.println(homeDevice.read("light"));
}
int state;
void loop() {
  //homeDevice.loop();
  if (irrecv.decode(&results)) {
    codigo=results.value; // variável "código" recebe dados da "results.value "
    Serial.println(codigo, DEC); // mostra o dados na Serial Monitor em decimal
    irrecv.resume();
  }
  if(codigo==2640478335) {
    digitalWrite(14,state);
    state=!state;
    delay(200);
  }  

}


