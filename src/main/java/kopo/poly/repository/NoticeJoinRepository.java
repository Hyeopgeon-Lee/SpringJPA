package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeEntity;
import kopo.poly.repository.entity.NoticeJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeJoinRepository extends JpaRepository<NoticeJoinEntity, Long> {

    List<NoticeJoinEntity> findAllByOrderByNoticeSeqDesc();

    @Query(value = "SELECT A.NOTICE_SEQ, A.NOTICE_YN, A.TITLE, A.CONTENTS, B.USER_NAME, A.REG_DT " +
            "FROM NOTICE A INNER JOIN USER_INFO B ON A.USER_ID = B.USER_ID",
            nativeQuery = true)
    List<NoticeEntity> getNoticeListUsingSQL();
}
