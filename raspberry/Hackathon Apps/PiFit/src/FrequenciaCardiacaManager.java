//package br.com.pifit.batimento;

import java.io.IOException;

import org.things.Device;
import org.things.Things;
import org.things.device.SerialDevice;

public class FrequenciaCardiacaManager {
	private static Device things;

	public static void iniciar(String porta) throws Exception{
		things = new SerialDevice(porta, 9600);
		things.open();
		Things.delay(1500);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static FrequenciaCardiaca ler() throws IOException, Exception {
		//Device things = new SerialDevice(porta, 9600);
	        //things.open();
        	//Things.delay(1500);
        	things.send("G1\r");
        	Things.delay(100);

        	String s = things.receive();
		String batimento = null;
		FrequenciaCardiaca fc;
		if(s != null){
			batimento = s.split(" ")[2];
			fc = new FrequenciaCardiaca(Integer.valueOf(batimento));
		}else{
			throw new Exception("O sensor retornou nulo.");
		}
        	//things.close();

		return fc;
	}

	public static void encerrar() throws Exception{
		things.close();
	}

}
