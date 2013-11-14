void delay_us(int x);
void delay_ms(int x);
void ioinit(void);
void reset(void);




// New
//************************************************************************
//*	adapted for Arduino and Mega by Mark Sproul
//*	Pin definitions
//*		RES	Reset			Digital Pin 8
//*		CS	Chip Select		Digital Pin 9
//*		DIO	Data I/O		Digital Pin 11
//*		SCK	Serial Clock	Digital Pin 13
//************************************************************************
//*		At first I tried to port it to the Arduino pin model, i.e. digitalWrite.
//*		As I feared, it introduced a noticeable decrease in speed.  For the LCD
//*		interface it needs to stay as it was. I now have it fully working on the
//*		mega utilizing the original code. This was done by modifying the pin
//*		definitions in the .h file.  Very straight forward and very clean. In fact
//*		it worked first shot on the Mega.
//************************************************************************
//*	these define the PORT and BIT number for each control pin
#define _USE_ARDUINO_FOR_NOKIEA_

//*	Arduino pin number defs
#define	LCD_RES_PIN	8
#define	CS_PIN		9
#define	DIO_PIN		11
#define	SCK_PIN		13

#ifdef __AVR_ATmega1280__
	//*	Arduino Mega bit numbers
	#define LCD_RES		5		// D8
	#define CS			6		// D9
	#define DIO			5		// D11
	#define SCK			7		// D13

	//*	Arduino Mega ports
	#define	LCD_PORT_CS		PORTH
	#define	LCD_PORT_SCK	PORTB
	#define	LCD_PORT_RES	PORTH
	#define	LCD_PORT_DIO	PORTB
#else
	//*	Arduino Duemilanove bit numbers
	#define LCD_RES		0		// D8
	#define CS			1		// D9
	#define DIO			3		// D11
	#define SCK			5		// D13
	//#define LCD_PORT	PORTB

	//*	Arduino Duemilanove ports
	#define	LCD_PORT_CS		PORTB
	#define	LCD_PORT_SCK	PORTB
	#define	LCD_PORT_RES	PORTB
	#define	LCD_PORT_DIO	PORTB
#endif


/* Old
#define CS			2		// D10
#define SCK			3		// D11
#define LCD_RES		1		// D9
#define DIO			4		// D12
#define LCD_PORT	PORTB
*/

//*******************************************************
//						Macros
//*******************************************************
#define sbi(var, mask)   ((var) |= (uint8_t)(1 << mask))
#define cbi(var, mask)   ((var) &= (uint8_t)~(1 << mask))

//*******************************************************
//					General Definitions
//*******************************************************
#define MYUBRR 16	//Used to set the AVR Baud Rate TO 115200 (External 16MHz Oscillator)