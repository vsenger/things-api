//
// Demonstration program for communicating with the HRMI over a serial interface
//


// Include the serial library
import processing.serial.*;

// Variable declaration
Serial port;                           // The serial port
byte[] rspCharArray = new byte[32];    // Where we'll put the raw data read from the HRMI device
int[] rspArgArray = new int[3];        // Where we'll put the converted response values
int validData = 0;
int CR = 13;                           // <CR> constant

void setup() {
  // Open a specific serial device (this will change for each HRMI device)
  port = new Serial(this, "/dev/tty.usbserial-A9003PDh", 9600);
  
  // Setup the serialEvent to be called when we receive complete response
  // packets from the HRMI device
  port.bufferUntil(CR);
  
}

void draw() {

  // Send a command to get a single heart rate value
  validData = 0;
  port.write('G');
  port.write('1');
  port.write(CR);
  
  // Wait for a response from the HRMI device
  while (validData == 0) {
      delay(1000);                    // Delay 1 second between checks
  }
  
  // Display mode, count and heartrate
  if ((rspArgArray[0] & 0x01) == 0x01)
    print("Averaged mode ");
  else
    print("Raw mode ");    
  print(rspArgArray[1]); print(" "); // Count
  println(rspArgArray[2]);           // Heart rate
}

// Catch the event from the serial interface.  Make sure there is
// actually data to read before attempting to do any processing.
void serialEvent(Serial port) {
      if (port.readBytesUntil(CR, rspCharArray) != 0) {
        // Read bytes until we get to the end of the packet converting
        // each ASCII digit into a number.  We make use of the space
        // character between sets of digits to delimit numbers.
        //    Argument 0: Status Flags
        //    Argument 1: Second Count
        //    Argument 2: Heartrate
        //
        int ArgIndex = 0;
        int CharIndex = 0;
        for (int i=0; i<3; i++) rspArgArray[i] = 0;
        while (rspCharArray[CharIndex] != CR) {
          if (rspCharArray[CharIndex] != ((byte) ' ')) {
            rspArgArray[ArgIndex] = (rspArgArray[ArgIndex]*10) + (rspCharArray[CharIndex] - ((byte) '0'));
          } else {
            ArgIndex++;
          }
          CharIndex++;
        }
        validData = 1;
      }
}
