//*******************************************************
//					Nokia Shield
//*******************************************************
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>

//*******************************************************
//					External Component Libs
//*******************************************************
#include "LCD_driver.h"
#include "nokia_tester.h"

static int uart_putchar(char c, FILE *stream);
uint8_t uart_getchar(void);
static FILE mystdout = FDEV_SETUP_STREAM(uart_putchar, NULL, _FDEV_SETUP_WRITE);

int main (void)
{
//*******************************************************
//					Main Code
//*******************************************************
	//Initialize ARM I/O
	ioinit();
	
	printf("Initializing\n");
	
	//Show the splash-screen (Sparkfun Logo)
	LCDInit();			//Initialize the LCD
	
	printf("Init. Done\n");
	
	LCDClear(BLACK);
	printf("Logo...");
	LCDPrintLogo();
	
	while(1){
		if((PIND & (1<<3)) == 0)
		{
			LCDClear(GREEN);
			LCDPrintLogo();
		}
		if((PIND & (1<<4)) == 0)
		{
			LCDClear(PINK);
			LCDPrintLogo();
		}
		if((PIND & (1<<5)) == 0)
		{
			LCDClear(BLACK);
			LCDPrintLogo();
		}
	}
    return 0;
}

void ioinit(void)
{
	DDRB = ((1<<CS)|(1<<DIO)|(1<<SCK)|(1<<LCD_RES));	//Set the control pins as outputs

	// USART Baud rate: 115200 (With 16 MHz Clock)
    UBRR0H = (MYUBRR >> 8) & 0x7F;	//Make sure highest bit(URSEL) is 0 indicating we are writing to UBRRH
	UBRR0L = MYUBRR;
    UCSR0A = (1<<U2X0);					//Double the UART Speed
	UCSR0B = (1<<RXEN0)|(1<<TXEN0);		//Enable Rx and Tx in UART
    UCSR0C = (1<<UCSZ00)|(1<<UCSZ01);		//8-Bit Characters
    stdout = &mystdout; //Required for printf init
	cli();
	
	// Arduino D3-5 are button pins
	// This is PD3-5
	DDRD = 0x00;
	PORTD = 0xFF;
	
	// Init timer 2
	//Set Prescaler to 8. (Timer Frequency set to 16Mhz)
	//Used for delay routines
	TCCR2B = (1<<CS20); 	//Divde clock by 1 for 16 Mhz Timer 2 Frequency	
}



static int uart_putchar(char c, FILE *stream)
{
  if (c == '\n')
    uart_putchar('\r', stream);
  
  loop_until_bit_is_set(UCSR0A, UDRE0);
  UDR0 = c;
  return 0;
}

uint8_t uart_getchar(void)
{
    while( !(UCSR0A & (1<<RXC0)) );
	return(UDR0);
}

//General short delays
void delay_ms(int x)
{
    for (; x > 0 ; x--)
        delay_us(1000);
}

//General short delays
void delay_us(int x)
{    
    TIFR2 = (1<<TOV2); //Clear any interrupt flags on Timer2
    TCNT2= 240; //Setting counter to 239 will make it 16 ticks until TOV is set. .0625uS per click means 1 uS until TOV is set
    while(x>0){
		while( (TIFR2 & (1<<TOV2)) == 0);
		TIFR2 = (1<<TOV2); //Clear any interrupt flags on Timer2
		TCNT2=240;
		x--;
	}
} 