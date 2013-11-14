IRsend irsend;
//unsigned int rawCodes[] = {  19850,1250,450,1200,450,400,1250,1250,400,400,1250,400,1250,450,1200,450,1250,1250,400,400,1250,450,1200,450};
// m1150 s550 m1100 s550 m300 s1300 m1200 s500 m300 s1350 m300 s1350 m350 s1300 m350 s1300 m1150 s550 m300 s1350 m300 s1350 m300

unsigned int rawUp[] = {  1150 ,550 ,1100 ,550 ,300 ,1300 ,1200 ,500 ,300 ,1350 ,300 ,1350 ,350 ,1300 ,350 ,1300 ,1150 ,550 ,300 ,1350 ,300 ,1350 ,300};
unsigned int rawDown[] =   {  1150 ,550 ,1100 ,550 ,300 ,1350 ,1100 ,550 ,300 ,1350 ,300 ,1350 ,1150 ,500 ,300 ,1400 ,300 ,1300 ,350 ,1350 ,300 ,1350 ,300};


char* irSender_write(char* args) {
  int codigo = atoi(args);
   if(codigo==0) { 
     irsend.sendRaw(rawUp,23,38); 
   }   
   else if(codigo==1) {
     irsend.sendRaw(rawDown,23,38); 
   }
   //else if(codigo==2) { //Faltou o STOP da tela

   //}
}

//Nao tem leitura de emissor de IR...
char* irSender_read() {
   char* r="\0";
}

