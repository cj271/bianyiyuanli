package com.xxx.compiler;


import com.xxx.compiler.Lexical_analyzer.Analyse;
import com.xxx.compiler.Parser.GrammarAnalyse;
import com.xxx.compiler.entity.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class themain {
    public static void main(String[] args) {
        ArrayList<Word> list=null;
        try{
            FileReader reader = new FileReader("src/main/java/com/xxx/compiler/testCode/pl0");
            BufferedReader br = new BufferedReader(reader);
            String s = null;

            //将配置文件转为map
            int line=0;
            while((s = br.readLine()) != null) {
                line++;
                if (s.equals("")){
                    continue;
                }
                list= Analyse.go(s,line);
            }
            br.close();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        for (int i=0;i<list.size();i++){
            list.get(i).printWord();
        }
        if(Analyse.getDuohangzhushi()==1){
            System.out.println("error! *) 不能匹配");
        }
        GrammarAnalyse grammarAnalyse=new GrammarAnalyse(list);
        grammarAnalyse.go();
    }
}
