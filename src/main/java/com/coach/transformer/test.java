package com.coach.transformer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
    public static void main(String[] args) {
        List<String> mList= Arrays.asList("i","am","sai");
        List<String> mList2= Arrays.asList("i","am","test");
       List<String> commonList= mList2.stream()
               .map(x-> mList.contains(x)? x:null)
               .filter(x->x!=null).collect(Collectors.toList());
        System.out.println(commonList);


    }
}
