package com.bolin.util.excel;

import com.alibaba.excel.EasyExcel;
import com.bolin.util.vo.UserData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelExportExample {

    public static void main(String[] args) throws InterruptedException {
        // 创建一些示例数据
        List<UserData> data = Arrays.asList(
            new UserData(1L, "Alice", 28, "2022-01-01"),
            new UserData(2L, "Bob", 34, "2021-05-23"),
            new UserData(3L, "Charlie", 25, "2020-12-15")
        );

        // 指定文件路径和输出文件的类
        String fileName = "user_data.xlsx";

        try (FileOutputStream fileOut = new FileOutputStream("output.xlsx")) {
            EasyExcel.write(fileOut, UserData.class)
                    .sheet("Sheet1")
                    .doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
