package com.cgg.redisstudy.service.impl;


import com.cgg.redisstudy.cache.RsCache;
import com.cgg.redisstudy.dao.ZkStudyRepository;
import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.entity.ZkStudy;
import com.cgg.redisstudy.service.IRsService;
import com.cgg.redisstudy.utils.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class RsServiceImpl implements IRsService {

    @Resource
    private ZkStudyRepository zkStudyRepository;

    @Resource
    private RsCache rsCache;

    @Override
    @Transactional()
    public RestResult<Object> setResource(String status, Long acTime, String name) {
        ZkStudy zkStudy = new ZkStudy();
        zkStudy.setId(UuidUtil.uuid());
        zkStudy.setName(name);
        zkStudy.setStatus(status);
        zkStudy.setActiveTime(new Date(acTime));
        zkStudyRepository.save(zkStudy);
        if (status.equals("2000")) {
            rsCache.store("zk_study", zkStudy);
        }
        return RestResult.success();
    }

    @Override
    public RestResult<Object> getResource(String status) {
        return null;
    }


}
