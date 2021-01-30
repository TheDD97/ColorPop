package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("adjacent")
public class Adjacent {
    @Param(0)
    private int row;
    @Param(1)
    private int column;
    @Param(2)
    private int row1;
    @Param(3)
    private int column1;

    public Adjacent() {
    }

    public Adjacent(int r, int c, int r1, int c1) {
        this.row = r;
        this.column = c;
        this.row1 = r1;
        this.column1 = c1;
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

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getColumn1() {
        return column1;
    }

    public void setColumn1(int column1) {
        this.column1 = column1;
    }

}