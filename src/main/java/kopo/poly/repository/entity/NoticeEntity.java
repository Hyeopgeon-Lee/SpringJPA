package kopo.poly.repository.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Slf4j
@Builder
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_seq")
    private Long noticeSeq;

    @NonNull
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @NonNull
    @Column(name = "notice_yn", length = 1, nullable = false)
    private String noticeYn;

    @NonNull
    @Column(name = "contents", nullable = false)
    private String contents;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "read_cnt", nullable = false)
    private Long readCnt;

    @Column(name = "reg_id", updatable = false)
    private String regId;

    @Column(name = "reg_dt", updatable = false)
    private String regDt;

    @Column(name = "chg_id")
    private String chgId;

    @Column(name = "chg_dt")
    private String chgDt;

//    @Builder(builderMethodName = "updateBuilder")
//    public NoticeEntity(NoticeDTO dto, @Nullable boolean type) {
//
//        log.info("this.noticeSeq : " + this.noticeSeq);
//        log.info("this.title : " + this.title);
//        log.info("this.noticeYn : " + this.noticeYn);
//        log.info("this.contents : " + this.contents);
//        log.info("this.userId : " + this.userId);
//        log.info("this.readCnt : " + this.readCnt);
//        log.info("type: " + type);
//
//        this.noticeSeq = dto.getNoticeSeq();
//        this.title = CmmUtil.nvl(dto.getTitle());
//        this.noticeYn = CmmUtil.nvl(dto.getNoticeYn());
//        this.contents = CmmUtil.nvl(dto.getContents());
//        this.userId = CmmUtil.nvl(dto.getUserId());
//
//        log.info("@@this.noticeSeq : " + this.noticeSeq);
//        log.info("@@this.title : " + this.title);
//        log.info("@@this.noticeYn : " + this.noticeYn);
//        log.info("@@this.contents : " + this.contents);
//        log.info("@@this.userId : " + this.userId);
//        log.info("@@this.readCnt : " + this.readCnt);
//        log.info("@@type: " + type);

//        if (type) {
//            this.readCnt = 0L;
//
//        }

//}

}
