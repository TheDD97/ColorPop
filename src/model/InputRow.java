package model;

import it.unical.mat.embasp.languages.asp.SymbolicConstant;

import java.util.ArrayList;
import java.util.Random;

public class InputRow {
    private ArrayList<Cell> row;
    private boolean create = false;
    private String type = null;
    private String color = null;

    public InputRow() {
        row = new ArrayList<Cell>();
        for (int i = 0; i < 8; ) {
            generateType();
            generateColor();
            checkCreation();
            if (create = true) {
                Cell c = new Cell(0, i, new SymbolicConstant(this.color), new SymbolicConstant(this.type));
                row.add(c);
                i++;
                create = false;
            }
        }
    }


    private void checkCreation() {
        if (color.equals("grey") && type.equals("empty"))
            while (color.equals("grey") && type.equals("empty"))
                generateColor();
        else if (!color.equals("grey"))
            if (!type.equals("empty") && !type.equals("bomb"))
                while (!type.equals("empty") && !type.equals("bomb"))
                    generateType();
            else create = true;

    }

    private String generateColor() {
        Random rand = new Random();
        int randCol = rand.nextInt(5);
        if (randCol == 0)
            color = "blue";
        else if (randCol == 1)
            color = "green";
        else if (randCol == 2)
            color = "red";
        else
            color = "grey";
        return color;
    }

    private String generateType() {
        Random rand = new Random();
        int randType = rand.nextInt(30);
        if (randType == 1)
            type = "bomb";
        else if (randType == 3)
            type = "treasure";
        else if (randType == 5)
            type = "horizzontal";
        else if (randType == 7)
            type = "vertical";
        else if (randType == 9)
            type = "bidirectional";
        else if (randType == 11)
            type = "time";
        else
            type = "empty";
        return type;
    }

    public Cell getCell(int c) {
        return row.get(c);
    }
}
