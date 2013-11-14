#define LDR 5
int led[] = {14,1,2,3,4,5,8,6,13};
int luminosidade;


void setup() {
  for(int x=0;x<9;x++) {
    pinMode(led[x], OUTPUT);
  }
}

void loop() {
  lerLuz();
  leds();
  
}


