package com.cgg.redisstudy.service.impl;


import com.cgg.redisstudy.cache.RsCache;
import com.cgg.redisstudy.dao.ZkStudyRepository;
import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.entity.ZkStudy;
import com.cgg.redisstudy.exception.RsException;
import com.cgg.redisstudy.service.IRsService;
import com.cgg.redisstudy.utils.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RsServiceImpl implements IRsService {

    @Resource
    private ZkStudyRepository zkStudyRepository;

    @Resource
    private RsCache rsCache;

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RestResult<Object> setResource(String status, Long acTime, String name) {
        ZkStudy zkStudy = new ZkStudy();
        zkStudy.setId(UuidUtil.uuid());
        zkStudy.setName(name);
        zkStudy.setStatus(status);
        zkStudy.setActiveTime(new Date(acTime));
        zkStudyRepository.save(zkStudy);
        return RestResult.success();
    }

    @Override
    public RestResult<Object> getResource(String status) {
        List<ZkStudy> cacheResource = rsCache.getByKey("rs_study", ZkStudy.class);
        if (ObjectUtils.isEmpty(cacheResource)) {
            List<ZkStudy> list = zkStudyRepository.findAll();
            rsCache.store("rs_study", list);
            return RestResult.success(list);
        }
        return RestResult.success(cacheResource);
    }

    @Override
    public RestResult<Object> updateResource(String status, Long acTime, String name) {
        rsCache.delete("rs_study");
        int count = zkStudyRepository.updateResource(status, new Date(acTime), name);
        return RestResult.success(count);
    }


}
