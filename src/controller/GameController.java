package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.ArrayList;


public class GameController {
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
    private Alert pauseAlert;
    private Alert startAlert;
    private boolean start = false;
    private boolean timeIsOut = false;
    private Integer r;
    private Integer c;
    private Thread t;
    private boolean pause = false;
    private InputRow newRow;
    private int countdown = 1;
    private int newAnswerSetCountdown = 0;
    private boolean first = true;
    private Flyweight imageCreator = new Flyweight();

    @FXML
    public void initialize() {
        startAlert = new Alert(Alert.AlertType.INFORMATION);
        startAlert.setTitle("ColorPop");
        startAlert.setHeaderText("Benvenuto!");
        startAlert.setContentText("Premi il pulsante per iniziare");
        pauseAlert = new Alert(Alert.AlertType.INFORMATION);
        pauseAlert.setHeaderText("Gioco in pausa");
        pauseAlert.setTitle("ColorPop");
        pauseAlert.setContentText("Premi il pulsante per riprendere il gioco");
        //startAlert.showAndWait();
        timer = new Timer();
        timer.setTime(60);
        graphicTimer.setText(Integer.toString(timer.getTime()));
        mainTable = new Table();
        logicProgram = new LogicProgram(encodingResource);
        //logicProgram.addFacts(mainTable, timer);
        newRow = new InputRow();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 8; ++j) {
                ImageView imgv = new ImageView(imageCreator.getImage(mainTable.getCell(i, j).getColor().toString() + mainTable.getCell(i, j).getType().toString()));
                imgv.setFitHeight(50);
                imgv.setFitWidth(50);
                grid.add(imgv, j, i);
            }
        }
        for (int j = 0; j < 8; ++j) {
            ImageView imgv = new ImageView(imageCreator.getImage(newRow.getCell(j).getColor().toString() + newRow.getCell(j).getType().toString()));
            imgv.setFitHeight(50);
            imgv.setFitWidth(50);
            newValues.add(imgv, j, 0);
        }
        t = new Thread(this::routine);
        t.setDaemon(true);
        t.start();
    }

    private synchronized void routine() {
        while (!timeIsOut) {
            Platform.runLater(() -> {
                if (!pause) {
                    int time = timer.getTime();
                    time--;
                    if (time == 0) {
                        timeIsOut = true;
                    }
                    timer.setTime(time);
                    graphicTimer.setText(Integer.toString(timer.getTime()));
                    if (!timeIsOut && time < 50) {//line 77 is just for try
                        //timeIsOut = true;
                        if (countdown % 2 == 0) {
                            logicProgram.addFacts(mainTable, timer);
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
                            points.setText(Integer.toString(pt));
                        }
                        mainTable.fixTable();
                        if (countdown % 5 == 0) {
                            if (!mainTable.insertInputRow(newRow)) {
                                timeIsOut = true;
                                pauseAlert.setAlertType(Alert.AlertType.ERROR);
                                pauseAlert.setTitle("ColorPop");
                                pauseAlert.setHeaderText("Game-over!");
                                pauseAlert.setTitle("Try again :(");
                                pauseAlert.setContentText(null);
                                pauseAlert.showAndWait();
                            } else {
                                newRow = new InputRow();
                                for (int i = 0; i < 8; i++) {
                                    ImageView imgv = new ImageView(imageCreator.getImage(newRow.getCell(i).getColor().toString() + newRow.getCell(i).getType().toString()));
                                    imgv.setFitHeight(50);
                                    imgv.setFitWidth(50);
                                    newValues.getChildren().remove(i, i);
                                    newValues.add(imgv, i, 0);
                                }
                            }
                        }

                        //mainTable.print();
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 8; j++) {
                                ImageView imgv = new ImageView(imageCreator.getImage(mainTable.getCell(i, j).getColor().toString() + mainTable.getCell(i, j).getType().toString()));
                                imgv.setFitHeight(50);
                                imgv.setFitWidth(50);
                                grid.getChildren().remove(i * 8 + j, i * 8 + j);
                                grid.add(imgv, j, i);
                            }
                        }
                    }
                }
            });
            try {
                Thread.sleep(1000);
                countdown++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void pause(ActionEvent event) {
        if (!timeIsOut) {
            pause = true;
            pauseAlert.showAndWait();
            pause = false;
        }
    }
}
