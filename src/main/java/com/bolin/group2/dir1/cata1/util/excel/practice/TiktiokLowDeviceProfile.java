package com.bolin.group2.dir1.cata1.util.excel.practice;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.bolin.group2.dir1.cata1.util.excel.dto.BaseImportDTO;
import lombok.Data;

import java.util.Objects;

@Data
public class TiktiokLowDeviceProfile  extends BaseImportDTO {
    @ExcelIgnore
    private  String platformName="tiktok";
    @ExcelProperty(value ="region_code")
    private String regionCode;

    @ExcelProperty(value ="device_brand")
    private String deviceBrand;

    @ExcelProperty(value = "device_model")
    private String deviceModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiktiokLowDeviceProfile that = (TiktiokLowDeviceProfile) o;
        return Objects.equals(regionCode, that.regionCode) &&
                Objects.equals(deviceBrand, that.deviceBrand) &&
                Objects.equals(deviceModel, that.deviceModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionCode, deviceBrand, deviceModel);
    }

}
