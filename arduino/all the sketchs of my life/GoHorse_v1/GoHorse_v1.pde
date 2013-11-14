#include <SdFat.h>

// SD chip select pin
const uint8_t chipSelect = SS_PIN;
#define LED_ERROR 4
#define LED_OK 7
// file system object
// store error strings in flash to save RAM
#define error(s) sd.errorHalt_P(PSTR(s))

SdFat sd;
volatile int mode=0;

long lastDebounce=0;
long debounceDelay=50;

void setup() {
  Serial.begin(9600);
  attachInterrupt(0, changeMode, LOW); //PORTA 21, quando estiver subindo

  pinMode(4, OUTPUT);
  pinMode(7, OUTPUT);
  setupAcelerometro();
  digitalWrite(LED_OK, HIGH);
  delay(100);
  digitalWrite(LED_OK, LOW);
}

//2805020000
void loop() {
  if(mode==1) coletar();
  else if(mode==0) idle();

  //Serial.print('x');
  //Serial.print(eixoXparaAngulo(lerEixoX()));
  //cout << endl << "Done" << endl;

  //debugAcelerometro();
  //delay(100);
}

void changeMode() {
  if(millis()-lastDebounce>debounceDelay) {
    mode = mode== 1 ? 0 : 1;
    lastDebounce=millis();
  }
}
void idle() {
  digitalWrite(LED_OK, HIGH);
  delay(2000);
  digitalWrite(LED_OK, LOW);
  delay(2000);     
}
  
void coletar() {
  if(mode!=1) return;
  char name[] = "APPEND.TXT";

  // initialize the SD card at SPI_HALF_SPEED to avoid bus errors with
  // breadboards.  use SPI_FULL_SPEED for better performance.
  if (!sd.init(SPI_HALF_SPEED, chipSelect)) sd.initErrorHalt();

  ofstream sdout(name, ios::out | ios::app);
  if (!sdout) {
    //error("open failed");
    digitalWrite(LED_ERROR, HIGH);
    delay(500);
    digitalWrite(LED_ERROR, LOW);
    delay(500);     
  }
  // use int() so byte will print as decimal number
  sdout << "x: " << lerEixoX();
  sdout << " millis = " << millis() << endl;
  digitalWrite(LED_OK, HIGH);
  delay(20);
  digitalWrite(LED_OK, LOW);
  delay(20);
  // close the stream
  sdout.close();
  /*if (!sdout) {
   digitalWrite(LED_ERROR, HIGH);
   delay(500);
   digitalWrite(LED_ERROR, LOW);
   delay(500);     
   //error("append data failed");
   }
   else {
   digitalWrite(LED_ERROR, HIGH);
   digitalWrite(LED_OK, HIGH);
   delay(500);
   digitalWrite(LED_ERROR, LOW);
   digitalWrite(LED_OK, LOW);
   delay(500);     
   }*/



}






