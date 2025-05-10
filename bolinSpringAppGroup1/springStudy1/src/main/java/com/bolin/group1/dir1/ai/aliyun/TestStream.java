package com.bolin.group1.dir1.ai.aliyun;

// 建议dashscope SDK的版本 >= 2.12.0

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
import org.apache.rocketmq.shaded.org.slf4j.Logger;
import org.apache.rocketmq.shaded.org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestStream {

    private static final Logger logger = LoggerFactory.getLogger( TestStream.class);
    private static StringBuilder fullContent = new StringBuilder();
    public static void callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {

        


    }

    private static void handleGenerationResult(GenerationResult message) {
        String content = message.getOutput().getChoices().get(0).getMessage().getContent();
        fullContent.append(content);
        System.out.println(content);
    }
    public static  void callWithMessage(List<Message> messages,String userString)throws ApiException, NoApiKeyException, InputRequiredException{
        if(messages.isEmpty()){
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是一个乐于助人的帮手")
                    .build();
            messages.add(systemMsg);

        }
        System.out.println(userString);

        fullContent=new StringBuilder("");


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
                .incrementalOutput(true)
                .build();
        Flowable<GenerationResult> result = gen.streamCall(param);
        result.blockingForEach(message -> handleGenerationResult(message));

        String assistantContent = fullContent.toString();

        Message assistantMessage = Message.builder()
                .role(Role.ASSISTANT.getValue())
                .content(assistantContent)
                .build();

        messages.add(assistantMessage);



    }
    public static void main(String[] args) {
        try {
            List<Message> messages=new ArrayList<Message>();

            callWithMessage(messages,"给我写个1000字作文来介绍中国");
//            System.out.println(JsonUtils.toJson(result));
           callWithMessage(messages,"世界上最高的山是什么，这是我的第几个问题，我的第一个问题是什么");


        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 使用日志框架记录异常信息
            System.err.println("An error occurred while calling the generation service: " + e.getMessage());
        }
        System.exit(0);
    }
}