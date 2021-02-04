package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.ArrayList;


public class GameController {
    private boolean goOn = true;
    private boolean timeIsOut = false;
    private Integer r;

    private Integer c;

    @FXML
    private Label points;

    @FXML
    private Label graphicTimer;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane newValues;
    private String encodingResource = "encodings/prog.txt";
    private LogicProgram logicProgram;
    private Table mainTable;
    private Timer timer;

    @FXML
    public void initialize() {
        timer = new Timer();
        timer.setTime(60);
        graphicTimer.setText(Integer.toString(timer.getTime()));
        mainTable = new Table();
        logicProgram = new LogicProgram(encodingResource);
        logicProgram.addFacts(mainTable, timer);
        InputRow newRow = new InputRow();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 8; ++j) {
                ImageView imgv = new ImageView("/images/" + mainTable.getCell(i, j).getColor() + mainTable.getCell(i, j).getType() + ".png");
                imgv.setFitHeight(50);
                imgv.setFitWidth(50);
                grid.add(imgv, j, i);
            }
        }
        for (int j = 0; j < 8; ++j) {
            ImageView imgv = new ImageView("/images/" + newRow.getCell(j).getColor() + newRow.getCell(j).getType() + ".png");
            imgv.setFitHeight(50);
            imgv.setFitWidth(50);
            newValues.add(imgv, j, 0);
        }
        Thread t = new Thread(this::routine);
        t.setDaemon(true);
        t.start();
    }

    private synchronized void routine() {
        while (!timeIsOut) {
            Platform.runLater(() -> {
                int time = timer.getTime();
                time--;
                timer.setTime(time);
                graphicTimer.setText(Integer.toString(timer.getTime()));
                /*
                if (time<=0){
                    timeIsOut=true;
                }*/

                if (!timeIsOut && time < 50) {//line 77 is just for try
                    timeIsOut = true;
                    //  logicProgram.addFacts(mainTable,timer);
                    //mainTable.print();
                    ArrayList<Cell> answerset = logicProgram.getAnswerSet(mainTable);
                    Integer pt = Integer.parseInt(points.getText());
                    for (Cell c : answerset) {
                        if (c.getType().equals("treasure"))
                            pt += 58; //33 + 25
                        else if (!c.getType().equals("time"))
                            pt += 33;
                        else timer.setTime(time += 4);
                        mainTable.clearCell(c.getRow(), c.getColumn());
                    }
                    mainTable.fixTable();
                    //mainTable.print();
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 8; j++) {
                            ImageView imgv = new ImageView("/images/" + mainTable.getCell(i, j).getColor() + mainTable.getCell(i, j).getType() + ".png");
                            imgv.setFitHeight(50);
                            imgv.setFitWidth(50);
                            grid.getChildren().remove(i * 8 + j, i * 8 + j);
                            grid.add(imgv, j, i);
                        }
                    }
                    points.setText(Integer.toString(pt));
                }

            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
