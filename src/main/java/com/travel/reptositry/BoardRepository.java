package com.travel.reptositry;

import com.travel.controller.MainController;
import com.travel.entity.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {

    @Query(value =
            "SELECT \n" +
                    "  T1.BOARD_IDX\n" +
                    "  ,T1.BOARD_TITLE\n" +
                    " ,T1.BOARD_CONTENT\n" +
                    " ,T1.PARENTS_IDX\n" +
                    " ,T1.CHILD_IDX\n" +
                    " ,T1.HASH_TAG\n" +
                    " ,T1.REG_DATE\n" +
                    " ,T1.UPT_DATE\n" +
                    " ,T1.USE_YN\n" +
                    " ,(SELECT GROUP_CONCAT( T2.FILE_IDX SEPARATOR ',' ) FROM board_file T2 WHERE T2.BOARD_IDX = T1.BOARD_IDX AND T2.USE_YN = 'Y' ) AS FILE_IDXS \n" +
                    " ,(SELECT COUNT(*) FROM board_file T3 WHERE T3.BOARD_IDX = T1.BOARD_IDX AND T3.USE_YN = 'Y') AS ATTACH_COUNT \n" +
                    "FROM \n" +
                    " BOARD T1\n" +
                    "WHERE\n" +
                    "T1.USE_YN = 'Y'\n" +
                    "ORDER BY T1.REG_DATE ASC", nativeQuery = true
    )
    List<Board> findAllByStoryList();

    @Query(value =
            "SELECT \n" +
                    "  T1.BOARD_IDX\n" +
                    "  ,T1.BOARD_TITLE\n" +
                    " ,T1.BOARD_CONTENT\n" +
                    " ,T1.PARENTS_IDX\n" +
                    " ,T1.CHILD_IDX\n" +
                    " ,T1.HASH_TAG\n" +
                    " ,T1.REG_DATE\n" +
                    " ,T1.UPT_DATE\n" +
                    " ,T1.USE_YN\n" +
                    " ,(SELECT GROUP_CONCAT( T2.FILE_IDX SEPARATOR ',' ) FROM board_file T2 WHERE T2.BOARD_IDX = T1.BOARD_IDX AND T2.USE_YN = 'Y' ) AS FILE_IDXS \n" +
                    " ,(SELECT COUNT(*) FROM board_file T3 WHERE T3.BOARD_IDX = T1.BOARD_IDX AND T3.USE_YN = 'Y') AS ATTACH_COUNT \n" +
                    "FROM \n" +
                    " board T1\n" +
                    "WHERE\n" +
                    "T1.USE_YN = 'Y'\n" +
                    " AND T1.PARENTS_IDX = :parentsIdx\n" +
                    "ORDER BY T1.REG_DATE DESC", nativeQuery = true
    )
    List<Board> findAllByParentsIdx(String parentsIdx);

    @Query(value =
            "SELECT \n" +
                    "  T1.BOARD_IDX\n" +
                    "  ,T1.BOARD_TITLE\n" +
                    " ,T1.BOARD_CONTENT\n" +
                    " ,T1.PARENTS_IDX\n" +
                    " ,T1.CHILD_IDX\n" +
                    " ,T1.HASH_TAG\n" +
                    " ,T1.REG_DATE\n" +
                    " ,T1.UPT_DATE\n" +
                    " ,T1.USE_YN\n" +
                    " ,(SELECT GROUP_CONCAT( T2.FILE_IDX SEPARATOR ',' ) FROM board_file T2 WHERE T2.BOARD_IDX = T1.BOARD_IDX AND T2.USE_YN = 'Y' ) AS FILE_IDXS \n" +
                    " ,(SELECT COUNT(*) FROM board_file T3 WHERE T3.BOARD_IDX = T1.BOARD_IDX AND T3.USE_YN = 'Y') AS ATTACH_COUNT \n" +
                    "FROM \n" +
                    " board T1\n" +
                    "WHERE\n" +
                    "T1.USE_YN = 'Y'\n" +
                    " AND T1.PARENTS_IDX = :parentsIdx\n" +
                    " AND T1.CHILD_IDX = :childIdx\n" +
                    "ORDER BY T1.REG_DATE DESC", nativeQuery = true
    )
    List<Board> findAllByParentsIdxAndChildIdx(String parentsIdx, String childIdx);

    Board findByBoardIdx(Long boardIdx);
}
