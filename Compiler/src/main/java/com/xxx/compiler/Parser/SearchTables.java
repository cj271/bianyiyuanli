package com.xxx.compiler.Parser;

import com.xxx.compiler.entity.Table;

import java.util.ArrayList;

public class SearchTables {

        public static int searchExits(ArrayList list,int j,String procName,String name){
        int n=1;
        Table tableTem;
        for (int i=0;i<list.size();i++){
            tableTem= (Table) list.get(i);
            if (tableTem.getValOrlev()==j||tableTem.getKind()==1){
                if (tableTem.getProcName().equals(procName) &&tableTem.getName().equals(name)){
                    return 0;
                }
            }
        }
        return 1;
    }
    public static int getDX(ArrayList list,int j,String procName){
        int n=0;
        Table tableTem;
        for (int i=0;i<list.size();i++){
            tableTem= (Table) list.get(i);
            if (tableTem.getValOrlev()==j&& tableTem.getProcName().equals(procName)){
                if (tableTem.getKind()==2){
                    n++;
                }
            }
        }
        return n;

    }
    public static int judgeIdentifierExits(ArrayList tableList,String name,ArrayList procNameList){
        int n=0;
        Table tableTem;
        String procName;
        for (int j=procNameList.size()-1;j>=0;j--){
            procName= (String) procNameList.get(j);
            for (int i=0;i<tableList.size();i++){
                tableTem= (Table) tableList.get(i);
                if (tableTem.getProcName().equals(procName) &&tableTem.getName().equals(name)){
                    return tableTem.getKind();
                }
            }
        }
        return n;
    }

}
