package com.bolin.group1.dir1.json.fastJson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class fastJsonrDemo {

    private  static String numString=new String();

    private static final String jsonStr = "{\n" +
            "  \"campaign_id\": \"adth14464_RTA_BR_3_2025-03-26\",\n" +
            "  \"ad_slot_id\": \"slot_01\",\n" +
            "  \"ad_list\": [\n" +
            "    {\n" +
            "      \"ad_id\": \"90802\",\n" +
            "      \"ad_name\": \"1344\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ad_id\": \"90803\",\n" +
            "      \"ad_name\": \"1345\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

//    这个的user_agent里面有重复的啊
    private static final String jsonStr2="{\"ad_request_id\":\"a047d558-cd00-462f-81ac-360b12017c0c\",\"android_id\":\"95b8c81398ad9d6c\",\"app_id\":\"1233\",\"bucket_id\":\"\",\"campaign_id_list\":[\"adth14464_RTA_BR_3_2025-03-26\"],\"campaigns_info\":\"[{\\\"campaign_name\\\":\\\"adth14464_RTA_BR_3_2025-03-26\\\",\\\"campaign_id\\\":\\\"adth14464_RTA_BR_3_2025-03-26\\\",\\\"ad_list\\\":[{\\\"ad_name\\\":\\\"1344\\\",\\\"ad_id\\\":\\\"90802\\\"}]}]\",\"channel\":\"com.fugo.wow\",\"city\":\"São Paulo\",\"client_ip\":\"187.90.222.24\",\"country\":\"BR\",\"device_brand\":\"Samsung\",\"device_model\":\"SM-J610G\",\"device_network_registration_time\":\"\",\"device_resolution\":\"\",\"device_timezone\":\"0\",\"gaid\":\"aca22113-d272-4435-a204-6e01ff05957a\",\"idfa\":\"\",\"media_source\":\"adthrustytk_int\",\"network_access\":\"wifi\",\"network_carrier\":\"\",\"os\":\"android\",\"os_version\":\"10\",\"rta_id_list\":[\"3\"],\"site_id\":\"VGdOu2Cc\",\"state\":\"\",\"sys_language\":\"pt\",\"test\":true,\"timestamp\":1744874179,\"user_agent\":\"Mozilla/5.0 (Linux; Android 10; SM-J610G Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/135.0.7049.38 Mobile Safari/537.36\",\"user_info\":\"{\\\"country\\\":\\\"BR\\\",\\\"device_model\\\":\\\"SM-J610G\\\",\\\"client_ip\\\":\\\"187.90.222.24\\\",\\\"user_agent\\\":\\\"Mozilla/5.0 (Linux; Android 10; SM-J610G Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/135.0.7049.38 Mobile Safari/537.36\\\"}\"}";

    private static final String jsonStr3="{\"ad_request_id\":\"68072258934e4a01b02a1016\",\"app_id\":\"1233\",\"bundle_id\":\"com.igg.android.lordsmobile\",\"campaigns_info\":\"[{\\\"campaign_name\\\":\\\"Descarga Tiktok para ver futebol ao vivo gratuitamente\\\",\\\"ad_list\\\":[{\\\"ad_name\\\":\\\"Descarga gratuita\\\",\\\"ad_id\\\":855700,\\\"ad_type\\\":\\\"Banner\\\",\\\"ad_placement\\\":\\\"Click url\\\",\\\"ad_width\\\":750,\\\"ad_height\\\":96}],\\\"campaign_id\\\":\\\"7962287\\\"}]\",\"channel\":\"com.igg.android.lordsmobile\",\"city\":\"Macauba\",\"client_ip\":\"45.171.252.80\",\"country\":\"BR\",\"device_brand\":\"unknow\",\"device_model\":\"M7S_PLUS\",\"device_timezone\":\"-03:00\",\"gaid\":\"e45b0799-6d21-4d6f-966d-7091d494d520\",\"media_source\":\"29312_29985\",\"network_access\":\"4G\",\"os\":\"android\",\"os_version\":\"8.1.0\",\"rta_id_list\":[\"3\"],\"site_id\":\"29312_29985_29985\",\"site_name\":\"undefined\",\"state\":\"Para\",\"sys_language\":\"PT\",\"test\":true,\"timestamp\":1745298009,\"user_agent\":\"Mozilla/5.0 (Linux; Android 8.1.0; M7S_PLUS Build/V1_20190630; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/131.0.6778.260 Safari/537.36\"}";



    public static final Map<String, Boolean> FIELDS = new LinkedHashMap<>() {{
        // basic info
        put("app_id", true);
        put("country", true);
        put("city", false);
        put("state", false);
        put("os", true);
        put("user_info", false);
        put("timestamp", true);
        // strategy
        put("rta_id_list", true);
        put("bucket_id", false);
        // advertising information
        put("campaign_id_list", false);
//        put("campaigns_info", true);
        put("ad_request_id", true);
        put("campaign_id",true);
        put("campaign_name",true);
        put("ad_id",true);
        put("ad_name",true);
        // device information
        put("gaid", true); // only required for android
        put("android_id", false);
        put("idfa", true); // only required for ios
        put("gaid_sha256", false);
        put("idfa_sha256", false);
        put("idfv", false);
        put("client_ip", true);
        put("user_agent", true);
        put("os_version", false);
        put("device_model", false);
        put("device_brand", false);
        put("sys_language", false);
        put("device_resolution", false);
        put("network_carrier", false);
        put("network_access", false);
        put("device_timezone", false);
        put("device_network_registration_time", false);
        // source info
        put("media_source", true);
        put("channel", true);
        put("site_id", true);
        // experiment information
        put("bundle_id", true);
        put("site_name", false);
    }};

    public static final long FIELDS_REQUIRED_SIZE = FIELDS.entrySet().stream().filter(Map.Entry::getValue).count();



    public static void main(String[] args) {
        checkValieField(jsonStr3);
    }

    /**
     * 自动遍历 JSON 对象或数组，适配任意嵌套结构
     * @param obj JSON 对象或数组
     * @param indent 缩进字符串，用于美观输出
     */
    public static Long traverseJson(Object obj, String indent,Map<String,List<String>> validFieldMap) {

        Long validFieldNum=0L;
        if (obj instanceof JSONObject jsonObject) {
            for (String key : jsonObject.keySet()) {

                Object value = jsonObject.get(key);
                if(key.equals("campaigns_info")){
                    int h=1;
                }
                System.out.print(indent + "🔑 " + key + ": ");
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    System.out.println();
                    if(value instanceof  JSONArray && !((JSONArray) value).isEmpty()){
                        if(checkKeyAndValueValid(key,value)){
                            addToMap(validFieldMap,key,String.valueOf(value));
                            validFieldNum++;
                            numString+=validFieldNum+":"+key;
                        }
                    }
                    validFieldNum+=traverseJson(value, indent + "    ",validFieldMap);
                } else if (value instanceof String valueStr && isJson(valueStr)) {
                    System.out.println("（嵌套 JSON 字符串）");
                    validFieldNum+=traverseJson(JSON.parse(valueStr), indent + "    ", validFieldMap);
                } else {

                    if(checkKeyAndValueValid(key,value)){
                        addToMap( validFieldMap,key,String.valueOf(value));
                        validFieldNum++;
                        numString+=validFieldNum+":"+key;
                        System.out.println("当前validFieldNum的值为"+validFieldNum);

                    }
                    System.out.println(value);
                }
            }
        } else if (obj instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println(indent + "📦 数组元素[" + i + "]:");
                validFieldNum+=traverseJson(jsonArray.get(i), indent + "    ", validFieldMap);
            }
        } else {
            // 其他基础类型
            System.out.println(indent + obj);
        }
        return  validFieldNum;
    }


    public static Boolean checkKeyAndValueValid(String key,Object value){
        return  FIELDS.get(key)!=null&&FIELDS.get(key)&& !String.valueOf(value).equals("");
    }


    public static boolean isJson(String str) {
        try {
            Object json = JSON.parse(str);
            return json instanceof JSONObject || json instanceof JSONArray;
        } catch (Exception e) {
            return false;
        }
    }

    // 添加一个 key-value 对（key 可以重复，value 会追加）
    public static void addToMap(Map<String, List<String>> map, String key, String value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }


    public static  void checkValieField(String jsonStr){

        System.out.println("🔍 自动解析并遍历 JSON：");
        Object json = JSON.parse(jsonStr);
        Map<String,List<String>> validFieldMap=new HashMap<String,List<String>>();
        Long fieldValidNum = traverseJson(json, "",validFieldMap);
        System.out.println("fieldValieNum"+fieldValidNum);
        Double filledRate=(double) fieldValidNum/(FIELDS_REQUIRED_SIZE-1);
        System.out.println("填充率"+filledRate);



    }

}
