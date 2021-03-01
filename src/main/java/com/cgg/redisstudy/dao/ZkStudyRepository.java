package com.cgg.redisstudy.dao;

import com.cgg.redisstudy.entity.ZkStudy;
import com.cgg.redisstudy.exception.RsException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author cgg
 */

@Repository
public interface ZkStudyRepository extends JpaRepository<ZkStudy, Long> {

    @Modifying
    @Query(value = "update zk_study z set z.active_time = ?2, z.name = ?3 where z.status = ?1", nativeQuery = true)
    int updateResource(String status, Date acTime, String name);

}
