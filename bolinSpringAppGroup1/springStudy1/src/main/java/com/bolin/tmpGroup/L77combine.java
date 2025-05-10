package com.bolin.tmpGroup;

import java.util.ArrayList;
import java.util.List;

public class L77combine {

    public List<List<Integer>> result;

    public List<Integer> tmpList;

    public List<List<Integer>> combine_250403(int n, int k) {
        result=new ArrayList<List<Integer>>();
        tmpList=new ArrayList<>();
        for(int i=1;i<=n;i++){
            int tmpLevel=1;
            tmpList.add(i);
            backTracking(i,tmpLevel++,k,n);
            tmpLevel--;
        }
        return  result;

    }

    public  void backTracking(int frontNum,int tmpLevel,int k,int n){
        if(tmpLevel==k){
            result.add(new ArrayList<>(tmpList));
            return;
        }
        for(int j=frontNum+1;j<=n;j++){
            tmpList.add(j);
            backTracking(j,++tmpLevel,k,n);
            tmpLevel--;
            tmpList.remove(tmpList.size()-1);
        }

    }
    public static void main(String[] args){
        L77combine l77combine = new L77combine();
        l77combine.combine_250403(4,2);

    }
}
