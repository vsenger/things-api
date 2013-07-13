/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vsenger
 */
import org.things.robot.Robot;

public class Teste {

    static int distancia;

    public static void main(String[] args) {
        Robot.parar();
        
        System.out.println(Robot.sensorDistancia(2));
        Robot.mudarServo(90);
        distancia = Robot.sensorDistancia(2);
        while (distancia > 15) {
            Robot.frente(3);
            distancia = Robot.sensorDistancia(2);

        }
        if (distancia > 0 && distancia < 15) {
            Robot.girar(3);
            Robot.esperar(1500);
            Robot.movimentoAleatorio(3);
            Robot.esperar(1000);
        }


    }
}