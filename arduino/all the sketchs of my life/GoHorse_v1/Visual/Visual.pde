/**
 * Simple Read
 * 
 * Read data from the serial port and change the color of a rectangle
 * when a switch connected to a Wiring or Arduino board is pressed and released.
 * This example works with the Wiring / Arduino program that follows below.
 */


import processing.serial.*;

Serial myPort;  // Create object from Serial class
int val;      // Data received from the serial port

void setup() 
{
  size(200,200,P3D);
  String portName = Serial.list()[0];
  myPort = new Serial(this, portName, 9600);
}

int valCorrigido = 0;
void draw()
{
  if ( myPort.available() > 0) { 
    val = myPort.read();         
    if(val=='x') {
      val = myPort.read();         
      
      if(val>0 && val<=90) valCorrigido=val;
      else valCorrigido = val-256;
    }

  }

  float t = map(valCorrigido,-90.0,+90.0, 0.0, 200.0);

  background(255);
  stroke(0);
  fill(175);
  translate(width/2, height/2);
  rotateX(PI*t/height);
  System.out.println(t);
  rectMode(CENTER);
  rect(0,0,100,100);
}




