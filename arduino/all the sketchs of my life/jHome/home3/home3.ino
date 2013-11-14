//jHome and Electron Kernel includes
#include "ElectronKernel.h"
#include "Device.h"
#include "etherShield.h"
#include "ETHER_28J60.h"

//Specific includes to LCD and HR
#include "LiquidCrystal_I2C.h"
#include "Wire.h"
#include "hrmi_funcs.h"

LiquidCrystal_I2C lcd(0x27,16,2);

//Device homeDevice=Device("sensor-device");


long handTimer=0;

void setup()
{
  homeDevice.add("red",          PWM,   3);
  homeDevice.add("green",        PWM,   6);
  homeDevice.add("blue",         PWM,   9);


  homeDevice.add("distance",     PING,  5);

  homeDevice.add("light",        ANALOG, 3);
  homeDevice.add("temp",         ANALOG,  2);
  //Cool jHome Feature: you can add your custom component passing a read / write function pointer
  homeDevice.add("heart",        LIB,   I2C, readHeartBeat, writeHeartBeat);

  Kernel.setup(INTERRUPTION);
  Kernel.registerMode("M1", jhome_setup);
  Kernel.registerTask(jhome);
  Kernel.registerMode("MH", heartMonitor_setup);
  Kernel.registerTask(heartMonitor);

  Kernel.registerMode("M2", rgb_red_setup);
  Kernel.registerTask(mode_rgb);
  Kernel.registerMode("M3", rgb_green_setup);
  Kernel.registerTask(mode_rgb);
  Kernel.registerMode("M4", rgb_blue_setup);
  Kernel.registerTask(mode_rgb);

  Serial.begin(115200);
  int ip[]={192,168,1,15};  
  //homeDevice.startNetwork(ip);

  /*lcd.init();
  lcd.setBacklight(0);
  lcd.setCursor(0, 0);
  lcd.print("jHome v1.0");
  lcd.setCursor(0, 1);
  lcd.print("by Globalcode");
  delay(1500);*/
}

void loop()
{
  Kernel.loop();
  //jhome();
} 

void jhome_setup()  { 
  lcd.setBacklight(15);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("jHome v1.0");
}
void jhome() {

  //homeDevice.loop();
  /*if(millis()-handTimer>1000) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("jHome v1.0");
    lcd.setCursor(0, 1);
    lcd.print(homeDevice.execute("distance"));
    lcd.setCursor(4, 1);
    float temperatura = (analogRead(2) * 0.00488);  // 5V / 1023 = 0.00488 (precisão do A/D)
    temperatura = temperatura * 100; //Converte milivolts para graus celcius, lembrando que a cada 10mV equivalem a 1 grau celcius
    lcd.print(temperatura);
    handTimer=millis();    
  }*/

}


