/*
 * Main.fx
 *
 * Created on Aug 11, 2010, 6:44:24 PM
 */
package desktopapplication1;

import br.com.globalcode.eletronlivre.arduino.serial.Arduino;

/**
 * @author vsenger
 */
public class Main {

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:main
    def __layoutInfo_scrollBar: javafx.scene.layout.LayoutInfo = javafx.scene.layout.LayoutInfo {
        width: 361.0
        height: 17.0
    }
    public-read def scrollBar: javafx.scene.control.ScrollBar = javafx.scene.control.ScrollBar {
        opacity: 1.0
        layoutX: 14.0
        layoutY: 46.0
        layoutInfo: __layoutInfo_scrollBar
        onMouseClicked: null
        onMousePressed: null
        onMouseReleased: teste
        min: 20.0
        max: 120.0
    }
    
    public-read def Luz_1: javafx.scene.control.Label = javafx.scene.control.Label {
        layoutX: 20.0
        layoutY: 18.0
        text: "Luz 1"
    }
    
    public-read def button: javafx.scene.control.Button = javafx.scene.control.Button {
        visible: false
        layoutX: 404.0
        layoutY: 279.0
        text: "Button"
    }
    
    def __layoutInfo_arduinoConsole: javafx.scene.layout.LayoutInfo = javafx.scene.layout.LayoutInfo {
    }
    public-read def arduinoConsole: javafx.scene.control.TextBox = javafx.scene.control.TextBox {
        visible: false
        layoutX: 17.0
        layoutY: 279.0
        layoutInfo: __layoutInfo_arduinoConsole
    }
    
    public-read def color: javafx.scene.paint.Color = javafx.scene.paint.Color {
        red: 0.9529412
        green: 0.8980392
        blue: 0.11372549
    }
    
    public-read def circle: javafx.scene.shape.Circle = javafx.scene.shape.Circle {
        opacity: 1.0
        layoutX: 406.0
        layoutY: 33.0
        fill: color
        radius: 25.0
    }
    
    public-read def scene: javafx.scene.Scene = javafx.scene.Scene {
        width: 480.0
        height: 320.0
        content: getDesignRootNodes ()
    }
    
    public-read def currentState: org.netbeans.javafx.design.DesignState = org.netbeans.javafx.design.DesignState {
        names: [ "teste", ]
        actual: 0
        timelines: [
            javafx.animation.Timeline {
                keyFrames: [
                    javafx.animation.KeyFrame {
                        time: 0ms
                        action: function() {
                            scrollBar.layoutX = 95.0;
                            scrollBar.layoutY = 46.0;
                            Luz_1.layoutX = 95.0;
                            Luz_1.layoutY = 24.0;
                            circle.layoutX = 33.0;
                            circle.layoutY = 46.0;
                            button.visible = true;
                            button.layoutX = 327.0;
                            button.layoutY = 279.0;
                            button.text = "Begin Communication";
                            button.action = buttonActionAtteste;
                            arduinoConsole.visible = true;
                            __layoutInfo_arduinoConsole.width = 274.0;
                        }
                    }
                ]
            }
        ]
    }
    
    public-read def connected: org.netbeans.javafx.design.DesignState = org.netbeans.javafx.design.DesignState {
        names: [ "State 1", ]
        actual: 0
        timelines: [
            javafx.animation.Timeline {
                keyFrames: [
                    javafx.animation.KeyFrame {
                        time: 0ms
                    }
                ]
            }
        ]
    }
    
    public function getDesignRootNodes (): javafx.scene.Node[] {
        [ scrollBar, Luz_1, circle, button, arduinoConsole, ]
    }
    
    public function getDesignScene (): javafx.scene.Scene {
        scene
    }
    // </editor-fold>//GEN-END:main

    function buttonActionAtteste(): Void {
        Arduino.begin(9600);

    //TODO
    }

    function teste(event: javafx.scene.input.MouseEvent): Void {
        java.lang.System.out.println(scrollBar.value);
        circle.opacity = -1 - scrollBar.value / 100;
        if(Arduino.map(scrollBar.value, 20, 121, 1, 9)==0) {
            Arduino.enviar(9);
        }
        else Arduino.enviar(Arduino.map(scrollBar.value, 20, 121, 1, 9));
    }

}

function run (): Void {
    var design = Main {};

    javafx.stage.Stage {
        title: "Main"
        scene: design.getDesignScene ()
    }
}
