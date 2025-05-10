package com.bolin.group2.dir1.cata1.util.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bolin.group2.dir1.cata1.util.excel.dto.BaseImportDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author freddy
 * @date 2022/11/2
 */
@Slf4j
public class ExcelRowNumListener<T extends BaseImportDTO> extends AnalysisEventListener<T> {

    @Getter
    private final List<T> result = new ArrayList<>();
    @Getter
    private Map<Integer, String> headMap;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        int excelRowNum = context.readRowHolder().getRowIndex() + 1;
        data.setExcelRowNum(excelRowNum);
        result.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("parse excel done");
    }
}
