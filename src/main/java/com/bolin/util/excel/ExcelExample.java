package com.bolin.util.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExample {

    public static void main(String[] args) {
        // 创建一个新的工作簿（Excel文件）
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // 创建一个工作表
            Sheet sheet = workbook.createSheet("Sheet1");

            // 创建第一行
            Row row = sheet.createRow(0);
            // 创建单元格并设置值
            row.createCell(0).setCellValue("Name");
            row.createCell(1).setCellValue("Age");
            row.createCell(2).setCellValue("City");

            // 创建第二行
            row = sheet.createRow(1);
            row.createCell(0).setCellValue("Alice");
            row.createCell(1).setCellValue(30);
            row.createCell(2).setCellValue("New York");

            // 创建第三行
            row = sheet.createRow(2);
            row.createCell(0).setCellValue("Bob");
            row.createCell(1).setCellValue(25);
            row.createCell(2).setCellValue("Los Angeles");

            // 写入Excel文件
            try (FileOutputStream fileOut = new FileOutputStream("example.xlsx")) {
                workbook.write(fileOut);
            }

            System.out.println("Excel文件已生成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
