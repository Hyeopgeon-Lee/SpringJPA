package kopo.poly.repository;

import kopo.poly.repository.entity.NoticeJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeJoinRepository extends JpaRepository<NoticeJoinEntity, Long> {

    List<NoticeJoinEntity> findAllByOrderByNoticeSeqDesc();

}
