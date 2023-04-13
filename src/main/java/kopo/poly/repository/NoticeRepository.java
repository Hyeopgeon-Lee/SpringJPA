package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAllByOrderByNoticeSeqDesc();

    NoticeEntity findByNoticeSeq(Long noticeSeq);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE NOTICE A SET A.READ_CNT = IFNULL(A.READ_CNT, 0) + 1 WHERE A.NOTICE_SEQ = :noticeSeq",
            nativeQuery = true)
    int updateReadCnt(@Param("noticeSeq") Long noticeSeq);

    @Query(value = "SELECT A.NOTICE_SEQ, A.NOTICE_YN, A.TITLE, A.CONTENTS, A.USER_ID, A.READ_CNT, " +
            "A.REG_ID, A.REG_DT, A.CHG_ID, A.CHG_DT, B.USER_NAME " +
            "FROM NOTICE A INNER JOIN USER_INFO B ON A.USER_ID = B.USER_ID " +
            "ORDER BY NOTICE_SEQ DESC",
            nativeQuery = true)
    List<NoticeEntity> getNoticeListUsingSQL();

}
