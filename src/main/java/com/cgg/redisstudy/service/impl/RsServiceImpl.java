package com.cgg.redisstudy.service.impl;


import com.cgg.redisstudy.cache.RsCache;
import com.cgg.redisstudy.dao.ZkStudyRepository;
import com.cgg.redisstudy.dto.RestResult;
import com.cgg.redisstudy.entity.ZkStudy;
import com.cgg.redisstudy.exception.RsException;
import com.cgg.redisstudy.service.IRsService;
import com.cgg.redisstudy.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RsServiceImpl implements IRsService {

    @Resource
    private ZkStudyRepository zkStudyRepository;

    @Resource
    private RsCache rsCache;

    @Resource
    private Environment environment;

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
    public RestResult<Object> getResource(String status, Long userId) {
        log.info("start search resource");
        List<ZkStudy> cacheResource = rsCache.getByKey("rs_study", ZkStudy.class);
        //使用bitmaps统计接口操作日活
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        rsCache.setBitMaps(simpleDateFormat.format(new Date()), userId, true);
        System.out.println("今天的日活量：" + rsCache.activeCountOfDay(simpleDateFormat.format(new Date())));
        System.out.println("七天的总日活量：" + rsCache.activeCountOfPeriodTime(7));
        System.out.println("连续七天登录的用户数：" + rsCache.activeUserCountOfPeriodTime(7));
        if (ObjectUtils.isEmpty(cacheResource)) {
            List<ZkStudy> list = zkStudyRepository.findAll();
            rsCache.store("rs_study", list);
            return RestResult.success(list);
        }
        return RestResult.success(cacheResource);
    }

    @Override
    public RestResult<Object> updateResource(String status, Long acTime, String name) {
        int count = zkStudyRepository.updateResource(status, new Date(acTime), name);
        rsCache.delete("rs_study");
        return RestResult.success(count);
    }

    @Override
    public RestResult<Object> getSetResource(String status) {
        log.info("start search set resource");
        List<ZkStudy> cacheResource = rsCache.getByKey("rs_study", ZkStudy.class);
        if (ObjectUtils.isEmpty(cacheResource)) {
            List<ZkStudy> list = zkStudyRepository.findAll();
            rsCache.store("rs_study", list);
            return RestResult.success(list);
        }
        return RestResult.success(cacheResource);

    }


}
