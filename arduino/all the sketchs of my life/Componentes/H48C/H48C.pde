//// VARS
int CS_pin = 9;
int DIO_pin = 10;
int CLK_pin = 11;

byte tempLSB = 0;
byte tempMSB = 0;

int aX = 0;
int aY = 0;
int aZ = 0;

//// FUNCTIONS
void StartMassurement() {
  pinMode(DIO_pin, OUTPUT);
  digitalWrite(CS_pin, LOW);
  digitalWrite(CLK_pin, LOW);
  delayMicroseconds(1);
  digitalWrite(DIO_pin, HIGH);
  digitalWrite(CLK_pin, HIGH);
  delayMicroseconds(1);

  }

void ShiftOutNibble(byte DataOutNibble) {
  for(int i = 3; i >= 0; i--) { // i = 3 ... 2 ... 1 ... 0
    digitalWrite(CLK_pin, LOW);
    // set DIO first
    if ((DataOutNibble & (1 << i)) == (1 << i)) {  // DataOutNibble AND 1 x 2^i Equals 1 x 2^i ?
	digitalWrite(DIO_pin, HIGH);

	}
    else {
	digitalWrite(DIO_pin, LOW);

	}
    // with CLK rising edge the chip reads the DIO from arduino in
    digitalWrite(CLK_pin, HIGH);
    // data rate is f_clk 2.0 Mhz --> 0,5 micro seeconds
    delayMicroseconds(1); // :-) just nothing
  }

}

void SampleIt() {
  digitalWrite(CLK_pin, LOW);
  delayMicroseconds(1);
  digitalWrite(CLK_pin, HIGH);
  delayMicroseconds(1);

  pinMode(DIO_pin, INPUT);
  digitalWrite(CLK_pin, LOW);
  delayMicroseconds(1);
  digitalWrite(CLK_pin, HIGH);
  if (digitalRead(DIO_pin)== LOW) {
    // Blink LED because ok
    }
}

byte ShiftInNibble() {
  byte resultNibble;
  resultNibble = 0;

    for(int i = 3 ; i >= 0; i--) {
	// The chip Shift out results on falling CLK
	digitalWrite(CLK_pin, LOW);
	delayMicroseconds(1); // :-) just nothing
	if( digitalRead(DIO_pin) == HIGH) {
	  resultNibble += 1 << i;
	}
	else {
	  resultNibble += 0 << i;
	}
	digitalWrite(CLK_pin, HIGH);
    }
return resultNibble;
}

void EndMessurement() {
  digitalWrite(CS_pin, HIGH);
  digitalWrite(CLK_pin, HIGH);
}

int GetValue(byte Axis) { // x = B1000, y = 1001, z = B1010
  int Result = 0;
  StartMassurement();
  ShiftOutNibble(Axis);
  SampleIt();
  Result =  (ShiftInNibble() << 8) + (ShiftInNibble() << 4) + ShiftInNibble();
  EndMessurement();

  return Result;
  }

//// SETUP
void setup() {
  Serial.begin(9600);
  pinMode(CS_pin, OUTPUT);
  pinMode(CLK_pin, OUTPUT);
  pinMode(DIO_pin, OUTPUT);
  // initialize device & reset
  digitalWrite(CS_pin,LOW);
  digitalWrite(CLK_pin,LOW);
  delayMicroseconds(1);
  digitalWrite(CS_pin, HIGH);
  digitalWrite(CLK_pin,HIGH);
}


//// LOOP
void loop() {
  Serial.print("X");
  Serial.print(2048 - GetValue(B1000));
  Serial.print("Y");
  Serial.print(2048 - GetValue(B1001));
  //Serial.print(2048 - GetValue(B1010));


  Serial.println("");
  delay(100);
}


 


