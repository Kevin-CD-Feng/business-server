package com.xtxk.recognition.prepare.service.svc;

import com.xtxk.recognition.prepare.service.dto.loopAlg.SimilarityDto;

import java.util.Map;

public interface SimilarityService {
    void saveSimilarity(Integer targetId,String feature);
    SimilarityDto getTargetInfo(String feature);//targetId,coff

    void load();
}
