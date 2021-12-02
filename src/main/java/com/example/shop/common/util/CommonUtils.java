package com.example.shop.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

@Slf4j
public class CommonUtils {

    public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void printMap(Map<String,Object> map){
        Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
        Entry<String,Object> entry = null;
        log.info("--------------------printMap--------------------\n");
        while(iterator.hasNext()){
            entry = iterator.next();
            log.info("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
        }
        log.info("");
        log.info("------------------------------------------------\n");
    }

    public static void printList(List<Map<String,Object>> list){
        Iterator<Entry<String,Object>> iterator = null;
        Entry<String,Object> entry = null;
        log.info("--------------------printList--------------------\n");
        int listSize = list.size();
        for(int i=0; i<listSize; i++){
            log.info("list index : "+i);
            iterator = list.get(i).entrySet().iterator();
            while(iterator.hasNext()){
                entry = iterator.next();
                log.info("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
            }
            log.info("\n");
        }
        log.info("------------------------------------------------\n");
    }
}
