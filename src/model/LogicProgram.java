package model;

import java.util.ArrayList;

import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class LogicProgram {
    private String encodingResource;
    private DesktopHandler handler;
    private InputProgram facts;
    private AnswerSets answer;

    public LogicProgram(String enc) {
        encodingResource = enc;
        handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));
        try {
            ASPMapper.getInstance().registerClass(Cell.class);
            ASPMapper.getInstance().registerClass(Adjacent.class);
            ASPMapper.getInstance().registerClass(Select.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OptionDescriptor option = new OptionDescriptor("--printonlyoptimum ");
        handler.addOption(option);
    }

    public void addFacts(Table t, Timer ti, boolean first) {
        if (!first)
            handler.removeProgram(facts);
        facts = new ASPInputProgram();
        facts.addFilesPath(encodingResource);
        try {
            facts.addObjectInput(ti);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        SymbolicConstant color;
        SymbolicConstant type;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 8; k++) {
                color = new SymbolicConstant(t.getCell(i, k).getColor().toString());
                type = new SymbolicConstant(t.getCell(i, k).getType().toString());
                try {
                    facts.addObjectInput(new Cell(i, k, color, type));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        handler.addProgram(facts);
    }

    public ArrayList<Cell> getAnswerSet(Table table) {
        Output out = handler.startSync();
        answer = (AnswerSets) out;
        ArrayList<Cell> adjacent = new ArrayList<Cell>();
         /*
        System.out.println(answer.getOutput());
        System.out.println(answer.getErrors());
        */
        int round = 0;
        Select select = null;
        for (AnswerSet a : answer.getOptimalAnswerSets()) {
            if (round < 1) {
                try {
                    for (Object obj : a.getAtoms()) {
                        if (obj instanceof Select) {
                            select = (Select) obj;
                            adjacent.add(new Cell(select.getRow(), select.getColumn(), table.getCell(select.getRow(), select.getColumn()).getColor(), table.getCell(select.getRow(), select.getColumn()).getType()));
                            //  System.out.println("\n" + adjacent.get(0).getColor() + "   " + adjacent.get(0).getType());
                            for (Object o : a.getAtoms())
                                if (o instanceof Adjacent) {
                                    Adjacent ad = (Adjacent) o;
                                    if (ad.getRow() == select.getRow() && ad.getColumn() == select.getColumn()) {
                                        SymbolicConstant color = table.getCell(ad.getRow1(), ad.getColumn1()).getColor();
                                        SymbolicConstant type = table.getCell(ad.getRow1(), ad.getColumn1()).getType();
                                        Cell adj = new Cell(ad.getRow1(), ad.getColumn1(), color, type);
                                        if (!adjacent.contains(adj))
                                            adjacent.add(adj);
                                    }
                                }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                round++;
            } else break;
        }/*
        for (int i = 1; i < adjacent.size(); i++)
            System.out.println(adjacent.get(i));*/
        return adjacent;
    }
}

