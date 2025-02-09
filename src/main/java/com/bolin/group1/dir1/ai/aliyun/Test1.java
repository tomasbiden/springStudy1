package com.bolin.group1.dir1.ai.aliyun;

// 建议dashscope SDK的版本 >= 2.12.0
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.System;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;

public class Test1 {
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

    public static String getHead1(String title){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("\n").append("# ").append(title).append("\n").toString();

    }
    public static  GenerationResult callWithMessage(List<Message> messages,String userString)throws ApiException, NoApiKeyException, InputRequiredException{
        if(messages.isEmpty()){

            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是一个乐于助人的帮手")
                    .build();
            messages.add(systemMsg);
            addToMd(getHead1("系统设置")+systemMsg.getContent());

        }
        System.out.println(userString);
        addToMd(getHead1("用户问题")+userString);

        Message userMessage = Message.builder()
                .role(Role.USER.getValue())
                .content(userString)
                .build();
        messages.add(userMessage);
        Generation gen = new Generation();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
//                .apiKey("sk-17708ab8a6954b5d8e26fd139c9d3621")
                .model("deepseek-r1")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        GenerationResult result = gen.call(param);

        String assistantContent = result.getOutput().getChoices().get(0).getMessage().getContent();
        addToMd(getHead1("回答")+assistantContent);
        Message assistantMessage = Message.builder()
                .role(Role.ASSISTANT.getValue())
                .content(assistantContent)
                .build();

        messages.add(assistantMessage);

        return  result;

    }
    public static void main(String[] args) {
        try {
            List<Message> messages=new ArrayList<Message>();



            GenerationResult result = callWithMessage(messages,"给我举出一个简单例子来讲解一下java泛型");
//            System.out.println(JsonUtils.toJson(result));
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());

            result=callWithMessage(messages,"世界上最高的山是什么，这是我的第几个问题，我的第一个问题是什么");

            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());



        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 使用日志框架记录异常信息
            System.err.println("An error occurred while calling the generation service: " + e.getMessage());
        }
        System.exit(0);
    }
}