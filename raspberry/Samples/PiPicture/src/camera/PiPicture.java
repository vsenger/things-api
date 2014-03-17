package camera;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import java.io.IOException;
import java.util.concurrent.Callable;

public final class PiPicture {

    static boolean alternate;
    public static String PICTUREFILENAME = "picamera";
    static Runtime rt = Runtime.getRuntime();
    static int increment;

    public static void main(String[] args) {
        final GpioPinDigitalOutput myLed[] = new GpioPinDigitalOutput[3];
        try {
            final GpioController gpio = GpioFactory.getInstance();
            final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,
                    PinPullResistance.PULL_DOWN);
            // setup gpio pins #04, #05, #06 as an output pins and make sure they are all LOW at startup
            myLed[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "LED #1", PinState.LOW);
            myLed[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LED #2", PinState.LOW);
            myLed[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "LED #3", PinState.LOW);
            System.out.println("Starting camera");
            myLed[0].setState(true);
            Thread.sleep(500);
            myLed[0].setState(false);
            Thread.sleep(500);
            myLed[1].setState(true);
            Thread.sleep(500);
            myLed[1].setState(false);
            Thread.sleep(500);
            myLed[2].setState(true);
            Thread.sleep(500);
            myLed[2].setState(false);
            Thread.sleep(500);
            myLed[0].setState(true);
            myButton.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
                public Void call() throws Exception {
                    if (!alternate) {
                        try {
                            myLed[1].setState(true);
                            Process pr1 = rt.exec("raspistill -o /home/pi/pictures/" + PICTUREFILENAME + increment++ + ".jpg");
                            pr1.waitFor();
                            myLed[1].setState(false);

                        } catch (IOException ex) {
                            myLed[2].setState(true);
                            Thread.sleep(500);
                            myLed[2].setState(false);
                            Thread.sleep(500);
                            myLed[2].setState(true);
                            Thread.sleep(500);
                            myLed[2].setState(false);
                            Thread.sleep(500);
                            ex.printStackTrace();
                        }
                        alternate = true;

                    } else {
                        alternate = false;
                    }
                    System.out.println(
                            " --> GPIO TRIGGER CALLBACK RECEIVED " + increment);

                    return null;
                }
            }));

            // keep program running until user aborts (CTRL-C)
            for (;;) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Something was wrong...");
            e.printStackTrace();
        } finally {
            
        }
    }

}