package com.xtxk.cn.third.service.impl;

import com.xtxk.cn.third.exception.ServiceException;
import com.xtxk.cn.third.enums.ErrorCode;
import com.xtxk.cn.third.service.BatchService;
import com.xtxk.cn.third.util.SpringBeanFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.util.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-08-23 11:53
 */
@Service
public class BatchServiceImpl implements BatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImpl.class);

    /***
     * mybatis 批量保存数据
     * @param list
     * @param clazz
     * @param biConsumer
     * @return
     */
    @Override
    public <M, T> boolean batchSave(List<T> list, Class<M> clazz, BiConsumer<M, T> biConsumer) {
        if (list == null || list.size() == 0) {
            logger.info("批量插入数据 list 数据为空! ");
            return false;
        }
        SqlSessionFactory sqlSessionFactory = SpringBeanFactory.getBean(SqlSessionFactory.class);
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            M mapper = session.getMapper(clazz);
            list.forEach(a -> biConsumer.accept(mapper, a));
            session.commit();
            session.clearCache();
        } catch (Exception e) {
            logger.error(" 批量插入数据异常:" + clazz.getName() + " error :" + e.getMessage());
            session.rollback();
            throw new ServiceException(ErrorCode.ERROR, e.getMessage());
        } finally {
            session.close();
        }
        return true;
    }
}
