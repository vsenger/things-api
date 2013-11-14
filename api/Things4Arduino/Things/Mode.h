
class Mode {

 private:
  typedef void (*ISR)();
  ISR setupFunction;
  ISR modeFunction;
  int hasSetup;
public:
   Mode();
   Mode(ISR);
   Mode(ISR, ISR);
   void execute();
   void setup();

};

