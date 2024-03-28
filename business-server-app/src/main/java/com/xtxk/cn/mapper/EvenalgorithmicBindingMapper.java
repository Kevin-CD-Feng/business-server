package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.EvenalgorithmicBinding.PageAlgorithmicBindingReqDto;
import com.xtxk.cn.dto.algorithmicModel.PageAlgorithmicModelReqDto;
import com.xtxk.cn.entity.EvenalgorithmicBinding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface EvenalgorithmicBindingMapper {

    /**
     * 新增
     * @param evenalgorithmicBinding
     * @return
     */
    int insert(EvenalgorithmicBinding evenalgorithmicBinding);

    /**
     * 列表
     * @param pageAlgorithmicBindingReqDto
     * @return
     */
    List<EvenalgorithmicBinding> queryPageList(@Param("param") PageAlgorithmicBindingReqDto pageAlgorithmicBindingReqDto);

    /**
     * 详情
     * @param id
     * @return
     */
    EvenalgorithmicBinding queryById(@Param("id") Integer id);

    /**
     * 更新
     * @param update
     * @return
     */
    int update(@Param("param") EvenalgorithmicBinding update);

    /**
     * 删除
     * @param id
     * @param updateTime
     * @param updateUser
     * @return
     */
    int delete(@Param("ids") Integer[] id, @Param("updateTime") Date updateTime, @Param("updateUser") String updateUser);

    /**
     * 校验事件绑定算法是否存在
     * @param eventCode
     * @param algorithmicNameCode
     * @return
     */
    EvenalgorithmicBinding checkExist(@Param("eventCode")String eventCode,@Param("algorithmicNameCode")String algorithmicNameCode);

    /**
     * 根据事件code查询算法code
     * @param eventCode
     * @return
     */
    String queryAlgorithmicCodeByEventCode(@Param("eventCode")String eventCode);
}