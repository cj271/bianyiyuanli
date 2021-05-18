package com.xxx.compiler.Lexical_analyzer;

import com.xxx.compiler.entity.Word;

import java.util.ArrayList;
import java.util.HashMap;

public class Analyse {
    private static final int BIAOSHIFU=35;
    private static final String ZBIAOSHIFU="$ID";
    private static final int CHANGSHU=36;
    private static final String ZCHANGSHU="$INT";
    private static char ch;
    private static StringBuffer strToken;
    private static int i=0;
    private static int j=0;
    private static ArrayList list=new ArrayList();//已识别的标识符
    private static ArrayList constList=new ArrayList();//已识别的常数
    private static ArrayList allList=new ArrayList();//已识别的所有单词
    private static int duohangzhushi=0;

    public static int getDuohangzhushi() {
        return duohangzhushi;
    }

    public static ArrayList go(String s,int line){
        j=0;
        strToken=new StringBuffer();
        do{
            strToken.delete(0,strToken.length());
            do{
                ch=getChar(s);
            }while(Util.getBC(ch));
            if(duohangzhushi==1){
                //识别多行注释结束符号
               if(ch=='*'){
                   ch=getChar(s);
                   if(ch==')'){
                       System.out.println("多行注释结束");
                       duohangzhushi=0;
                       continue;
                   }else {
                       j--;
                       continue;
                   }
               }else {
                   continue;
               }
            }
            //识别单行注释
            if(ch=='/'){
                contact(ch);
                if (j==s.length()){
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    break;
                }
                ch=getChar(s);
                if(ch=='/'){
                    System.out.println("识别单行注释成功！");
                    return allList;
                }else {
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    j--;
                    continue;
                }
            //识别多行注释开始符
            }else if(ch=='('){
                contact(ch);
                if (j==s.length()){
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    break;
                }
                ch=getChar(s);
                if(ch=='*'){
                    duohangzhushi=1;
                    System.out.println("多行注释开始");
                }else {
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    j--;
                    continue;
                }

            }
            //标识符识别
             else if(Util.IsLetter(ch)){
                contact(ch);
                if(j==s.length()){
                    int code=Keyword.reserveR(strToken);
                    if(code==0){
                        if(!Util.IsExist(list,strToken)){//不存在
                            list.add(strToken);
                            allList.add(new Word(strToken.toString(),BIAOSHIFU,ZBIAOSHIFU,list.size()-1,line));
                        }else {//已存在
                            allList.add(new Word(strToken.toString(),BIAOSHIFU,ZBIAOSHIFU,Util.getExistWordSubscript(list,strToken),line));
                        }
                    }else{
                        allList.add(new Word(strToken.toString(),code,Keyword.getMnemonic(code),line));
                    }
                    break;
                }
                ch=getChar(s);
                while(Util.IsLetter(ch)||Util.IsDigit(ch)){
                    contact(ch);
                    if(j==s.length()){j++;break;}
                    ch=getChar(s);
                }

                j--;
                int code=Keyword.reserveR(strToken);
                if(code==0){
                    if(!Util.IsExist(list,strToken)){//不存在
                        list.add(strToken);
                        allList.add(new Word(strToken.toString(),BIAOSHIFU,ZBIAOSHIFU,list.size()-1,line));
                    }else {//已存在
                        allList.add(new Word(strToken.toString(),BIAOSHIFU,ZBIAOSHIFU,Util.getExistWordSubscript(list,strToken),line));
                    }
                }else{
                    allList.add(new Word(strToken.toString(),code,Keyword.getMnemonic(code),line));
                }
            //常数识别
            }else if (Util.IsDigit(ch)){
                while(Util.IsDigit(ch)){
                    contact(ch);
                    if(j==s.length()){j++;break;};
                    ch=getChar(s);
                }

                j--;
                if(!Util.IsConstExist(constList,Integer.parseInt(strToken.toString()))){
                    constList.add(Integer.parseInt(strToken.toString()));
                    allList.add(new Word(strToken.toString(),CHANGSHU,ZCHANGSHU,constList.size()-1,line));
                }else {
                    allList.add(new Word(strToken.toString(),CHANGSHU,ZCHANGSHU,Util.getExistConstSubscript(constList,strToken.toString()),line));
                }
            //识别：=
            }else if (ch==':'){
                contact(ch);
                if (j==s.length()){
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    break;
                }
                ch=getChar(s);
                if(ch=='='){
                    contact(ch);
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                }else {
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    j--;
                    continue;
                }
            //识别<=和<>
            }else if(ch=='<'){
                contact(ch);
                if (j==s.length()){
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    break;
                }
                ch=getChar(s);
                if(ch=='>'||ch=='='){
                    contact(ch);
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                }else {
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    j--;
                    continue;
                }
            //识别>=
            }else if(ch=='>'){
                contact(ch);
                if (j==s.length()){
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    break;
                }
                ch=getChar(s);
                if(ch=='='){
                    contact(ch);
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                }else {
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                    j--;
                    continue;
                }
            //识别剩余的运算符和界符
            }else{
                contact(ch);
                int code=Keyword.findOP(strToken.toString());
                if(code==0){
                    System.out.println("error！不能识别："+ch);
                }else{
                    allList.add(new Word(strToken.toString(),Keyword.findOP(strToken.toString()),Keyword.getMnemonicOP(Keyword.findOP(strToken.toString())),line));
                }
            }


        }while(j<s.length());
        return allList;
    }
    public static char getChar(String s){
        j++;
        return  s.charAt(j-1);
    }
    public static void contact(char ch){
        strToken.append(ch);
    }
}
