package com.xtxk.cn.service.impl.car;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xtxk.cn.dto.carInfo.ResourceCntWithTypeDto;
import com.xtxk.cn.dto.carInfo.SearchCarDTO;
import com.xtxk.cn.dto.personInfo.VioModelCntDto;
import com.xtxk.cn.entity.CarInfo;
import com.xtxk.cn.entity.DicItem;
import com.xtxk.cn.mapper.CarInfoMapper;
import com.xtxk.cn.mapper.PersonInfoMapper;
import com.xtxk.cn.service.car.CarInfoService;
import com.xtxk.cn.service.impl.dic.DicItemHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Autowired
    private DicItemHandler dicItemHandler;

    @Override
    public PageInfo findByPage(SearchCarDTO searchCarDTO) {
        PageHelper.startPage(searchCarDTO.getPageNo(), searchCarDTO.getPageSize());
        List<CarInfo> list = carInfoMapper.findByPage(searchCarDTO);
        List<String> vioIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
            list.forEach(e -> {
                e.setCarTypeName(mapping.get(e.getCarType()));
                vioIds.add(e.getCarNumber());
            });
        }
        if(vioIds.size()>0) {
            List<VioModelCntDto> vioModelCntDtos = this.personInfoMapper.queryVioCnt(2, vioIds);
            Map<String, VioModelCntDto> collect1 = vioModelCntDtos.stream().collect(Collectors.toMap(VioModelCntDto::getModelId, it -> it));
            list.forEach(e->{
                if(collect1.containsKey(String.valueOf(e.getCarNumber()))){
                    e.setVioCnt(collect1.get(e.getCarNumber()).getCnt());
                }
            });
        }
        return new PageInfo(list);
    }

    @Override
    public Boolean saveCarInfo(CarInfo carInfo) {
        carInfo.setCarNumber(carInfo.getCarNumber().toUpperCase());
        if (carInfo.getCarId() != null) {
            carInfo.setUpdateTime(new Date());
            return carInfoMapper.update(carInfo) > 0;
        } else {
            carInfo.setUpdateTime(new Date());
            carInfo.setCreateTime(new Date());
            return carInfoMapper.insert(carInfo) > 0;
        }
    }

    @Override
    public Boolean deleteById(Integer id) {
        return carInfoMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean insertBatch(List<CarInfo> list) {
        Map<String, String> mapping = dicItemHandler.getDicItemCodeMapping();
        Map<String, String> tmpMapping = new HashMap<>();
        Set<Map.Entry<String, String>> entries = mapping.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            tmpMapping.put(entry.getValue(), entry.getKey());
        }
        for (CarInfo info : list) {
            info.setUpdateTime(new Date());
            info.setCreateTime(new Date());
            info.setCarType(tmpMapping.get(info.getCarTypeName()));
        }
        return carInfoMapper.insertBatch(list) > 0;
    }

    @Override
    public Boolean deleteBatch(String ids) {
        List<Integer> list = Stream.of(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return carInfoMapper.deleteBatch(list) > 0;
    }

    @Override
    public List<ResourceCntWithTypeDto> queryCarCntWithType() {
        List<ResourceCntWithTypeDto> resourceCntWithTypeDtos = this.carInfoMapper.queryCarCntWithType();
        List<DicItem> car_type = this.dicItemHandler.getAllDicItemByDicCode("car_type");
        Map<String, DicItem> collect = car_type.stream().collect(Collectors.toMap(DicItem::getItemCode, it -> it));
        List<String> exists = new ArrayList<>();
        resourceCntWithTypeDtos.forEach(it->{
            if(collect.containsKey(it.getCategory())){
                DicItem dicItem = collect.get(it.getCategory());
                it.setCategoryDesc(dicItem.getItemName());
                exists.add(it.getCategory());
            }
        });

        collect.forEach((k,v)->{
            if(!exists.contains(k)){
                ResourceCntWithTypeDto dto = new ResourceCntWithTypeDto();
                dto.setCnt(0);
                dto.setCategory(v.getItemCode());
                dto.setCategoryDesc(v.getItemName());
                resourceCntWithTypeDtos.add(dto);
            }
        });
        return resourceCntWithTypeDtos;
    }


}
