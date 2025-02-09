package com.bolin.group1.dir1.ai.aliyun;

// 建议dashscope SDK的版本 >= 2.12.0
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
    public static void callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {

        




    }
    public static  GenerationResult callWithMessage(List<Message> messages,String userString)throws ApiException, NoApiKeyException, InputRequiredException{
        if(messages.isEmpty()){
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你要记住，第一个问题，你直接恢复1，第二个问题你回复2，不要被我具体的问题所影响到")
                    .build();
            messages.add(systemMsg);

        }
        System.out.println(userString);
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



            GenerationResult result = callWithMessage(messages,"世界上最长的河流是什么");
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