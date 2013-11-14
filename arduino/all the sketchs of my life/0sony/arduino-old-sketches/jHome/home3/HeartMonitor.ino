#define HRMI_HOST_BAUDRATE 9600
#define HRMI_I2C_ADDR      127
#define MAX_IN_BUFFSIZE 16

char serInStr[MAX_IN_BUFFSIZE];   // Serial input string array
int numEntries = 0;               // Number of HR values to request
int numRspBytes;                  // Number of Response bytes to read
byte i2cRspArray[34];	          // I2C response array, sized to read 32 HR values
byte hrmi_addr = HRMI_I2C_ADDR;   // I2C address to use
boolean heartInit = false;
void heartMonitor_setup() {
}  

char* readHeartBeat() {
  if(!heartInit) {
    hrmi_open();
    heartInit=true;
  }
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {  
    //int val = map(i2cRspArray[2], 80,130,0, 150);
    //for(int x=0;x<34;x++) {
    if (++numEntries > 30) numEntries = 0;
    char r[1];// = { i2cRspArray[2] ]};
    return r;  
  }
  else { 
    char r[1]; return r; 
  }
}
void writeHeartBeat(char *c) {
 
}

void oldReadHeartBeat() {
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {  
    int val = map(i2cRspArray[2], 80,130,0, 150);
    //for(int x=0;x<34;x++) {
    Serial.print(i2cRspArray[2],DEC);
    Serial.flush();
  }
  if (++numEntries > 30) numEntries = 0;
}

void heartMonitor() {
  hrmiCmdArg(hrmi_addr, 'G', (byte) numEntries);      
  numRspBytes = numEntries + 2;
  if (hrmiGetData(hrmi_addr, numRspBytes, i2cRspArray) != -1) {  
    int val = map(i2cRspArray[2], 80,130,0, 150);
    //for(int x=0;x<34;x++) {
    Serial.print(i2cRspArray[2],DEC);
    //}
    Serial.println(" ");
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Heart Monitor");
    lcd.setCursor(0, 1);
    //char c*[];
    //toa(i2cRspArray[2],&c);
    String n = String(i2cRspArray[2]);

    lcd.print(i2cRspArray[2],DEC);

  }
  if (++numEntries > 30) numEntries = 0;
  //Kernel.wait(300);
}

