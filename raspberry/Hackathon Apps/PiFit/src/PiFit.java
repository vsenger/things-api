/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.javafx.collections.ObservableListWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Rogério
 */
public class PiFit extends Application {

    GridPane mainPane = new GridPane();
    Scene scene;
    XYChart.Series series;
    Label lblFrequenciaCardiacaValor = null;
    long cont = 0;
    private static final long FIXED_DELAY = 1000;

    @Override
    public void start(Stage primaryStage) {

        //Platform.runLater(null);

        String strIP = System.getProperty("d");

        this.setPageLayout();
        this.setImages();
        this.createChart();
        this.createLabels();
        
        //primaryStage.setFullScreen(true);
        primaryStage.setTitle("PiFit");

        scene.getStylesheets().add("layout.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timer timer = new Timer(true);
        timer.schedule(new Looping(), 0, 1000);
    }

    private void setPageLayout() {

        mainPane.setAlignment(Pos.CENTER);

        mainPane.setHgap(20);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(10);

//        RowConstraints row1 = new RowConstraints();
//        row1.set
//
//        RowConstraints row2 = new RowConstraints();
//        row2.setPrefHeight(700);

//        RowConstraints row3 = new RowConstraints();
//        row3.setPercentHeight(30);


        mainPane.getColumnConstraints().addAll(column1, column2, column3); // each get 50% of width
        //mainPane.getRowConstraints().addAll(row1,row2);


//
//        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//
//
//
//        scene = new Scene(mainPane, screenBounds.getWidth(), screenBounds.getHeight() - 50);
        scene = new Scene(mainPane, 1024, 768);

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void setImages() {
        Image logo = new Image("raspberry_heart.gif");

        ImageView iv1 = new ImageView();
        iv1.setImage(logo);
        iv1.setPreserveRatio(true);
        iv1.setFitHeight(114);

        iv1.setId("heart");
        GridPane.setRowIndex(iv1, 0);
        GridPane.setColumnIndex(iv1, 2);

        mainPane.getChildren().add(iv1);
    }

      NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
    private void createChart() {
       
        xAxis.setLabel("Segundos");
        yAxis.setLabel("Batimentos");
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("PiFit");
        GridPane.setRowIndex(lineChart, 3);
        GridPane.setColumnIndex(lineChart, 1);

        //defining a series
        series = new XYChart.Series();
        series.setName("Frequência Cardíaca");

        lineChart.getData().add(series);

        mainPane.getChildren().add(lineChart);
    }

    private void createLabels() {
        Font fntLabel = Font.font("Terminus", FontWeight.NORMAL, 40);

        Font fntLabelValor = Font.font("Terminus", FontWeight.BOLD, 50);

        Label lblFrequenciaCardiaca = new Label("Freq. Cardíaca");
        lblFrequenciaCardiaca.setFont(fntLabel);
        GridPane.setRowIndex(lblFrequenciaCardiaca, 5);
        GridPane.setColumnIndex(lblFrequenciaCardiaca, 1);

        lblFrequenciaCardiacaValor = new Label("--");
        lblFrequenciaCardiacaValor.setFont(fntLabelValor);
        GridPane.setRowIndex(lblFrequenciaCardiacaValor, 6);
        GridPane.setColumnIndex(lblFrequenciaCardiacaValor, 1);
        /*
         Label lblTemperatura = new Label("Temp. Corporal");
         GridPane.setRowIndex(lblTemperatura, 8);
         GridPane.setColumnIndex(lblTemperatura, 1);

         Label lblTemperaturaValor = new Label("37");
        
         GridPane.setRowIndex(lblTemperaturaValor, 9);
         GridPane.setColumnIndex(lblTemperaturaValor, 1);
         * mainPane.getChildren().addAll(lblFrequenciaCardiaca, lblFrequenciaCardiacaValor, lblTemperatura, lblTemperaturaValor);
         */
        mainPane.getChildren().addAll(lblFrequenciaCardiaca, lblFrequenciaCardiacaValor);
    }

    List<XYChart.Data> temp = new ArrayList<>();
    private void addChartEntry(Integer x, Integer y) {

        /*if (series.getData().size() > 20) {
            series.getData().remove(0);
        }
        * */
        
        //System.out.println("Tamanho do temp: "+temp.size());
        this.lblFrequenciaCardiacaValor.setText(y.toString());
        //adiciona o valor no grafico
        /*if(cont > 5){
            series.getData().remove(0);
            xAxis.setLowerBound(cont-5);
            series.getData().add(new XYChart.Data(cont, y));*/
           /* System.out.println("maior que 5");
           for(int i=1;i<=5;i++){
                System.out.println(i);
                temp.set(i-1, temp.get(i));
                series.getData().set(i-1, temp.get(i));
            }
           temp.set(5, new XYChart.Data(cont,y));
           series.getData().set(5, new XYChart.Data(cont,y));*/
            
            
           //series.setData(new ObservableListWrapper(temp));
            //series.getData().add(new XYChart.Data(cont-1, y));
        //}else{
           // temp.add(new XYChart.Data(cont, y));
       // if(cont > 5){
            series.getData().add(new XYChart.Data(cont, y));    
            //series.getData().remove(0);
            //System.out.println("lowerbound: "+xAxis.getLowerBound());
            //xAxis.setLowerBound(xAxis.getLowerBound()+1);
     //   }else{
         //   series.getData().add(new XYChart.Data(cont, y)); 
       // }
        //}
        

        
        
        cont++;
    }

    public void looping() throws Exception {
    }

    public class Looping extends TimerTask {
        //String porta = "COM5";
        String porta = "/dev/ttyUSB0";

        private boolean iniciou = false;
        private int cont = 0;

        public void run() {
            try {
                if (!iniciou) {
                    System.out.println("porta " + porta);
                    System.setProperty("gnu.io.rxtx.SerialPorts", porta);
                    FrequenciaCardiacaManager.iniciar(porta);
                    iniciou = true;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            FrequenciaCardiaca fc = null;
                            fc = FrequenciaCardiacaManager.ler(); //read
                            
                            if(fc != null){
                                //cont++;
                                //addChartEntry(series.getData().size(), fc.getBatimentos());
                                addChartEntry(cont, fc.getBatimentos());
                            }else{
                                addChartEntry(cont, 0);
                            }
                            //FrequenciaCardiacaManager.encerrar();
                        } catch (IOException ex) {
                            Logger.getLogger(PiFit.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(PiFit.class.getName()).log(Level.SEVERE, null, ex);
                        }



                    }
                });


            } catch (Exception ex) {
                Logger.getLogger(PiFit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
