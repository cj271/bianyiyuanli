package com.xxx.compiler.entity;

public class Table {
    private String name;
    private int kind;
    private int valOrlev;
    private  int adr;
    private  String procName;

    public Table(String name, int kind, int valOrlev, int adr,String procName) {
        this.name = name;
        this.kind = kind;
        this.valOrlev = valOrlev;
        this.adr = adr;
        this.procName=procName;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procNum) {
        this.procName= procName;
    }

    public String getName() {
        return name;
    }

    public int getKind() {
        return kind;
    }

    public int getValOrlev() {
        return valOrlev;
    }

    public int getAdr() {
        return adr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setValOrlev(int valOrlev) {
        this.valOrlev = valOrlev;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }
}
