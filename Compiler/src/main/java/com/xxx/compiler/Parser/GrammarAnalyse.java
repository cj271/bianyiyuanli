package com.xxx.compiler.Parser;

import com.xxx.compiler.entity.Table;
import com.xxx.compiler.entity.Word;

import java.util.ArrayList;

public class GrammarAnalyse {
    private ArrayList list;
    private ArrayList tables=new ArrayList();
    private ArrayList<String> procNameList=new ArrayList();
    private int j;
    private int i=0;
    private Word word;
    public GrammarAnalyse(ArrayList list) {
        this.list = list;
        this.j=0;

    }

    /**
     *
     * @param
     */
    public void go(){
        if (Subprogram()==0){
            return;
        }else {
            word=getSym();
            if(word.getType()==17){
                System.out.println("分析结束,无错误。");
                return;
            }else {
                System.out.println(word.getSymbol()+"附近有错误");
            }
        }

    }

    /**
     *
     * @param
     * @param
     */
    public int block(){
        if(word.getType()==1){
            word=getSym();
            String name;
            if(word.getType()==35){
                name=word.getSymbol();
                if(SearchTables.searchExits(tables,i,procNameList.get(procNameList.size()-1),name)==0){
                    System.out.println("标识符"+name+"已存在");
                    err(word);
                    return 0;
                }
                word=getSym();
                if (word.getType()==20){
                    word=getSym();
                    if (word.getType()==36){
                        int val= Integer.parseInt(word.getSymbol());
                        tables.add(new Table(name,1,val,0,procNameList.get(procNameList.size()-1)));
                        word=getSym();
                        while (word.getType()==18){//word==","
                            word=getSym();
                            if(word.getType()==35) {//word==const
                                name=word.getSymbol();
                                if(SearchTables.searchExits(tables,i,procNameList.get(procNameList.size()-1),name)==0){
                                    System.out.println("标识符"+name+"已存在");
                                    err(word);
                                    return 0;
                                }
                                word=getSym();
                                if (word.getType()==20){
                                    word=getSym();
                                    if (word.getType()==36){
                                         val= Integer.parseInt(word.getSymbol());
                                        tables.add(new Table(name,1,val,0,procNameList.get(procNameList.size()-1)));
                                        word=getSym();
                                    }else {err(word);return 0;}
                                }else {err(word);return 0;}
                            }else {err(word);return 0;}
                        }
                    }else {
                        err(word);
                        return 0;
                    }
                }else {
                    err(word);
                    return 0;
                }
            }else {
                err(word);
                return 0;
            }
        }else  if (word.getType()==2){
            word=getSym();
            if (word.getType()==35) {//word==标识符
                String name=word.getSymbol();
                if(SearchTables.searchExits(tables,i,procNameList.get(procNameList.size()-1),name)==0){
                    System.out.println("标识符"+name+"已存在");
                    err(word);
                    return 0;
                }
                int lev=SearchTables.getDX(tables,i,procNameList.get(procNameList.size()-1));
                tables.add(new Table(name,2,i,lev,procNameList.get(procNameList.size()-1)));
                word = getSym();
                while (word.getType() == 18) {//word==","
                    word = getSym();
                    if (word.getType() == 35) {//word==标识符
                        name=word.getSymbol();
                        if(SearchTables.searchExits(tables,i,procNameList.get(procNameList.size()-1),name)==0){
                            System.out.println("标识符"+name+"已存在");
                            err(word);
                            return 0;
                        }
                        lev=SearchTables.getDX(tables,i,procNameList.get(procNameList.size()-1));
                        tables.add(new Table(name,2,i,lev,procNameList.get(procNameList.size()-1)));
                        word = getSym();
                    } else {
                        err(word);
                        return 0;
                    }
                }
                if (word.getType()==19)return 1;
            } else {
                err(word);
                return 0;}
        }else if (word.getType()==3){
            while (word.getType()==3){
                word=getSym();
                if(word.getType()==35){
                    procNameList.add(word.getSymbol());

                    word=getSym();
                    if (word.getType()==19){
                        if (i<3){
                            i++;
                        }else {
                            System.out.println("超出嵌套层数！");
                            err(word);
                            return 0;
                        }

                        if (Subprogram()==0){return 0;
                        }else {
                            procNameList.remove(procNameList.size()-1);
                            i--;
                        }
                        word=getSym();
                        if (word.getType()==19){
                            word=getSym();
                        }else {err(word);return 0;}
                    }else{err(word);return 0;}
                }else {err(word);return 0;}

            }
//            j--;
        } else {
            return 1;
        }
        return 1;
    }

    /**
     * 获得指针所指的单词对象
     * @return
     */
    public Word getSym(){
        if (j<list.size()){
            Word word=(Word) list.get(j);
            j++;
            return word;
        }else {
            System.out.println("读取溢出！！！");
        }
        return null;
    }

    /**
     * 出错提示
     * @param word
     */
    public void err(Word word){
        System.out.println("出错位置:第"+word.getLine()+"行"+"===>"+word.getSymbol()+"附近");
    }
    /**
     * 分程序
     */
    public int  Subprogram(){
        if (i==0){
            procNameList.add("main");
        }
        word=getSym();
        if(word.getType()==1){
                       if (block()==0)return 0;

                       if (word.getType()==19){//word==";"
                           word=getSym();
                           if (word.getType()==2){//word==var
                                   if (block()==0)return 0;

                                   if (word.getType()==19){//word==";"
                                       word=getSym();
                                       if (block()==0)return 0;
                                       j--;
                                       if(statement()==0){
                                           return 0;
                                       }else return 1;


                                   }else {err(word);return 0;}

                           }else if(word.getType()==3){
                               if (block()==0)return 0;
                               j--;
                               if(statement()==0){
                                   return 0;
                               }else return 1;
                           }else {
                               j--;
                               if(statement()==0){
                                   return 0;
                               }else {return 1;}
                           }
                       }else {err(word);return 0;}


        }else if (word.getType()==2){//word==var
                if (block()==0)return 0;
                if (word.getType()==19){//word==";"
                    word=getSym();
                    if (block()==0)return 0;
                    j--;
                    if(statement()==0){
                        return 0;
                    }else return 1;
                }else {err(word);return 0;}

        } else if (word.getType() == 3) {//word==procedure
            if (block()==0)return 0;
            if(statement()==0){
                return 0;
            }else return 1;

        }else{//进入statement程序
            j--;
            if(statement()==0){
                return 0;
            }else return 1;
        }

    }
    public int statement(){
        word=getSym();
        if (word.getType()==35){//=标识符
            if (SearchTables.judgeIdentifierExits(tables,word.getSymbol(),procNameList)==0){
                System.out.println(word.getSymbol()+"未定义");
                    err(word);
                    return 0;
            }else if (SearchTables.judgeIdentifierExits(tables,word.getSymbol(),procNameList)==1){
                System.out.println(word.getSymbol()+"是常量，不能进行赋值操作");
            }
            word=getSym();
            if (word.getType()==21){
                if (expression()==0)return 0;
            }else {err(word);return 0;}

        }else if (word.getType()==10){//=call
            word=getSym();
            if (word.getType()==35){

            }else  {err(word);return 0;}

        }else if (word.getType()==11){//=begin
            if(statement()==0)return 0;
            word=getSym();
            while (word.getType()==19){
                if(statement()==0)return 0;
                word=getSym();
            }
            if (word.getType()==12){

            }else {err(word);return 0;}

        }else if (word.getType()==5){//=if
            if (condition()==0)return 0;
            word=getSym();
            if (word.getType()==6){
                word=getSym();
                if(statement()==0)return 0;
            }else  {err(word);return 0;}

        }else if (word.getType()==8){//=while
            if (condition()==0)return 0;
            word=getSym();
            if (word.getType()==9){
                if(statement()==0)return 0;
            }else{err(word);return 0;}
        }else if (word.getType()==15){//=read
            word=getSym();
            if (word.getType()==24){
                word=getSym();
                if (word.getType()==35){
                    if (SearchTables.judgeIdentifierExits(tables,word.getSymbol(),procNameList)==0){
                        System.out.println(word.getSymbol()+"未定义");
                        err(word);
                        return 0;
                    }
                    word=getSym();
                    while (word.getType()==18){
                        word=getSym();
                        if (word.getType()==35){
                            word=getSym();
                        }else  {err(word);return 0;}
                    }
                    if (word.getType()==25){

                    }else  {err(word);return 0;}
                }else  {err(word);return 0;}
            }else  {err(word);return 0;}

        }else if (word.getType()==16){//=write
            word=getSym();
            if (word.getType()==24){
                expression();
                word=getSym();
                while (word.getType()==18){
                    expression();
                    word=getSym();
                }
                if (word.getType()==25){

                }else  {err(word);return 0;}
            }else  {err(word);return 0;}

        }else {j--;}
        return 1;
    }
    public int condition(){
        word=getSym();
        if (word.getType()==4){
            if(expression()==0)return 0;
            return 1;
        }else {
            j--;
            if(expression()==0)return 0;
            word=getSym();
            if (word.getType()==20){

            }else if (word.getType()==33){
                if(expression()==0)return 0;
                return 1;
            }else if (word.getType()==29){
                if(expression()==0)return 0;
                return 1;
            }else if (word.getType()==30){
                if(expression()==0)return 0;
                return 1;
            }else if (word.getType()==31){
                if(expression()==0)return 0;
                return 1;
            }else if (word.getType()==32){
                if(expression()==0)return 0;
                return 1;
            }else  {err(word);return 0;}
            return 1;
        }
    }
    public int expression(){
        word=getSym();
        if (word.getType()==22||word.getType()==23){
            if(sum()==0)return 0;
            word=getSym();
            while (word.getType()==22||word.getType()==23){
                if(sum()==0)return 0;
                word=getSym();
            }
            if (word.getType()!=22&&word.getType()!=23){
                j--;
            }
            return  1;
        }else {
            j--;
            if(sum()==0)return 0;
            word=getSym();
            while (word.getType()==22||word.getType()==23){
                if(sum()==0)return 0;
                word=getSym();
            }
            if (word.getType()!=22&&word.getType()!=23){
                j--;
            }
            return  1;
        }
    }
    public int sum(){
        if(factor()==0)return 0;
        word=getSym();
        while (word.getType()==26||word.getType()==27){
            if(factor()==0)return 0;
            word=getSym();
        }
        if (word.getType()!=26&&word.getType()!=27){
            j--;
        }
        return 1;
    }
    public int factor(){
        word=getSym();
        if (word.getType()==35){
            if (SearchTables.judgeIdentifierExits(tables,word.getSymbol(),procNameList)==0){
                System.out.println(word.getSymbol()+"未定义");
                err(word);
                return 0;
            }
            return 1;
        }else if (word.getType()==36){
            return 1;
        }else if (word.getType()==24){
            if(expression()==0)return 0;
            word=getSym();
            if (word.getType()==25){
                return 1;
            }else  {err(word);return 0;}
        }else {err(word);return 0;}
    }
}
