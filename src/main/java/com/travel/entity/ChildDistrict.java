package com.travel.entity;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *  자식 지역 entity
 */

@Entity
@Getter
public class ChildDistrict {

    //자식 지역 idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childIdx;

    //부모 지역 idx
    private Long parentsIdx;

    //부모 지역 이름
    private String districtName;

    //사용여부
    private String useYn;

}
