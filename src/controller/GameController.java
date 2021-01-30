package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Cell;
import model.InputRow;
import model.LogicProgram;
import model.Table;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class GameController {
    private boolean goOn = true;
    private boolean timeIsOut = false;
    private Integer r;

    private Integer c;

    @FXML
    private Label points;

    @FXML
    private Label timer;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane newValues;
    private String encodingResource = "encodings/prog.txt";
    private LogicProgram logicProgram;
    private Table mainTable;

    @FXML
    public void initialize() {
        timer.setText(Integer.toString(60));
        mainTable = new Table();
        logicProgram = new LogicProgram(encodingResource);
        logicProgram.addFacts(mainTable);
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
                int time = Integer.parseInt(timer.getText());
                time--;
                timer.setText(Integer.toString(time));
                if (time <= 0)
                    timeIsOut = true;
                else timeIsOut = false;
                if (!timeIsOut) {
                    ArrayList<Cell> answerset = logicProgram.getAnswerSet(mainTable);
                    Integer pt = Integer.parseInt(points.getText());
                    for (Cell c : answerset) {
                        ImageView imgv = new ImageView("/images/greyempty.png");
                        imgv.setFitHeight(50);
                        imgv.setFitWidth(50);
                        grid.getChildren().remove(c.getRow() * 8 + c.getColumn(), c.getRow() * 8 + c.getColumn());
                        grid.add(imgv, c.getColumn(), c.getRow());
                        if (c.getType().equals("treasure"))
                            pt += 58; //33 + 25
                        else if (!c.getType().equals("time"))
                            pt += 33;
                    }
                    System.out.println(pt);
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
