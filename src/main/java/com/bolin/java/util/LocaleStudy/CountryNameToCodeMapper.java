package com.bolin.java.util.LocaleStudy;

import java.util.*;
import java.util.Locale;

public class CountryNameToCodeMapper {

    // 国家英文名称 -> 二字码 映射表
    private static final Map<String, String> countryNameToCodeMap = new HashMap<>();

    static {
        for (String isoCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", isoCode);
            String countryName = locale.getDisplayCountry(Locale.ENGLISH);
            countryNameToCodeMap.put(countryName, isoCode);
        }
    }

    /**
     * 根据国家英文名称获取 ISO 3166-1 Alpha-2 二字码
     * @param countryName 英文国家名，例如 "Germany"
     * @return 二字码，例如 "DE"，找不到则返回 null
     */
    public static String getCountryCode(String countryName) {
        return countryNameToCodeMap.getOrDefault(countryName, null);
    }

    /**
     * 将一组国家英文名称批量转换为国家代码
     */
    public static List<String> convertNamesToCodes(Collection<String> countryNames) {
        List<String> result = new ArrayList<>();
        for (String name : countryNames) {
            String code = getCountryCode(name);
            if (code != null) {
                result.add(code);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> countryNames = Arrays.asList(
                "Saudi Arabia", "United States", "Philippines",
                "United Kingdom", "Italy", "France", "Germany", "Spain"
        );

        List<String> codes = convertNamesToCodes(countryNames);
        System.out.println("国家英文名 → 二字码: " + codes);

//        国家英文名 → 二字码: [SA, US, PH, GB, IT, FR, DE, ES]
    }
}
