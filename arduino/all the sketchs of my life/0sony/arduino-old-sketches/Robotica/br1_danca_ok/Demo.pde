/*
1.5'                   3.2'
 C              E       Am 
 Fa�o c�digo como c�o;Maldito bug, que n�o encontro n�o;
 5.5'                    8' com breque
 Vem o prazo e o chef�o, o redeploy n�o da certo n�o;
 10'               13'
 G        F  C
 Ooooolha o prazo, ta demorando, ta demorando 
 15'              17'             18'
 Oooolha o teste, to implantando, to implantando
 19'             20.5     23'                   26'
 E               Am       E                    Am
 Estou esperando release, vai ficar muito mais easy
 27.8'                   30'

28'                      30'          31'
 G           F       C
 Se voc� come�a de novo, um bug novo, um bug novo
 32'                         34'          35'
 Estou implorando socorro, pro bug novo, pro bug novo
 36'
 G           F
 J� estou penando aqui
 38'                                41'
 C                  E               Am
 Fa�o o download de um jar grand�o, mas tamb�m n�o           42'
 funciona n�o
 43'                        45'                     48'
 Chamo o arquiteto bacan�o, mas no fundo � um man�z�o
 49.5'
 G    F C
 Olha o budget, ce ta gastando, ce gastando
 Olha o prazo, ce ta furando, ce ta furando
 E                   Am
 N�o me sinto up-to-date, eu estou � �deprecate�
 */


void Demo() {

  /*1.5'                   3.2'
   C              E       Am 
   Fa�o c�digo como c�o;Maldito bug, que n�o encontro n�o;
   5.5'                    8' com breque
   Vem o prazo e o chef�o, o redeploy n�o da certo n�o;
   */
  delay(1500);
  andarFrente(1700,4);
  andarTraz(2300,4);
  girarNoEixo(2000,3,1);
  andarTraz(1500,4);
  parar();
  delay(1500);

  /* 10'               13'
   G        F  C
   Ooooolha o prazo, ta demorando, ta demorando 
   */
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);   
  changeServo(0);
  delay(1000);   
  changeServo(90);
  andarFrente(2000,4);
  parar();

  /* 15'              17'             18'
   Oooolha o teste, to implantando, to implantando
   */
  changeServo(180);
  delay(1000);
  changeServo(0);
  delay(1000);    
  changeServo(90);
  delay(1000);    

  /* 19'             20.5     23'                   27'
   E               Am       E                    Am
   Estou esperando release, vai ficar muito mais easy
   */
  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);      
  changeServo(0);
  changeServo(90);


/*28'                      30'          31'
 G           F       C
 Se voc� come�a de novo, um bug novo, um bug novo
 32'                         34'          35'
 Estou implorando socorro, pro bug novo, pro bug novo
 36'*/

  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1000);
  andarFrente(2000,3);
  parar();
  
  /* Ja estou penando aqui */
  
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1500);
  
  /* Faco download de um jar grandao */  
  andarFrente(1700,4);
  /* Mas tamb�m n�o funciona n�o */
  andarTraz(2300,4);
  /*Chamo o arquiteto bacanao */
  girarNoEixo(2000,3,1);
  /*Mas no fundo � um manezao */
  andarTraz(2000,4);
  
  parar();
  delay(2000);

  /* Olha o budget */
  girarNoEixo(2000,3,0);
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);   
  changeServo(0);
  delay(1000);   
  changeServo(90);
 /* Olha o prazo */
  girarNoEixo(2000,3,1);
  changeServo(180);
  delay(500);
  changeServo(0);
  delay(500);
  changeServo(90);
  delay(1000);   
  
  /* Nao me sinto uptodate, eu estou deprecated */
   girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(1000);
  changeServo(90);
  delay(1000);      
  changeServo(0);
  changeServo(90);
 

  girarNoEixo(3000,3,1);
  parar();
  girarNoEixo(3000,3,0);
  parar();
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(1000);
  andarFrente(2000,3);
  parar();
  andarTraz(2000,3);
  parar();
  
  changeServo(180);
  delay(500);
  changeServo(90);
  delay(500);      
  changeServo(0);
  delay(500);      
  changeServo(90);
  delay(200000000);  

}  


