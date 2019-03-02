package com.lujia;

/**
 * @author :lujia
 * @date :2019/2/14  11:13
 */
public class ArraysTest {


    public static void main(String[] args) {


        System.out.println( ArraysTest.function(new int[]{2,1,1,3,1}));

    }


    public static Integer function(int [] a){

        for (int i=0;i<a.length;i++){
            int num=1;
            for (int j=i+1;j<a.length;j++){
                if (a[i]==a[j]){
                    num++;
                }
            }
            if (num>a.length/2){
                return a[i];
            }
        }
        return null;

    }
}
