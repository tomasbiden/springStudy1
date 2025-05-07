package com.bolin.group2.dir1.cata1.util.excel;
import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.bolin.group2.dir1.cata1.util.ZoneUtil;
import com.bolin.group2.dir1.cata1.util.excel.converter.DateTimeZoneNumberConverter;
import com.bolin.group2.dir1.cata1.util.excel.converter.DateTimeZoneStringConverter;
import com.bolin.group2.dir1.cata1.util.excel.converter.SpaceStringConverter;
import com.bolin.group2.dir1.cata1.util.excel.dto.BaseImportDTO;
import com.bolin.group2.dir1.cata1.util.excel.listener.ExcelHeadDataListener;
import lombok.SneakyThrows;
import java.io.InputStream;
import java.util.List;

public class EasyExcelUtils {



    private static ExcelReaderBuilder commonReaderBuilder(InputStream inputStream, Class<?> head, ReadListener<?> readListener) {
        return EasyExcel.read(inputStream, head, readListener)
                .registerConverter(new DateTimeZoneStringConverter(ZoneUtil.CN))
                .registerConverter(new DateTimeZoneNumberConverter(ZoneUtil.CN))
                .registerConverter(new SpaceStringConverter());
    }


    @SneakyThrows
    public static <T extends BaseImportDTO> List<T> readSheet0(Class<T> clazz,String fileResourcePath, ClassLoader classLoader) {

        InputStream inputStream = classLoader.getResourceAsStream(fileResourcePath);
        Assert.notNull(inputStream, "获取模板文件失败");
        ExcelReaderBuilder excelReaderBuilder = commonReaderBuilder(inputStream, clazz, new ExcelHeadDataListener<>(clazz));
        List<T> objects = excelReaderBuilder.sheet().doReadSync();
        return objects;


    }
/*

    @SneakyThrows
    public static <T extends BaseImportDTO> List<T> readSheet0(Class<T> clazz, String fileResourcePath) {
        InputStream inputStream = new ClassPathResource(fileResourcePath).getInputStream();
        ExcelReaderBuilder excelReaderBuilder = commonReaderBuilder(inputStream, clazz, new ExcelHeadDataListener<>(clazz));
        List<T> objects = excelReaderBuilder.sheet().doReadSync();

        return objects;
    }

    @SneakyThrows
    public static <T extends BaseImportDTO> List<T> readSheet0(Class<T> clazz, MultipartFile file) {
        return readSheet0(clazz, file, new ExcelHeadDataListener<>(clazz));
    }



    @SneakyThrows
    public static <T extends BaseImportDTO> List<T> readSheet0(Class<T> clazz, MultipartFile file, AnalysisEventListener<T> listener) {
        // 校验文件后缀
        String fileName = file.getOriginalFilename();
        Assert.isTrue(StrUtil.endWithAnyIgnoreCase(fileName, ExcelConstant.ALLOWED_SUFFIX), "文件格式错误。支持扩展名：.xlsx .xls .csv");
        // ExcelHeadDataListener 根据class字段校验表头
        ExcelReaderBuilder excelReaderBuilder = commonReaderBuilder(file.getInputStream(), clazz, listener);
        if (fileName != null && fileName.contains(".csv")) {
            excelReaderBuilder.excelType(ExcelTypeEnum.CSV).charset(Charset.forName("GBK"));
        }
        List<T> objects = excelReaderBuilder.sheet().doReadSync();

        return objects;
    }

 */
}
