package model;

import it.unical.mat.embasp.languages.asp.SymbolicConstant;

import java.util.ArrayList;
import java.util.Arrays;

public class Table {

    private ArrayList<ArrayList<Cell>> table;
    //private ArrayList<Boolean> emptyColumns;

    public Table() {
        //emptyColumns = new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false, false, false));
        table = new ArrayList<ArrayList<Cell>>();
        for (int r = 0; r < 7; r++) {
            ArrayList<Cell> tmp = new ArrayList<Cell>();
            for (int c = 0; c < 8; c++) {
                Cell ce = new Cell(r, c, new SymbolicConstant("grey"), new SymbolicConstant("empty"));
                tmp.add(ce);
            }
            table.add(tmp);
        }
        SymbolicConstant color;
        for (int r = 7; r < 10; r++) {
            ArrayList<Cell> tmp = new ArrayList<Cell>();
            for (int c = 0; c < 8; c++) {
                int rand = (int) (Math.random() * 3);
                if (rand == 0)
                    color = new SymbolicConstant("blue");
                else if (rand == 1)
                    color = new SymbolicConstant("green");
                else
                    color = new SymbolicConstant("red");
                tmp.add(new Cell(r, c, color, new SymbolicConstant("empty")));
            }
            table.add(tmp);
        }
    }

    public void print() {
        for (int i = 0; i < table.size(); i++) {
            for (int e = 0; e < table.get(i).size(); e++) {
                System.out.print(table.get(i).get(e).getColor().toString() + " ");
            }
            System.out.println();
        }
    }

    public Cell getCell(int r, int c) {
        return table.get(r).get(c);
    }

    public void fixTable() {
      /*  boolean realignColumn = false;
        int startAlign;
        for (Cell c : table.get(table.size() - 1)) {
            if (c.isGreyEmpty()) {
                emptyColumns.set(c.getColumn(), true);
                realignColumn = true;
            }
        }
      */
        for (int i = table.size() - 2; i >= 0; i--) {
            for (int j = 0; j < table.get(i).size(); j++) {
                int lastGreyEmptyOnColumn = -1;
                for (int k = table.size() - 1; k > i; k--) {
                    if (table.get(k).get(j).isGreyEmpty()) {
                        lastGreyEmptyOnColumn = k;
                        break;
                    }
                }
                if (lastGreyEmptyOnColumn > -1) {
                    table.get(lastGreyEmptyOnColumn).set(j, new Cell(lastGreyEmptyOnColumn, j, table.get(i).get(j).getColor(), table.get(i).get(j).getType()));
                    clearCell(i, j);
                }

            }
        }
        /*
        boolean shiftDx = true;
        if (realignColumn) {
            for (int i = 0; i <= emptyColumns.size() / 2+1; ++i) {
                if (emptyColumns.get(i)) {
                    shiftDx = false;
                    break;
                }
            }
            int direction;
            if (shiftDx) {
                direction = 1;
                startAlign = 0;
            } else {
                direction = -1;
                startAlign = 7;
            }
            System.out.println("direction " + direction + " start: " + startAlign);
            realignColumn(startAlign, direction);
        }*/


        /*

           INSERISCI IL CODICE PER IL RIALLINEAMENTO DELLA MATRICE QUI


         */



    }

  /*  public void realignColumn(int columnIndex, int direction) {
        if ((columnIndex < 7 && direction == 1) || (columnIndex > 0 && direction == -1)) {
            System.out.println(columnIndex);
            if (emptyColumns.get(columnIndex + direction)) {
                for (int i = 0; i < table.size(); ++i) {
                    table.get(i).set(columnIndex + direction, table.get(i).get(columnIndex));
                    clearCell(i, columnIndex);
                }
                emptyColumns.set(columnIndex + direction, false);
                emptyColumns.set(columnIndex, true);
            }
            realignColumn(columnIndex + direction, direction);
        }

    }*/

    public void clearCell(int r, int c) {
        table.get(r).set(c, new Cell(r, c, new SymbolicConstant("grey"), new SymbolicConstant("empty")));
    }

}
