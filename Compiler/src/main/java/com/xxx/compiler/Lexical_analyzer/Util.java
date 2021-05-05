package com.xxx.compiler.Lexical_analyzer;

import java.util.ArrayList;

public class Util {

    public static boolean IsLetter(char ch){
        if(ch>='a'&&ch<='z'||ch>='A'&&ch<='Z'){
            return true;
        }
        return false;
    }
    public static boolean IsDigit(char ch){
        if(ch>='0'&&ch<='9'){
            return true;
        }
        return false;
    }
    public static boolean getBC(char ch){
        if (ch==' '){
            return true;
        }
        return false;
    }
    public static boolean IsExist(ArrayList list, StringBuffer s){
        String ss=s.toString().toLowerCase();
        for(int i=0;i<list.size();i++){
            if(ss.equals(list.get(i))){
                return true;
            }
        }
        return false;
    }
    //获得标识符在列表中的下标
    public static int getExistWordSubscript(ArrayList list,StringBuffer s){
        for(int i=0;i<list.size();i++){
            if(s.equals(list.get(i))){
                return i;
            }
        }
        return -1;
    }
    //获得常数在列表中的下标
    public static int getExistConstSubscript(ArrayList list,String  n){
        for(int i=0;i<list.size();i++){
            if(n.equals(list.get(i))){
                return i;
            }
        }
        return -1;
    }
    public static boolean IsConstExist(ArrayList list,int n){
        String ns= String.valueOf(n);
        for(int i=0;i<list.size();i++){
            if(ns.equals(list.get(i))){
                return true;
            }
        }
        return false;
    }
}
