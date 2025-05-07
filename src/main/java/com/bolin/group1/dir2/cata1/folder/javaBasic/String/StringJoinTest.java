package com.bolin.group1.dir2.cata1.folder.javaBasic.String;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import com.bolin.group2.dir1.cata1.util.excel.EasyExcelUtils;
import com.bolin.group2.dir1.cata1.util.excel.practice.TiktiokLowDeviceProfile;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;


public class StringJoinTest {

//  6607ms
    public String joinM1(String brand ,String model){
        return brand+"_"+model;
    }

//    15653 ms

    public String joinM2(String brand ,String model){
        return String.join("_",brand,model);
    }


//     18110ms

    public String joinM3(String brand ,String model){
        return  StringUtil.join("_", Arrays.asList(brand, model)).toString();
    }

//     6398ms
    public String joinM4(String brand ,String model){
        return  new StringBuilder().append(brand).append("_").append(model).toString();
    }

    public String joinM5(String brand ,String model){
        return StrUtil.join("_", brand, model);
    }

    public String joinM6(String brand ,String model){
        return StringUtils.join("_", brand, model);
    }

    //   19130ms

    public String joinM7(String brand ,String model){
        return Joiner.on("_").join(brand,model);
    }


    
    public List<TiktiokLowDeviceProfile> getListFromExcel(){
        List<TiktiokLowDeviceProfile> tiktiokLowDeviceProfiles = EasyExcelUtils.readSheet0(TiktiokLowDeviceProfile.class, "excel/tiktok/tiktokLowDevice0424.xlsx", this.getClass().getClassLoader());
        return  tiktiokLowDeviceProfiles;
    }

    public static void main(String[] args) {
     
        StringJoinTest stringJoinTest = new StringJoinTest();
        List<TiktiokLowDeviceProfile> tiktiokLowDeviceProfiles = stringJoinTest.getListFromExcel();
        int  count=1000000;
        // 记录开始时间（ms）
        long startTime = System.currentTimeMillis();
        for(int i=1;i<=count;i++){
            tiktiokLowDeviceProfiles.forEach(tiktiokLowDeviceProfile -> stringJoinTest.joinM5(tiktiokLowDeviceProfile.getDeviceBrand(),tiktiokLowDeviceProfile.getDeviceModel()));

        }
        long endTime=System.currentTimeMillis();

        // 计算总耗时（毫秒）
        long totalTimeMs = endTime - startTime;
        System.out.println(totalTimeMs+"ms");

    }



}
