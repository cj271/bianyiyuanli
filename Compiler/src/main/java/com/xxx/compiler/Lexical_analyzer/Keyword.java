package com.xxx.compiler.Lexical_analyzer;

public class Keyword {
    private static final String reserved_word[]=new String[]{"const", "var", "procedure", "odd", "if", "then", "else", "while", "do", "call", "begin", "end", "repeat", "until", "read", "write"};
    private static final String option_boundary[]=new String[]{".",",",";","=",":=","+","-","(",")","*","/","<>","<","<=",">",">=","#","!"};
    public static int reserveR(StringBuffer s){
        String ss=s.toString().toLowerCase();
        for(int i=0;i<reserved_word.length;i++){
            if(ss.equals(reserved_word[i])){
                return i+1;
            }
        }
        return 0;
    }
    public static String getMnemonic(int i){
        return "$"+reserved_word[i-1];
    }
    public static int findOP(String s){
        for(int i=0;i<option_boundary.length;i++){
            if(s.equals(option_boundary[i])){
                return reserved_word.length+i+1;
            }
        }
        return 0;
    }
    public static String getMnemonicOP(int i){
        return "$"+option_boundary[i-reserved_word.length-1];
    }

}
