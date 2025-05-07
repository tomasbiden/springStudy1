package com.bolin.group2.dir1.cata1.util.excel.practice;

import com.bolin.group2.dir1.cata1.util.excel.EasyExcelUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TikTokLowDeviceFilter {

    public static Set<String> tiktokLowDeviceSet= new HashSet<String>();

    public static String generateInsertStatements(List<TiktiokLowDeviceProfile> devices, String tableName) {
        if (devices == null || devices.isEmpty()) {
            return "-- No data to insert";
        }

        // 获取第一个对象用于提取字段名（假设所有对象结构相同）
        TiktiokLowDeviceProfile sample = devices.get(0);
        String columns = String.join(", ",
                "platform_name",
                "region_code",
                "device_brand",
                "device_model"
        );

        // 生成VALUES部分
        String values = devices.stream()
                .map(device -> String.format("('%s','%s', '%s', '%s')",
                        escapeSql(device.getPlatformName()),
                        escapeSql(device.getRegionCode()),
                        escapeSql(device.getDeviceBrand()),
                        escapeSql(device.getDeviceModel())))
                .collect(Collectors.joining(",\n    "));

        return String.format("INSERT INTO %s (%s) VALUES \n    %s;",
                tableName, columns, values);
    }

    // SQL注入防护
    private static String escapeSql(String input) {
        return input == null ? "" : input.replace("'", "''");
    }

    public static List<TiktiokLowDeviceProfile> getDistinctDevicesStream(List<TiktiokLowDeviceProfile> list) {
        return list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                device -> String.join("|",
                                        device.getRegionCode().toUpperCase(),
                                        device.getDeviceBrand().toUpperCase(),
                                        device.getDeviceModel().toUpperCase()
                                ),
                                Function.identity(),
                                (existing, replacement) -> existing,  // 保留首次出现的记录
                                LinkedHashMap::new
                        ),
                        map -> new ArrayList<>(map.values())
                ));
    }


    


    @PostConstruct
    public void init(){
        List<TiktiokLowDeviceProfile> tiktiokLowDeviceProfiles = EasyExcelUtils.readSheet0(TiktiokLowDeviceProfile.class, "excel/tiktok/tiktokLowDevice0424.xlsx", this.getClass().getClassLoader());
        for (TiktiokLowDeviceProfile tiktiokLowDeviceProfile : tiktiokLowDeviceProfiles) {
            tiktiokLowDeviceProfile.setDeviceModel(tiktiokLowDeviceProfile.getDeviceModel().toUpperCase());
            tiktiokLowDeviceProfile.setDeviceBrand(tiktiokLowDeviceProfile.getDeviceBrand().toUpperCase());
        }

        List<TiktiokLowDeviceProfile> distinctDevicesStream = getDistinctDevicesStream(tiktiokLowDeviceProfiles);
        String s = generateInsertStatements(distinctDevicesStream, "pac_rta_shield.rta_device_filter");
        int h=1;


    }

    public static String buildLowDeviceKey(String regionCode, String brand, String model) {
        return String.join("|",
                Optional.ofNullable(regionCode).orElse("").replaceAll("\\s+", "").toUpperCase(),
                Optional.ofNullable(brand).orElse("unknown").replaceAll("\\s+", "").toUpperCase(),
                Optional.ofNullable(model).orElse("").replaceAll("\\s+", "").toUpperCase()
        );
    }


    public static void main(String[] args) throws IOException {


//        List<TiktiokLowDeviceProfile> tiktiokLowDeviceProfiles = EasyExcelUtils.readSheet0(TiktiokLowDeviceProfile.class, "excel/tiktok/tiktokLowDevice0411.xlsx");

//        int h=
//        int h=1;


    }
}
