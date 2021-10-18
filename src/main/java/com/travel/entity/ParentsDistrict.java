package com.travel.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *  부모 지역 entity
 */

@Entity
@Getter
public class ParentsDistrict {

    //부모 지역 idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentsIdx;

    //부모 지역 이름
    private String districtName;

    //사용여부
    private String useYn;

}
