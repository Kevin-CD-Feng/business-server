package com.xtxk.recognition.prepare.service.svc;

import cn.hutool.core.util.StrUtil;
import com.xtxk.recognition.prepare.service.algAction.AlgLoopModel;
import com.xtxk.recognition.prepare.service.algAction.AlgModel;
import com.xtxk.recognition.prepare.service.dto.loopAlg.AlgLoopModelByConditionDto;
import com.xtxk.recognition.prepare.service.entity.DicItem;
import com.xtxk.recognition.prepare.service.mapper.DicItemMapper;
import com.xtxk.recognition.prepare.service.mapper.LoopAlgResMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AlgDispatcherService {
    @Autowired
    private LoopAlgResMapper loopAlgResMapper;

    @Autowired
    private DicItemMapper dicItemMapper;

    @Value("${scheduling.alarmWithFace}")
    private String hasEnableFace;

    public HashMap<String, List<AlgLoopModel>>  queryAlgLoopModelByCondition(String resourceId,String loopId){
        List<AlgLoopModelByConditionDto> algLoopModelByConditionDtos = this.loopAlgResMapper.queryAlgLoopModelByCondition(resourceId, loopId);
        return this.getStringListHashMap(algLoopModelByConditionDtos);
    }

    private boolean isFaceWith(String eventCode){
        String[] split = this.hasEnableFace.split(",");
        Optional<String> first = Stream.of(split).filter(it -> it.equals(eventCode)).findFirst();
        if(first.isPresent()){
            return true;
        }
        return false;
    }

    private HashMap<String, List<AlgLoopModel>> getStringListHashMap(List<AlgLoopModelByConditionDto> algLoopModelByConditionDtos) {
        Map<String, List<AlgLoopModelByConditionDto>> bucketDtos = algLoopModelByConditionDtos.stream().collect(Collectors.groupingBy(it -> {
            return it.getAlgCode() + "_" + it.getEventCode()+"_"+ it.getAlgOrder();
        }));
        HashMap<String,List<AlgModel>> modelsHash = new HashMap<>();//algCode_eventCode_algOrder
        List<DicItem> algorithm_name = dicItemMapper.queryDicItemListByDicCode("algorithm_name");
        Map<String, DicItem> algorithmMap = algorithm_name.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        bucketDtos.forEach((k,v)->{
            if(!modelsHash.containsKey(k)){
                modelsHash.put(k,new ArrayList<>());
            }
            List<AlgModel> algLoopModels = modelsHash.get(k);
            v.forEach(item->{
                AlgModel algModel = AlgModel.builder().build();
                algModel.setAlgCode(item.getAlgCode());
                algModel.setEventCode(item.getEventCode());
                algModel.setResourceId(item.getResourceId());
                algModel.setResourceName(item.getResourceName());
                algModel.setWebCoordinate(item.getWebCoordinate());
                algModel.setFrameInterval(item.getFrameInterval());
                algModel.setOrder(item.getAlgOrder());
                algModel.setLoopTime(item.getLoopTime());
                algModel.setResourceSipId(item.getResourceSipId());
                DicItem dicItem = algorithmMap.get(item.getAlgCode());
                algModel.setAlgorithmName(dicItem.getItemName());
                algModel.setIsEnableFace(isFaceWith(item.getEventCode()));


                Integer violationDuration = Optional.ofNullable(item.getViolationDuration()).orElse(0);
                Integer violationInterval = Optional.ofNullable(item.getViolationInterval()).orElse(0);

                algModel.setDuration(violationDuration);
                algModel.setInterval(violationInterval);
                algLoopModels.add(algModel);
            });
            modelsHash.put(k,algLoopModels);
        });

        //构成AlgLoopModel
        HashMap<String,List<AlgLoopModel>> line = new HashMap<>();
        modelsHash.forEach((k,v)->{
            String bucketName = StrUtil.subBefore(k,"_",true);
            if(!line.containsKey(bucketName)){
                line.put(bucketName,new ArrayList<>());
            }
            List<AlgLoopModel> algLoopModels = line.get(bucketName);
            Optional<AlgModel> first = v.stream().findFirst();
            if(first.isPresent()){
                AlgLoopModel algLoopModel = new AlgLoopModel();
                algLoopModel.setContext(k);
                //数据库中loopTime为小时,转换为秒
                algLoopModel.setOrder(first.get().getOrder());
                algLoopModel.init((int) (first.get().getLoopTime()*3600),k,v);
                algLoopModels.add(algLoopModel);
            }
        });
        return line;
    }
}