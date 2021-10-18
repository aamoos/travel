package com.travel.reptositry;

import com.travel.entity.ChildDistrict;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChildDistrictRepository extends CrudRepository<ChildDistrict, Long> {
    List<ChildDistrict> findByParentsIdxAndUseYn(Long parentsIdx, String useYn);
}
