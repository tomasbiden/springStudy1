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
import com.bolin.group2.dir1.cata1.model.param.AiQueryParam;
import org.springframework.stereotype.Component;

@Component
public class AIMannger {

    private List<Message> messages=new ArrayList<Message>();
    public  void addToMd(String content,String filePath){

        // 创建一个 File 对象，表示将要写入的 .md 文件
//        "F:\\group1\\JAVA\\JAVA1\\projectSet2\\deepSeek1\\md\\example.md"
        File markdownFile = new File(filePath);
        // 使用 BufferedWriter 来写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(markdownFile,true))) {
            // 将字符串内容写入到 .md 文件中
            writer.append(content);
            System.out.println("内容已成功写入"+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  String getHead1(String title){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("# ").append(title).append("\n").toString();

    }
    public  GenerationResult callWithMessage(AiQueryParam aiQueryParam)throws ApiException, NoApiKeyException, InputRequiredException{
        if(aiQueryParam.getNewContact()){
            messages.clear();
        }
        if(messages.isEmpty()){

            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content(aiQueryParam.getSystemSet())
                    .build();
            messages.add(systemMsg);
            addToMd(getHead1("系统设置:"+systemMsg.getContent()),aiQueryParam.getFilePath());

        }
        System.out.println(aiQueryParam.getUserQuestion());
        addToMd(getHead1("问："+aiQueryParam.getUserQuestion()),aiQueryParam.getFilePath());

        Message userMessage = Message.builder()
                .role(Role.USER.getValue())
                .content(aiQueryParam.getUserQuestion())
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
        addToMd(getHead1("回答"+"\n"+assistantContent),aiQueryParam.getFilePath());
        Message assistantMessage = Message.builder()
                .role(Role.ASSISTANT.getValue())
                .content(assistantContent)
                .build();

        messages.add(assistantMessage);

        return  result;

    }

}