package com.bolin.group2.dir1.cata1.util.excel.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseImportDTO {
    @ExcelIgnore
    @JsonIgnore
    @ApiModelProperty(value = "行号", hidden = true)
    protected int excelRowNum;
}
