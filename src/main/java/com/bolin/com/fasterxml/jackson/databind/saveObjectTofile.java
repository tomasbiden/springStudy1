package com.bolin.com.fasterxml.jackson.databind;

import cn.hutool.core.collection.CollStreamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class saveObjectTofile {
    //    todo 注意删除
//    临时记录一下
//    Map<String, String> map = CollStreamUtil.toMap(orderList, SheinOrderApiResponse.Data::getShipping_country, obj -> CountryUtils.getCountryCode(obj.getShipping_country()));
    public static void saveToFile( Map<String, String> data) {
        String CACHE_FILE = "shein-cps-tow-code.json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CACHE_FILE), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
