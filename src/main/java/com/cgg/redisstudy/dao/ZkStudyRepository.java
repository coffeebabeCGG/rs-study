package com.cgg.redisstudy.dao;

import com.cgg.redisstudy.entity.ZkStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cgg
 */

@Repository
public interface ZkStudyRepository extends JpaRepository<ZkStudy, Long> {


}
