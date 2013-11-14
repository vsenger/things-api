// ArduCAM demo (C)2013 Lee
// web: http://www.ArduCAM.com
// This program is a demo of how to use most of the functions
// of the library with a supported camera modules.
//
// This demo was made for Omnivision OV2640 sensor.
// 1. Set the sensor to JPEG output mode.
// 2. Capture and buffer the image to FIFO. 
// 3. Store the image to Micro SD/TF card with JPEG format.
// 4. Resolution can be changed by myCAM.OV2640_set_JPEG_size() function.
// This program requires the ArduCAM V3.0.0 (or above) library and Rev.C ArduCAM shield
// and use Arduino IDE 1.5.2 compiler

#include <UTFT_SPI.h>
#include <SD.h>
#include <Wire.h>
#include <ArduCAM.h>
#include <SPI.h>
#if defined(__arm__)
#include <itoa.h>
#endif

#define SD_CS 9 
// set pin 10 as the slave select for SPI:
const int slaveSelectPin = 10;

ArduCAM myCAM(OV2640,10);
//UTFT myGLCD(slaveSelectPin);

void setup()
{
#if defined (__AVR__)
  Wire.begin(); 
#endif
#if defined(__arm__)
  Wire1.begin(); 
#endif
  Serial.begin(115200);
  Serial.println("hello"); 
  // set the slaveSelectPin as an output:
  pinMode(slaveSelectPin, OUTPUT);

  // initialize SPI:
  SPI.begin(); 

  myCAM.write_reg(ARDUCHIP_MODE, 0x00);

  //myGLCD.InitLCD();
  myCAM.set_format(JPEG);

  myCAM.InitCAM();

  //myCAM.OV2640_set_JPEG_size(OV2640_320x240);
  myCAM.OV2640_set_JPEG_size(OV2640_1600x1200);

  //Initialize SD Card
  if (!SD.begin(SD_CS)) 
  {
    //while (1);		//If failed, stop here
  }

  Serial.println("init done");
  Serial.println(ARDUCHIP_TRIG);
  cap();
  
}

void cap() {
  char str[8];
  File outFile;
  static int k = 0;
  static int n = 0;
  uint8_t temp,temp_last;
  uint8_t start_capture = 0;
  Serial.println("starting capture");
  //Flush the FIFO 
  myCAM.flush_fifo();	
  //Clear the capture done flag 
  myCAM.clear_fifo_flag();		 
  //Start capture
  myCAM.start_capture();		 
  //Construct a file name
  k = k + 1;
  itoa(k, str, 10); 
  strcat(str,".jpg");
  //Open the new file  
  outFile = SD.open(str,FILE_WRITE);
  if (! outFile) 
  { 
    Serial.println("open file failed");
    return;
  }

  temp = myCAM.read_fifo();
  Serial.println("init write");

  outFile.write(temp);
  Serial.println("end write");

  //Read JPEG data from FIFO
  while( (temp != 0xD9) | (temp_last != 0xFF) )
  {
    temp_last = temp;
    temp = myCAM.read_fifo();
    //Write image data to file
    outFile.write(temp);
  }
  //Close the file 
  outFile.close(); 
  Serial.println("file closed");

  //Clear the capture done flag 
  myCAM.clear_fifo_flag();
  //Clear the start capture flag
  start_capture = 0;

}

void loop()
{
  char str[8];
  File outFile;
  static int k = 0;
  static int n = 0;
  uint8_t temp,temp_last;
  uint8_t start_capture = 0;

  //Wait trigger from shutter buttom   
  if(myCAM.read_reg(ARDUCHIP_TRIG) & SHUTTER_MASK)	
  {
    //Wait until buttom released
    while(myCAM.read_reg(ARDUCHIP_TRIG) & SHUTTER_MASK);
    start_capture = 1;
  }

  //Start capture when detect a valid shutter press  
  if(start_capture)
  {
    Serial.println("starting capture");
    //Flush the FIFO 
    myCAM.flush_fifo();	
    //Clear the capture done flag 
    myCAM.clear_fifo_flag();		 
    //Start capture
    myCAM.start_capture();		 
  }

  if(myCAM.read_reg(ARDUCHIP_TRIG) & CAP_DONE_MASK)
  {
    //Construct a file name
    k = k + 1;
    itoa(k, str, 10); 
    strcat(str,".jpg");
    //Open the new file  
    outFile = SD.open(str,FILE_WRITE);
    if (! outFile) 
    { 
      Serial.println("open file failed");
      return;
    }

    temp = myCAM.read_fifo();
    Serial.println("init write");

    outFile.write(temp);
    Serial.println("end write");

    //Read JPEG data from FIFO
    while( (temp != 0xD9) | (temp_last != 0xFF) )
    {
      temp_last = temp;
      temp = myCAM.read_fifo();
      //Write image data to file
      outFile.write(temp);
    }
    //Close the file 
    outFile.close(); 
    Serial.println("file closed");

    //Clear the capture done flag 
    myCAM.clear_fifo_flag();
    //Clear the start capture flag
    start_capture = 0;
  }
}







