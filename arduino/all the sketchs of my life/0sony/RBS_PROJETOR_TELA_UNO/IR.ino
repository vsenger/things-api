IRsend irsend;
char* irSender_write(char* args) {
  int codigo = atoi(args);
   if(codigo==0) {
     irsend.sendNEC(0x81CEE01F,32); //on off 
   }   
   else if(codigo==1) {
     irsend.sendNEC(0x81CE807F,32); //Phono 
   }
   else if(codigo==2) {
     irsend.sendNEC(0x81CEC03F,32); //FM
   }
   else if(codigo==3) {
     irsend.sendNEC(0x81CE40BF,32); //CD
   }
   else if(codigo==4) {
     irsend.sendNEC(0x81CE08F7,32); //AUX
   }
   else if(codigo==5) {
     irsend.sendNEC(0x81CE20DF,32); //TAPE MONITOR 
   }
   else if(codigo==6) {
     irsend.sendNEC(0x81CE609F,32); //Volume Down 
   }
   else if(codigo==7) {
     irsend.sendNEC(0x81CEA05F,32); //Volume UP
   }
   else if(codigo==8) {
     irsend.sendNEC(0x81CE10EF,32); //MUTE     
   }  
}

//Nao tem leitura de emissor de IR...
char* irSender_read() {
   char* r="\0";
}

