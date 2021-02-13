package model;

import it.unical.mat.embasp.languages.asp.SymbolicConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
                System.out.print("[ " + table.get(i).get(e).getRow() + table.get(i).get(e).getColumn() + "] ");
            }
            System.out.println();
        }
    }

    public Cell getCell(int r, int c) {
        return table.get(r).get(c);
    }

    public void fixTable() {
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
        shiftSx();
        shiftDx();
    }

    public void shiftSx() {
        int halfColumn = table.get(0).size() / 2;
        boolean swap = false;

        for (int i = halfColumn; i >= 0; i--) {
            int count = 0;
            for (int k = i - 1; k >= 0; k--)
                if (table.get(table.size() - 1).get(k).isGreyEmpty()) {
                    swap = true;
                    count++;
                } else break;
            if (swap && count > 0 && (i - count - 1 >= 0)) {
                for (int row = 0; row < table.size(); row++) {
                    table.get(row).set(i - 1, new Cell(row, i - 1, table.get(row).get(i - count - 1).getColor(), table.get(row).get(i - count - 1).getType()));
                    clearCell(row, i - count - 1);
                }
            }
            swap = false;
        }
    }

    public void shiftDx() {
        int halfColumn = table.get(0).size() / 2;
        boolean swap = false;
        for (int i = halfColumn - 1; i < table.get(0).size(); i++) {
            int count = 0;
            for (int k = i + 1; k < table.get(0).size(); k++)
                if (table.get(table.size() - 1).get(k).isGreyEmpty()) {
                    swap = true;
                    count++;
                } else break;
            if (swap && count > 0 && (i + count + 1 <= table.get(0).size() - 1)) {
                for (int row = 0; row < table.size(); row++) {
                    table.get(row).set(i + 1, new Cell(row, i + 1, table.get(row).get(i + count + 1).getColor(), table.get(row).get(i + count + 1).getType()));
                    clearCell(row, i + count + 1);
                }
            }
            swap = false;
        }
    }

    public void clearCell(int r, int c) {
        table.get(r).set(c, new Cell(r, c, new SymbolicConstant("grey"), new SymbolicConstant("empty")));
    }

    public boolean insertInputRow(InputRow ir) {
        for (int i = 0; i < table.get(0).size(); i++)
            if (!table.get(0).get(i).isGreyEmpty())
                return false;
        table.remove(0);
        for (int i = 0; i < table.size(); i++)
            for (int e = 0; e < table.get(i).size(); e++) {
                int r = table.get(i).get(e).getRow();
                table.get(i).get(e).setRow(r - 1);
            }
        table.add(new ArrayList<Cell>());
        for (int i = 0; i < table.get(0).size(); i++) {
            ir.getCell(i).setRow(table.size() - 1);
            table.get(table.size() - 1).add(ir.getCell(i));
        }
        return true;
    }
}