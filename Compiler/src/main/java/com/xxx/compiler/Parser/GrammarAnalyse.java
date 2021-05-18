package com.xxx.compiler.Parser;

import com.xxx.compiler.entity.Word;

import java.util.ArrayList;

public class GrammarAnalyse {
    private ArrayList list;
    private int j;
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
                System.out.println("分析结束。");
                return;
            }else {
                System.out.println("err");
            }
        }

    }

    /**
     *
     * @param TX
     * @param LEV
     */
    public void block(int TX,int LEV){

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
            System.out.println("怎么就溢出了啊？？？");
        }
        return null;
    }

    /**
     * 出错提示
     * @param word
     */
    public void err(Word word){
        System.out.println("出错位置:"+word.getLine()+"行"+"---"+word.getSymbol());
    }
    /**
     * 分程序
     */
    public int  Subprogram(){
        word=getSym();
        if(word.getType()==1){//word==const
            word=getSym();//读取下一个单词
            if (word.getType()==35){
                word=getSym();
                if (word.getType()==20){ //word=="="
                   word=getSym();
                   if (word.getType()==36){//word=="常数"
                       word=getSym();
                       while (word.getType()==18){//word==","
                          if(word.getType()==35) {//word==const
                              word=getSym();
                              if (word.getType()==20){
                                  word=getSym();
                                  if (word.getType()==36){
                                      word=getSym();
                                  }else {err(word);return 0;}
                              }else {err(word);return 0;}
                          }else {err(word);return 0;}
                       }
                       if (word.getType()==19){//word==";"
                           word=getSym();
                           if (word.getType()==2){//word==var
                               word=getSym();
                               if (word.getType()==35) {//word==标识符
                                   word=getSym();
                                   while(word.getType()==18){//word==","
                                       word=getSym();
                                       if (word.getType()==35){//word==标识符
                                           word=getSym();
                                       }else {err(word);return 0;}
                                   }
                                   if (word.getType()==19){//word==";"
                                       word=getSym();
                                       while (word.getType()==3){
                                           word=getSym();
                                           if(word.getType()==35){
                                               word=getSym();
                                               if (word.getType()==19){
                                                    word=getSym();
                                                   if (Subprogram()==0)return 0;
                                                   if (word.getType()==19){
                                                       word=getSym();
                                                   }else {err(word);return 0;}
                                               }else{err(word);return 0;}
                                           }else {err(word);return 0;}

                                       }
                                       if(statement()==0){
                                           return 0;
                                       }else return 1;


                                   }else {err(word);return 0;}
                               }else {err(word);return 0;}
                           }else if(word.getType()==3){
                               while (word.getType()==3){
                                   word=getSym();
                                   if(word.getType()==35){
                                       word=getSym();
                                       if (word.getType()==19){
                                           if (Subprogram()==0)return 0;
                                           word=getSym();
                                           if (word.getType()==19){
                                               word=getSym();
                                           }else {err(word);return 0;}
                                       }else {err(word);return 0;}
                                   }else {err(word);return 0;}

                               }
                               j--;
                               if(statement()==0){
                                   return 0;
                               }else return 1;
                           }else {
                               j--;
                               if(statement()==0){
                                   return 0;
                               }else return 1;
                           }
                       }else {err(word);return 0;}

                   }else {err(word);return 0;}
                }else {err(word);return 0;}
            }else {err(word);return 0;}
        }else if (word.getType()==2){//word==var
            //word==var
            word=getSym();
            if (word.getType()==35) {//word==标识符
                word=getSym();
                while(word.getType()==18){//word==","
                    word=getSym();
                    if (word.getType()==35){//word==标识符
                        word=getSym();
                    }else {err(word);return 0;}
                }
                if (word.getType()==19){//word==";"
                    word=getSym();
                    while (word.getType()==3){
                        word=getSym();
                        if(word.getType()==35){
                            word=getSym();
                            if (word.getType()==19){
                                if (Subprogram()==0)return 0;
                                word=getSym();
                                if (word.getType()==19){
                                    word=getSym();
                                }else {err(word);return 0;}
                            }else{err(word);return 0;}
                        }else {err(word);return 0;}

                    }
                    j--;
                    if(statement()==0){
                        return 0;
                    }else return 1;


                }else {err(word);return 0;}
            }else {err(word);return 0;}
        } else if (word.getType() == 3) {//word==procedure
            while (word.getType()==3){
                word=getSym();
                if(word.getType()==35){
                    word=getSym();
                    if (word.getType()==19){
                        if (Subprogram()==0)return 0;
                        word=getSym();
                        if (word.getType()==19){
                            word=getSym();
                        }else {err(word);return 0;}
                    }else {err(word);return 0;}
                }else {err(word);return 0;}

            }
            j--;
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
            }else  {err(word);return 0;}
        }else if (word.getType()==15){//=read
            word=getSym();
            if (word.getType()==24){
                word=getSym();
                if (word.getType()==35){
                    word=getSym();
                    while (word.getType()==17){
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
                while (word.getType()==17){
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
