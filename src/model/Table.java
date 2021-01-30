package model;

import it.unical.mat.embasp.languages.asp.SymbolicConstant;

import java.util.ArrayList;

public class Table {

    private ArrayList<ArrayList<Cell>> table;

    public Table() {
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
                System.out.print(table.get(i).get(e));
            }
            System.out.println();
        }
    }

    public Cell getCell(int r, int c) {
        return table.get(r).get(c);
    }

}
