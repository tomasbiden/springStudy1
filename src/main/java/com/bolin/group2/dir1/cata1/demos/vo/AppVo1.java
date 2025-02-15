package com.bolin.group2.dir1.cata1.demos.vo;

import com.bolin.group2.dir1.cata1.demos.pojo.Question;
import com.bolin.group2.dir1.cata1.demos.pojo.User;
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
