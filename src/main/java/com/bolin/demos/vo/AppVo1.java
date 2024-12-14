package com.bolin.demos.vo;

import com.bolin.demos.pojo.Question;
import com.bolin.demos.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class AppVo1 {
    long id;

    /**
     * 应用名
     */
    private String appName;

    private  List<Question>  questionList;



    private List<String> questionContent;

    private User user;

}
