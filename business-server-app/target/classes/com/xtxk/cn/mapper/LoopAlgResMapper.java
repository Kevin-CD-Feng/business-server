package com.xtxk.cn.mapper;

import com.xtxk.cn.dto.loopAlg.LoopAlgResReqDto;
import com.xtxk.cn.dto.loopAlg.LoopAlgResRspDto;
import com.xtxk.cn.dto.loopAlg.LoopAlgResVo;
import com.xtxk.cn.dto.loopAlg.SwapLoopResReqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoopAlgResMapper {
    List<LoopAlgResVo> queryloopRes(@Param("code") String code, @Param("keyWords") String keyWords);
    Integer addLoopRes(@Param("entity")LoopAlgResReqDto loopAlgResReqDto);
    Integer delLoopRes(@Param("loopId") String loopId);
    List<LoopAlgResVo> querySingleloopRes(@Param("loopId") String loopId);

    Integer editLoopRes(@Param("entity")LoopAlgResReqDto loopAlgResReqDto);
    Integer swapLoopRes(@Param("entity")SwapLoopResReqVo swapLoopResReqVo);

    Integer delLoopResItem(@Param("loopId") String loopId);
}