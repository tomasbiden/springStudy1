package com.bolin.group2.dir1.cata1.init;

import com.bolin.group2.dir1.cata1.demos.pojo.App;
import com.bolin.group2.dir1.cata1.service.AppService;
import com.bolin.group2.dir1.cata1.service.UserAnswerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 执行时机​​：
 * 比 @PostConstruct 更晚（所有bean已初始化完成）
 * 比 ApplicationReadyEvent 更早（应用尚未完全就绪）
 */

/**
 * 
 */

@SpringBootApplication
public class CommandLineRunnerExample implements  CommandLineRunner {

    @Autowired
    private AppService appServiceImpl;


    // CommandLineRunner 示例

    public void run(String... args) throws Exception {
//        这里可以进行初始化，加载数据
        try {
            App app = appServiceImpl.test1();
        }catch (Exception e){
//             暂时放过
        }

    }

}



