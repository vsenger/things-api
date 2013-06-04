/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.things.robot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.things.device.InternetDevice;

/**
 *
 * @author vsenger
 */
public class Robot {

    public void configurarIP(String ip) {
        robot = new InternetDevice(ip + ":8080/things");
    }
    private static InternetDevice robot = new InternetDevice("192.168.1.218:8080/things");

    public static void frente(int intensidade) {
        try {
            robot.send("frente/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void re(int intensidade) {
        try {
            robot.send("re/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void esquerda(int intensidade) {
        try {
            robot.send("esquerda/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void direita(int intensidade) {
        try {
            robot.send("direita/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void reEsquerda(int intensidade) {
        try {
            robot.send("reEsquerda/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void reDireita(int intensidade) {
        try {
            robot.send("reDireita/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void girar(int intensidade) {
        try {
            robot.send("girar/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void girarH(int intensidade) {
        try {
            robot.send("girarH/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void girarAH(int intensidade) {
        try {
            robot.send("girarAH/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void movimentoAleatorio(int intensidade) {
        try {
            robot.send("aleatorio/" + intensidade);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mudarServo(int grau) {
        try {
            robot.send("servo/" + grau);
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void parar() {
        try {
            robot.send("parar/0");
        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void esperar(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int sensorLuz() {
        int r1 = -1;
        try {
            robot.send("sl");

            String r = robot.receive();
            if(r==null) return -1;
            else r1 = Integer.parseInt(r.substring(2, r.length() - 2));

        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r1;
    }

    public static int sensorDistancia(int i) {
        int r1 = -1;
        try {
            robot.send("sd1");

            String r = robot.receive();
            r1 = Integer.parseInt(r.substring(3, r.length() - 2));

        } catch (IOException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r1;
    }
}
