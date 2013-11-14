/* :::MonitorDuino v_1.0:::
/* An Arduino based performance computer monitor
/*
/* AUTHOR: Stefano Manni | http://www.stefanomanni.it/arduino | ::FREE SOFTWARE::
/*
/* HOW TO: upload sketch and run systemMonitor.sh script on your computer with LINUX OS

 * CIRCUIT:
 *     LCD ---------------------------  ARDUINO
 *
 * Vss pin (1)               to           GND
 * Vdd pin (2)               to           5V
 * VO/Contrast pin (3)       to           5V through a potentiometer 
 * RS pin (4)                to           digital pin 12
 * Enable pin (6)            to           digital pin 11
 * D4 pin (11)               to           digital pin 5
 * D5 pin (12)               to           digital pin 4
 * D6 pin (13)               to           digital pin 3
 * D7 pin (14)               to           digital pin 2
 * R/W pin (5)               to           GND
 * Led+ pin (15)             to           5V
 * Led- pin (16(             to           GND
*/

#include "string.h"
#include <LiquidCrystal.h>

char UPTIME[6];        //system uptime [h:min]
char CPU[7];           //cpu utilization [%]
char RAM[6];           //memory used [MB]
char NET[11];           //download rate [kB/s]
char FS[4];            //upload rate [kB/s]  
char TEMP[5];          //cpu temperature [°Celsius]

int numR = 4; //number of rows on lcd
int numC = 20; //number of columns on lcd

char string[40];      //string received from bash script via serial

LiquidCrystal lcd(12,11,6,5,4,3);   //constructor LiquidCrystal xxx (RS, Enable, D4, D5, D6, D7)

boolean flag=false;      

//Pattern (P_) of constant strings and custom chars to visualize on lcd
byte P_arrowDown[9]={B00000,B00000,B11111,B01110,B00100,B00000,B00000,B00000,};  //custom char arrow down
byte P_arrowUp[9]={B00000,B00000,B00000,B00100,B01110,B11111,B00000,B00000,};    //custom char arrow up
char P_header[20] = "MonitorDuino v_1.0";
char P_uptime[9]= "Uptime:";
char P_cpuUsage[12]="Cpu usage:";
char P_cpuTemp[11]= "Cpu temp:";
char P_mem[12]="Mem usage:";
char P_rx[10]="Rx rate:";
char P_disk[13]="Disk usage:";


void setup(){
lcd.createChar(1,P_arrowDown);        
lcd.createChar(2,P_arrowUp);
lcd.begin(numR,numC);
Serial.begin(115200);
attachInterrupt(0, changePage, RISING);        //interrupt on digital pin 2 (pushbutton through a pull-down resistor)
initPage1();
}


void initPage1(){
lcd.clear();
lcd.setCursor(0,0);
lcd.print(P_header);      //defined in MonitorDuinoStrings.h
lcd.setCursor(0,1);
lcd.print(P_uptime);
lcd.setCursor(0,2);
lcd.print(P_cpuUsage);
lcd.setCursor(0,3);
lcd.print(P_cpuTemp);
lcd.setCursor(19,3);
lcd.write(1);          //arrow DOWN
}


void initPage2(){
lcd.clear();
lcd.setCursor(0,0);
lcd.print(P_header);
lcd.setCursor(0,1);
lcd.print(P_mem);
lcd.setCursor(0,2);
lcd.print(P_rx);
lcd.setCursor(0,3);
lcd.print(P_disk);
lcd.setCursor(19,0);
lcd.write(2);          //arrow UP
}


void changePage (){        //invoked when an interrupt occurs
flag=!flag;
if (!flag) initPage1();
else initPage2();
}


void clearRow(int col, int row){        
int k;
    lcd.setCursor(col,row);
    for(k=col;k<numC;k++) lcd.write(' ');        //clear the row from the indicated column
    lcd.setCursor(col,row);                      //place the cursor 
}


void cleanString(char* string){                //fill string with ' ' until end 
int i=0;
  for(i;i<strlen(string);i++) string[i]=' ';              
}


void parseString(char *string){                  //split received string in UPTIME, CPU, RAM, NET, FS, TEMP
int j=0;
int k=0;

      //Input String Format:
      //UPTIME@CPU@RAM@NET@FS@TEMP'\n'
      
      //uptime
      cleanString(UPTIME);      
      while(string[j]!=64){
      UPTIME[k]=string[j];
      j++;k++;
      }
      
      //cpu usage
      cleanString(CPU);
      j++;k=0;
      while(string[j]!=64){
      CPU[k]=string[j];
      j++;k++;
      }

     //ram 
      cleanString(RAM);
      j++;k=0;
      while(string[j]!=64){
      RAM[k]=string[j];
      j++;k++;
      }

      //net
      cleanString(NET);
      j++;k=0;
      while(string[j]!=64){
      NET[k]=string[j];
      j++;k++;
      }
      
      //fs
      cleanString(FS);
      j++;k=0;
      while(string[j]!=64){
      FS[k]=string[j];
      j++;k++;
      }
      
      //temp
      cleanString(TEMP);    
      j++;k=0;
      while(string[j]!='\n'){
      TEMP[k]=string[j];
      j++;k++;
      }  
}


void loop(){
int i=0;   

//receive
while (Serial.available() > 0){    
            delay(10);
            string[i]=Serial.read();
            i++;
}

if (i!=0) {        //if i've receveid something

      for(i;i<strlen(string);i++) string[i]=' ';              //fill string with ' ' until end 
        
      parseString(string);

      if (!flag){          //PAG 1
      clearRow(11,1);
      lcd.print(UPTIME);
      clearRow(11,2);
      lcd.print(CPU);
      clearRow(11,3);
      lcd.print(TEMP);    
      clearRow(19,3);
      lcd.write(1);   //arrow DOWN      
      }
      
      else{                //PAG 2
      clearRow(13,1);
      lcd.print(RAM);
      clearRow(9,2);
      lcd.print(NET);   
      clearRow(13,3);
      lcd.print(FS);
      }
}

}


