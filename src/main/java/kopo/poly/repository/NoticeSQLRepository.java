package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeSQLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeSQLRepository extends JpaRepository<NoticeSQLEntity, Long> {

    @Query(value = "SELECT A.NOTICE_SEQ, A.NOTICE_YN, A.TITLE, A.CONTENTS, A.USER_ID, A.READ_CNT, " +
            "A.REG_ID, A.REG_DT, A.CHG_ID, A.CHG_DT, B.USER_NAME " +
            "FROM NOTICE A INNER JOIN USER_INFO B ON A.USER_ID = B.USER_ID " +
            "ORDER BY NOTICE_SEQ DESC",
            nativeQuery = true)
    List<NoticeSQLEntity> getNoticeListUsingSQL();

}
