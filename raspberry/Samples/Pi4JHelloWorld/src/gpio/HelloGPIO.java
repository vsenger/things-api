package gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public final class HelloGPIO {

    public static void main(String[] args) throws Exception {
        if(args.length!=2) {
            System.out.println("Usage: HelloGPIO <blinks> <interval>");
            return;
        }
        int numberOfBlinks, interval;
        numberOfBlinks = Integer.parseInt(args[0]);
        interval = Integer.parseInt(args[1]);

        GpioPinDigitalOutput myLed;
        GpioController gpio = GpioFactory.getInstance();
        myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);

        for (int x = 0; x < numberOfBlinks; x++) {
            myLed.setState(true);
            Thread.sleep(interval);
            myLed.setState(false);
            Thread.sleep(interval);
        }
    }
}