/*
 * hrmi_funcs.h
 *
 * Arduino library to communicate with a HRMI using I2C
 * 
 * Written by Dan Julio, www.danjuliodesigns.com 
 * Version 1.0, 03/27/2008
 * Version 1.1  12/28/2008
 *
 */

#include <Arduino.h>

/*
 * hrmi_open: Initialize the I2C library
 *            This routine must be called before attempting to communicate with I2C
 */
void hrmi_open();


/*
 * hrmiCmdArg: Send a Command followed by a single Argument
 */
void hrmiCmdArg(byte addr, byte cmd, byte arg);


/*
 * hrmiCmd: Send a Command with no argument
 */
void hrmiCmd(byte addr, byte cmd);


/*
 * hrmiGetData: Get the specified number of data bytes from the HRMI into
 *              an array.
 */
int hrmiGetData(byte addr, byte numBytes, byte* dataArray);

