package com.bolin.group2.dir1.cata1.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建用户答案请求
 *
 * 
 * 

 */
@Data
public class UserAnswerAddRequest implements Serializable {

    /**
     * id（用户答案 id，用于保证提交答案的幂等性）
     */
    private Long id;

    private Long tenantId;

    /**
     * 应用 id
     */
    private Long appId;

    private Long userId;

    /**
     * 用户答案（JSON 数组）
     */
    private List<String> choices;

    private static final long serialVersionUID = 1L;
}