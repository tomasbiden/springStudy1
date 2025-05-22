package com.bolin.com.fasterxml.jackson.databind;

import cn.hutool.core.collection.CollStreamUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class saveObjectTofile {
    //    todo 注意删除
//    临时记录一下
//    Map<String, String> map = CollStreamUtil.toMap(orderList, SheinOrderApiResponse.Data::getShipping_country, obj -> CountryUtils.getCountryCode(obj.getShipping_country()));
    public static void saveToFile( Map<String, String> data,String fileName) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//   "output/data/user.json"
    public static void saveToFile(Object data, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(fileName);

        // 自动创建父目录
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException("写入 JSON 文件失败: " + fileName, e);
        }
    }

    public static void save1(){
//          Map<Integer, List<String>> mysqlAffMediaSourceMap = CollStreamUtil.groupKeyValue(affiliatePidDTOList, AffiliatePidDTO::getAffiliateId, AffiliatePidDTO::getMediaSource);
    }
    public static  void saveMaptest1(){
        Map<String, Object> data = new HashMap<>();
        data.put("user", Map.of("name", "张三", "age", 25));
        data.put("roles", List.of("admin", "editor"));

        saveToFile(data, "save/user.json");
    }

    public static void main(String[] args) {
       saveMaptest1();

    }
}
