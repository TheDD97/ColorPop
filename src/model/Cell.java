package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("cell")
public class Cell {
    @Param(0)
    private int row;
    @Param(1)
    private int column;
    @Param(2)
    private SymbolicConstant color;
    @Param(3)
    private SymbolicConstant type;

    public Cell(int r, int c, SymbolicConstant co, SymbolicConstant t) {
        this.row = r;
        this.column = c;
        this.color = co;
        this.type = t;
    }

    public Cell() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public SymbolicConstant getColor() {
        return color;
    }

    public void setColor(SymbolicConstant color) {
        this.color = color;
    }

    public SymbolicConstant getType() {
        return type;
    }

    public void setType(SymbolicConstant type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return color.toString() + " ";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell)) return false;
        Cell c = (Cell) obj;
        return this.row == c.row && this.column == c.column && this.color.toString().equals(c.color.toString()) && this.type.toString().equals(c.type.toString());
    }
}