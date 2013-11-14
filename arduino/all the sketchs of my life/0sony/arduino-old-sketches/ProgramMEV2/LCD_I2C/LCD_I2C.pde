#include <LiquidCrystal_I2C.h>
#include <Wire.h> 
LiquidCrystal_I2C lcd(0x27,16,2);

void setup() {
    lcd.init();
    lcd.backlight();
    lcd.setCursor(0, 1);
    lcd.print("Program-ME v2");
    
}


void loop() {}

    
