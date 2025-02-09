package com.bolin.model.param;


import lombok.Data;

@Data
public class AiQueryParam {
    Boolean  newContact;
    String   userQuestion;
    String   systemSet;
    String   filePath;
}
