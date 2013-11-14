/*
 * hrmi_funcs.cpp
 *
 * Arduino library to communicate with a HRMI using I2C
 * 
 * Written by Dan Julio, www.danjuliodesigns.com 
 * Version 1.1, 12/28/2008
 *
 */
 
 #include <Wire.h>
 #include "hrmi_funcs.h"
 
 /*
 * hrmi_open: Initialize the I2C library
 *            This routine must be called before attempting to communicate with I2C
 */
void hrmi_open()
{
  Wire.begin();
}


/*
 * hrmiCmdArg: Send a Command followed by a single Argument
 */
void hrmiCmdArg(byte addr, byte cmd, byte arg)
{
  Wire.beginTransmission(addr);
  Wire.write(cmd);
  Wire.write(arg);
  Wire.endTransmission();
}


/*
 * hrmiCmd: Send a Command with no argument
 */
void hrmiCmd(byte addr, byte cmd)
{
  Wire.beginTransmission(addr);
  Wire.write(cmd);
  Wire.endTransmission();
}


/*
 * hrmiGetData: Get the specified number of data bytes from the HRMI into
 *              an array.
 */
int hrmiGetData(byte addr, byte numBytes, byte* dataArray)
{
  Wire.requestFrom(addr, numBytes);
  if (Wire.available()) {
    for (int i=0; i<numBytes; i++)
      dataArray[i] = Wire.read();
    return(0);
  }
  return(-1);
}
