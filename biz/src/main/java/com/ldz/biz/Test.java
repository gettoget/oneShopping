package com.ldz.biz;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    /*public static void main(String[] args) {
        DateTimeFormatter hHmmssSSS = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");

        // 创建51 个中奖号码  根据 中奖规则 计算中奖率
        String d = "20190410080000000";
        DateTime now = DateTime.parse(d, hHmmssSSS);
        System.out.println(now);
        List<String> list = new ArrayList<>();
        Random random = new Random();
        int i1 = random.nextInt(5788);
        // 生成 51 个号码
        for(int i = 0 ; i < i1 ; i++){
            // 根据时间生成
            int nextInt = random.nextInt(86400000-28800000);
            DateTime plusMillis = now.plusMillis(nextInt);
            String s = plusMillis.toString(hHmmssSSS);
            String s1 = s.substring(8);

            list.add(s1);
        }
        // 号根据 时间排序
        List<String> collect = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        // 计算中奖号码
        List<Long> list1 = new ArrayList<>();
        long zjhm = 0;
        long total = 0;
        for(int i = 0; i < 201; i++){

            total += Long.parseLong(collect.get(i));
            long aa = 0;
            for(int j = i ; j>=0 ; j-- ){
                aa += Long.parseLong(collect.get(j));
                long l = (aa + i1) / (i1) + 10000001;
                list1.add(l);
            }
            if(i == 49){
                zjhm = total % 201 + 10000001;
            }
        }

        System.out.println("zjhm + " + zjhm);

        if(list1.contains(zjhm)){
            System.out.println("中奖号码为: " + zjhm);
        }
        list1.sort(Long::compareTo);
        System.out.println(list1);



    }*/

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replace("-","").substring(15,25).toUpperCase();
        System.out.println(uuid);

    }


}
