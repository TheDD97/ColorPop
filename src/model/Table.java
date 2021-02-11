package model;

import it.unical.mat.embasp.languages.asp.SymbolicConstant;

import java.util.ArrayList;
import java.util.Arrays;

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
                System.out.print(table.get(i).get(e).getColor().toString() + " ");
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
        int halfColumn=table.get(0).size()/2;
        ArrayList<Integer> cellGreyEmpty=new ArrayList<Integer>();
        for(int i=0; i<halfColumn; i++) {
            if(table.get(table.size()-1).get(i).isGreyEmpty()) {
                cellGreyEmpty.add(i);
            }
        }
        for(int i=halfColumn-1; i>0;i--) {
            if(cellGreyEmpty.contains(i) && !cellGreyEmpty.contains(i-1)) {
                for(int j=0; j<table.size(); j++) {
                    table.get(j).set(i, new Cell(j,i,table.get(j).get(i-1).getColor(), table.get(j).get(i-1).getType()));
                    clearCell(j,i-1);
                }
                cellGreyEmpty.remove(cellGreyEmpty.size()-1);
                cellGreyEmpty.add(i-1);
            }
        }
    }


    public void shiftDx() {
        int halfColumn=table.get(0).size()/2;
        ArrayList<Integer> cellGreyEmpty=new ArrayList<Integer>();
        for(int i=halfColumn; i<table.get(0).size(); i++) {
            if(table.get(table.size()-1).get(i).isGreyEmpty()) {
                cellGreyEmpty.add(i);
            }
        }
        for(int i=halfColumn; i<=table.get(0).size()-2;i++) {
            if(cellGreyEmpty.contains(i) && !cellGreyEmpty.contains(i+1)) {
                for(int j=0; j<table.size(); j++) {
                    table.get(j).set(i, new Cell(j,i,table.get(j).get(i+1).getColor(), table.get(j).get(i+1).getType()));
                    clearCell(j,i+1);
                }
                cellGreyEmpty.remove(cellGreyEmpty.size()-1);
                cellGreyEmpty.add(i+1);
            }
        }
    }

    public void clearCell(int r, int c) {
        table.get(r).set(c, new Cell(r, c, new SymbolicConstant("grey"), new SymbolicConstant("empty")));
    }

    public boolean insertInputRow(InputRow ir) {
        for(int i=0; i<table.get(0).size(); i++)
            if(!table.get(0).get(i).isGreyEmpty())
                return false;
        table.remove(0);
        for(int i=0; i<table.size()-1;i++)
            for(int e=0; e<table.get(i).size(); e++) {
                table.get(i).get(e).setRow(table.get(i).get(e).getRow()-1);
            }
        table.add(new ArrayList<Cell>());
        for(int i=0; i<table.get(0).size(); i++)
            table.get(table.size()-1).add(ir.getCell(i));
        return true;
    }
}