package com.travel.reptositry;

import com.travel.entity.ParentsDistrict;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParentsDistrictRepository extends CrudRepository<ParentsDistrict, Long> {
    List<ParentsDistrict> findByUseYn(String useYn);
}
