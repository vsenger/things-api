/*
 * Demonstration program for communicating with the HRMI over a serial interface
 */

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <errno.h>
#include <paths.h>
#include <termios.h>
#include <sysexits.h>
#include <sys/param.h>
#include <sys/select.h>


// Define a constant specifying the largest response string we could get from the HRMI
#define MAX_RSP_CHARS  140


// Global to hold original serial port attributes
static struct termios gOriginalTTYAttrs;


//
// OpenSerialPort: Routine to open and configure the serial prot
//
static int OpenSerialPort(const char *deviceFilePath)
{
    // variables
    int fd = -1;              // file descriptor for serial port
    struct termios options;   // serial port configuration options

    // Open the serial port
    if ((fd = open(deviceFilePath, O_RDWR | O_NOCTTY )) == -1) {
        printf("Error opening serial port %s - %s(%d)\n",
               deviceFilePath, strerror(errno), errno);
        return(-1);
    }

    // Prevent other processes from opening the serial port
    if (ioctl(fd, TIOCEXCL) == -1) {
        printf("Error setting TIOCEXCL on %s - %s(%d)\n",
               deviceFilePath, strerror(errno), errno);
        return(-1);
    }

    // Get the serial port current options and save them to restore on exit
    if (tcgetattr(fd, &gOriginalTTYAttrs) == -1) {
        printf("Error getting tty attributes %s - %s(%d)\n",
               deviceFilePath, strerror(errno), errno);
        return(-1);
    }

    // Configure the serial port
    options = gOriginalTTYAttrs;
    //   Set raw input (non-canonical) mode, with reads blocking until either a
    //   single character has been received or a one second timeout expires
    cfmakeraw(&options);
    options.c_cc[VMIN] = 1;
    options.c_cc[VTIME] = 10;
    //    Set the baud rate and word length
    cfsetspeed(&options, B9600);
    options.c_cflag |= (CS8);
    //    Cause the new options to take effect immediately
    if (tcsetattr(fd, TCSANOW, &options) == -1) {
        printf("Error setting tty attributes!\n");
        return(-1);
    }

    return(fd);
}



//
// CloseSerialPort: Close our connection and restore the original settings
//
void CloseSerialPort(int fd)
{
    // Block until all written output has been sent from the device
    if (tcdrain(fd) == -1) {
        printf("Error waiting for drain - %s(%d)\n", strerror(errno), errno);
    }

    // Reset the serial port back to the state in which we found it
    if (tcsetattr(fd, TCSANOW, &gOriginalTTYAttrs) == -1) {
        printf("Error restoring tty attributes - %s(%d)\n", strerror(errno), errno);
    }

    // Close the port
    close(fd);
}



//
// SendGetHeartRate: Function to send a command to get the number of heart rate values
//                   specified in NumEntries
//
//                   Returns 1 for success, 0 for failure
//
int SendGetHeartRate(int fd, int NumEntries)
{
    char SendCommand[8];      // Array sized to hold the largest command string
    int  CmdLength;           // Number of characters in the command string

    // Validate NumEntries
    if (NumEntries < 0)
        NumEntries = 0;
    else if (NumEntries > 32)
        NumEntries = 32;

    // Build the command string
    //   Note: "\015" is the carriage return character
    CmdLength = sprintf(SendCommand, "G%0d\015", NumEntries);

    // Send the command string
    return(write(fd, SendCommand, CmdLength) == CmdLength);
}



//
// GetResponseString: Function to read a response string back from the HRMI
//
int GetResponseString(int fd, char* ResponseString)
{
    char b[2];
    int i = 0;

    do {
        int n = read(fd, b, 1);     // read a char at a time
        if (n == -1)
            return(-1);             // read failed
        if (n == 0) {
            usleep(10 * 1000);      // wait 10 msec before trying again
            continue;
        }

        ResponseString[i] = b[0];   // store the character
        i++;

    // repeat until we see the <CR> character or exceed the buffer
    } while ((b[0] != 0x0D) && (i < MAX_RSP_CHARS));

    ResponseString[i-1] = 0;                   // null terminate the string (replace the <CR>)
    return(0);
}



int main()
{
    int sfd;                      // serial port file descriptor
    int i = 0;                    // loop counter
    char RspBytes[MAX_RSP_CHARS]; // Response string 
    int numBytes;

    // Open the serial port device associated with the HRMI
    if ((sfd = OpenSerialPort("/dev/cu.usbserial-A9003PDh")) == -1) {
        return(-1);
    }

    // Send a series of Get Heart Rate commands, each time requesting more history buffer
    // entries
    while (i++ < 33) {
        if (SendGetHeartRate(sfd, i) == 0) {
            printf("Error: SendGetHeartRate failed!\n");
            break;
        }

        if (GetResponseString(sfd, RspBytes) == -1) {
            printf("Error: GetResponseString failed!\n");
            break;
        } else {
            printf("Request %2d => %s\n", i, RspBytes);
        }

        sleep(1);
    }

    CloseSerialPort(sfd);
}
