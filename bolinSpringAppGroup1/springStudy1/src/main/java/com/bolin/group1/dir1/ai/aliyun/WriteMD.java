package com.bolin.group1.dir1.ai.aliyun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteMD {

    public static void main(String[] args) {
        // 假设这是你从后端得到的字符串内容
        String content = "# 标题\n\n这是一个 Markdown 文件的内容示例。\n- 项目 1\n- 项目 2\n- 项目 38986+566";

        // 创建一个 File 对象，表示将要写入的 .md 文件
        File markdownFile = new File("F:\\group1\\JAVA\\JAVA1\\projectSet2\\deepSeek1\\md\\example.md");

        // 使用 BufferedWriter 来写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(markdownFile,true))) {
            // 将字符串内容写入到 .md 文件中
            writer.append(content);
            System.out.println("内容已成功写入 example.md 文件。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addToMd(String content){


        // 创建一个 File 对象，表示将要写入的 .md 文件
        File markdownFile = new File("F:\\group1\\JAVA\\JAVA1\\projectSet2\\deepSeek1\\md\\example.md");

        // 使用 BufferedWriter 来写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(markdownFile,true))) {
            // 将字符串内容写入到 .md 文件中
            writer.append(content);
            System.out.println("内容已成功写入 example.md 文件。");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

