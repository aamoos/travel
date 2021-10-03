package com.travel.reptositry;

import com.travel.entity.BoardFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoardFileRepository extends CrudRepository<BoardFile, Long> {

    @Query(value =
                    "SELECT" +
                    " T2.FILE_IDX"+
                    " ,T1.BOARD_IDX" +
                    " ,T1.USE_YN" +
                    " ,T1.BOARD_FILE_IDX" +
                    " FROM" +
                    " BOARD_FILE T1" +
                    " ,FILE T2" +
                    " ,BOARD T3"+
                    " WHERE" +
                    " T1.FILE_IDX = T2.FILE_IDX" +
                    " AND T1.BOARD_IDX = T3.BOARD_IDX"+
                    " AND T1.USE_YN = 'Y'" +
                    " AND T3.USE_YN = 'Y'" +
                    " ORDER BY T2.REG_DATE DESC", nativeQuery = true
    )
    List<BoardFile> findAllByGalleryList();

    @Query(value =
            "SELECT" +
                    " T2.FILE_IDX"+
                    " ,T1.BOARD_IDX" +
                    " ,T1.USE_YN" +
                    " ,T3.PARENTS_IDX" +
                    " ,T3.CHILD_IDX" +
                    " ,T3.BOARD_TITLE" +
                    " ,T3.BOARD_CONTENT" +
                    " ,T1.BOARD_FILE_IDX" +
                    " FROM" +
                    " BOARD_FILE T1" +
                    " ,FILE T2" +
                    " ,BOARD T3"+
                    " WHERE" +
                    " T1.FILE_IDX = T2.FILE_IDX" +
                    " AND T1.BOARD_IDX = T3.BOARD_IDX"+
                    " AND T1.USE_YN = 'Y'" +
                    " AND T3.USE_YN = 'Y'" +
                    " AND T3.PARENTS_IDX = :parentsIdx" +
                    " ORDER BY T2.REG_DATE DESC", nativeQuery = true
    )
    List<BoardFile> findAllByParentsIdx(String parentsIdx);

    @Query(value =
            "SELECT" +
                    " T2.FILE_IDX"+
                    " ,T1.BOARD_IDX" +
                    " ,T1.USE_YN" +
                    " ,T3.PARENTS_IDX" +
                    " ,T3.CHILD_IDX" +
                    " ,T3.BOARD_TITLE" +
                    " ,T3.BOARD_CONTENT" +
                    " ,T1.BOARD_FILE_IDX" +
                    " FROM" +
                    " BOARD_FILE T1" +
                    " ,FILE T2" +
                    " ,BOARD T3"+
                    " WHERE" +
                    " T1.FILE_IDX = T2.FILE_IDX" +
                    " AND T1.BOARD_IDX = T3.BOARD_IDX"+
                    " AND T1.USE_YN = 'Y'" +
                    " AND T3.USE_YN = 'Y'" +
                    " AND T3.PARENTS_IDX = :parentsIdx" +
                    " AND T3.CHILD_IDX = :childIdx" +
                    " ORDER BY T2.REG_DATE DESC", nativeQuery = true
    )
    List<BoardFile> findAllByParentsIdxAndChildIdx(String parentsIdx, String childIdx);

    List<BoardFile> findByBoardIdxAndUseYn(Long boardIdx, String useYn);
}
