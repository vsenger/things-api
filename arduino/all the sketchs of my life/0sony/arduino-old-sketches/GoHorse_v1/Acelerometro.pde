//SOBRE O EIXO X - vertical
// frente ele vai de 255 at 190 e depois 190 ate 255
// tras ele vai de 0 a 64

//SOBRE O EIXO Y - horizontal
// plano 233, inclinando para esquerda 172
// plano 233, inclinando para direita 255, 0, 46

//SOBRE O EIXO Z - 
#include <Wire.h>

#define ACCELEROMETER 0x1D //Address for Accelerometer

//REGISTERS
#define MODE_CONTROL  0x16 //Mode control register
#define PULSE_DET     0x1B //Pulse detection threshold limit value
#define X_OUT	      0x06 //8 bit register containing value for X
#define Y_OUT	      0x07 //8 bit register containing value for Y
#define Z_OUT	      0x08 //8 bit register containing value for Z
#define DETECTION     0x0A //Detection source register

//VALUES
#define Z_PULSE	 0x40 //Pulse detected on Z-axis
#define SENSEVALUE    0x25 //Default sensitivity level

void setupAcelerometro() {
  Wire.begin();
  accWrite(MODE_CONTROL, SENSEVALUE);

}

//function to write byte data into a register
void accWrite(byte address, byte data) {
  Wire.beginTransmission(ACCELEROMETER);
  Wire.send(address);
  Wire.send(data);
  Wire.endTransmission();
}

//function to read byte data from a register
byte accRead(byte address){
  byte val = 0x00;
  Wire.beginTransmission(ACCELEROMETER);
  Wire.send(address);
  Wire.endTransmission();
  Wire.requestFrom(ACCELEROMETER, 1);
  if(Wire.available() > 0) {
    val = Wire.receive();
  }
  Wire.endTransmission();
  return val;
}

void debugAcelerometro() {
  Serial.print("X:");
  Serial.println((unsigned int)accRead(X_OUT));
  Serial.print("Y:");
  Serial.println((unsigned int)accRead(Y_OUT));
  Serial.print("Z:");
  Serial.println((unsigned int)accRead(Z_OUT));
  delay(100);
}
byte eixoXparaAngulo(int valor) {
  if(valor>=190 && valor<=255) {
    return map(valor,255,192,0,90);
  }
  else if(valor>=0 && valor<=64) {
    return map(valor,0,64,1,90)*-1;
  }
  else {
    return 0;
  }
}

int lerEixoX() {
  return (int)accRead(X_OUT);
}
byte eixoYparaAngulo(int valor) {
  if(valor>=172 && valor<=233) {
    return map(valor,172,233,90,0);
  }
  else if((valor>=0 && valor<=46) || valor>233 && valor<=255 ) {
    valor = valor > 233 ? valor = 256-valor : 23+valor;
    
    return map(valor,0,69,1,90)*-1;
  }
  else {
    return 0;
  }
}
int lerEixoZ() {
  return (int)accRead(Z_OUT);
}
int lerEixoY() {
  return (int)accRead(Y_OUT);
}
