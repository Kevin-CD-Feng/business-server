package com.xtxk.cn.service.impl.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xtxk.cn.entity.PersonInfo;
import com.xtxk.cn.enums.ErrorCode;
import com.xtxk.cn.exception.ServiceException;
import com.xtxk.cn.service.user.PersonInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author HW
 */
@Component
public class PersonInfoListener extends AnalysisEventListener<PersonInfo> {

    private static final int BATCH_COUNT = 200;
    List<PersonInfo> list = new ArrayList<>();

    private PersonInfoService personInfoService;

    public PersonInfoListener(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    /**
     * 模板校验
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        int count = 0;
        // 获取数据实体的字段列表
        Field[] fields = PersonInfo.class.getDeclaredFields();
        // 遍历字段进行判断
        for (Field field : fields) {
            // 获取当前字段上的ExcelProperty注解信息
            ExcelProperty fieldAnnotation = field.getAnnotation(ExcelProperty.class);
            // 判断当前字段上是否存在ExcelProperty注解
            if (fieldAnnotation != null) {
                ++count;
                // 存在ExcelProperty注解则根据注解的index索引到表头中获取对应的表头名
                String headName = headMap.get(fieldAnnotation.index());
                // 判断表头是否为空或是否和当前字段设置的表头名不相同
                if (StringUtils.isEmpty(headName) || !headName.equals(fieldAnnotation.value()[0])) {
                    // 如果为空或不相同，则抛出异常不再往下执行
                    throw new ServiceException(ErrorCode.EXCEL_MODE_ERROR);
                }
            }
        }

        // 判断用户导入表格的标题头是否完全符合模板
        if (count != headMap.size()) {
            throw new ServiceException(ErrorCode.EXCEL_MODE_ERROR);
        }
    }

    @Override
    public void invoke(PersonInfo personInfo, AnalysisContext analysisContext) {
        list.add(personInfo);
        if (list.size() >= BATCH_COUNT) {
            personInfoService.insertBatch(list);
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        personInfoService.insertBatch(list);
        list.clear();
    }

}
