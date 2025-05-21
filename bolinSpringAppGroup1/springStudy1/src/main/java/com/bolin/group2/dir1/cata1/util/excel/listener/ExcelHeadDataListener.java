package com.bolin.group2.dir1.cata1.util.excel.listener;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.bolin.group2.dir1.cata1.util.excel.dto.BaseImportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author
 * @since 2022/12/30 18:21
 */
@Slf4j
@AllArgsConstructor
public class ExcelHeadDataListener<T extends BaseImportDTO> extends AnalysisEventListener<T> {

    @Getter
    private Class<T> clazz;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        ExcelHeadProperty excelHeadProperty = new ExcelHeadProperty(context.readRowHolder(), clazz, Collections.emptyList());
        Map<Integer, Head> clazzHeadMap = excelHeadProperty.getHeadMap();
        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            Integer k = entry.getKey();
            String v = entry.getValue();

            Head head = clazzHeadMap.get(k);

            // 打印调试信息，便于你排查是哪一列报错
//            System.out.println("列索引: " + k);
//            System.out.println("Excel表头值: " + v);
            if (head != null) {
//                System.out.println("实体类注解值: " + head.getHeadNameList());
            } else {
                System.out.println("实体类中第" + k + "列找不到对应注解");
            }

            // 执行断言逻辑
            Assert.isTrue(
                    head != null &&
                            Objects.equals(
                                    head.getHeadNameList().stream().findFirst().orElse(null),
                                    v
                            ),
                    "导入模板不匹配，错误列索引：" + k + "，实际值：" + v
            );
        }


//        headMap.forEach((k, v) -> {
//            Head head = clazzHeadMap.get(k);
//            Assert.isTrue(Objects.nonNull(head)
//                    &&  Objects.equals(head.getHeadNameList().stream().findFirst().orElse(null), v), "导入模板不匹配");
//        });
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        int excelRowNum = context.readRowHolder().getRowIndex() + 1;
        data.setExcelRowNum(excelRowNum);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("parse excel done");
    }

}
