package com.xtxk.recognition.prepare.service.svc.impl;

import com.xtxk.recognition.prepare.service.common.FloatUtil;
import com.xtxk.recognition.prepare.service.dto.loopAlg.FeatureDto;
import com.xtxk.recognition.prepare.service.dto.loopAlg.SimilarityDto;
import com.xtxk.recognition.prepare.service.mapper.LoopAlgResMapper;
import com.xtxk.recognition.prepare.service.svc.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class SimilarityServiceImpl implements SimilarityService, CommandLineRunner {

    private ConcurrentHashMap<Integer,String> features;

    @Autowired
    private LoopAlgResMapper loopAlgResMapper;

    @Override
    public void saveSimilarity(Integer targetId, String feature) {
        if(!this.features.containsKey(targetId)){
            this.features.put(targetId,feature);
            List<FeatureDto> dtos = new ArrayList<>();
            FeatureDto dto = new FeatureDto();
            dto.setPersonId(targetId);
            dto.setFeature(feature);
            dtos.add(dto);
            this.loopAlgResMapper.insertfeature(dtos);
        }
    }

    @Override
    public SimilarityDto getTargetInfo(String feature) {
        SimilarityDto dto = new SimilarityDto();
        this.features.forEach((k,v)->{
            double[] expect = FloatUtil.hexStr2Feature(v);
            double[]  input =FloatUtil.hexStr2Feature(feature);
            try{
                double similarity2 = FloatUtil.getSimilarity2(input, expect);
                dto.setPersonId(k);
                dto.setCoff(similarity2);
            }catch (Exception exception){

            }
        });
       return dto;
    }

    @Override
    public void load() {
        this.features = new ConcurrentHashMap<>();
        List<FeatureDto> featureDtos = this.loopAlgResMapper.selectfeatureAll();
        featureDtos.forEach(it->{
            features.put(it.getPersonId(),it.getFeature());
        });
    }

    @Override
    public void run(String... args) throws Exception {
        this.load();
    }
}