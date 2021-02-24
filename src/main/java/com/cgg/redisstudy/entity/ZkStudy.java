package com.cgg.redisstudy.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class ZkStudy implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "active_time")
    private Date activeTime;

    @Column(name = "status")
    private String status;
}
