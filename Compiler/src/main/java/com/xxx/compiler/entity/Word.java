package com.xxx.compiler.entity;

public class Word {
    private static final int bz=35;//标识符种别号
    private static final int cz=36;//常数种别号
    private String symbol;
    private int type;
    private String mnemonic;
    private int  inter_code;
    private int line;
//当词为保留字,界符，运算符时构造方法
    public Word(String symbol, int type, String mnemonic,int line) {
        this.symbol = symbol;
        this.type = type;
        this.mnemonic = mnemonic;
        this.line=line;
    }
//当词为标识符或数字时的构造方法
    public Word(String symbol, int type, String mnemonic, int inter_code,int line) {
        this.symbol = symbol;
        this.type = type;
        this.mnemonic = mnemonic;
        this.inter_code = inter_code;
        this.line=line;
    }

    public int getLine() {
        return line;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getType() {
        return type;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public int getInter_code() {
        return inter_code;
    }

    public void printWord(){
        if(type!=bz&&type!=cz){
            System.out.println("<"+symbol+","+type+","+mnemonic+">"+"line:"+line);
        }else if(type==bz){
            System.out.println("<"+symbol+","+type+","+mnemonic+",符号表指针："+inter_code+">"+"line:"+line);
        }else {
            System.out.println("<"+symbol+","+type+","+mnemonic+",常数表指针："+inter_code+">"+"line:"+line);
        }
    }
}
