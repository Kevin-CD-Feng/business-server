package com.xtxk.recognition.prepare.service.mapper;

import com.xtxk.recognition.prepare.service.dto.loopAlg.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoopAlgResMapper {
    List<LoopAlgResVo> queryloopRes(@Param("code") String code,@Param("eventCode") String eventCode, @Param("keyWords") String keyWords);
    Integer addLoopRes(@Param("entity") LoopAlgResReqDto loopAlgResReqDto);
    Integer delLoopRes(@Param("loopId") String loopId);
    List<LoopAlgResVo> querySingleloopRes(@Param("loopId") String loopId);

    Integer editLoopRes(@Param("entity")LoopAlgResReqDto loopAlgResReqDto);
    Integer swapLoopRes(@Param("entity") SwapLoopResReqVo swapLoopResReqVo);

    Integer delLoopResItem(@Param("loopId") String loopId);

    List<LoopAlgResVo> queryloopResByResId(@Param("resourceId") String resourceId);

    List<AlgLoopModelByConditionDto> queryAlgLoopModelByCondition(@Param("resourceId") String resourceId,@Param("loopId") String loopId);

    void insertfeature(@Param("list") List<FeatureDto> list);

    List<FeatureDto> selectfeatureAll();

    Integer queryMaxOrder(@Param("algCode")String algCode,@Param("eventCode")String eventCode);
}