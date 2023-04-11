package kopo.poly.repository.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Builder
@Cacheable
@Entity
public class NoticeJoinEntity {

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

    /**
     * 단순 컬럼 기반 조인
     * UserInfoEntity, NoticeJoinEntity 둘다 동일한 이름의 user_id 사용하고 있어
     * 둘 중 하나는 데이터 값의 변화를 주지 않기 위해 insertable = false, updatable = false 설정함
     *
     * @OneToOne = > 1:1 조인으로 inner 조인과 유사함
     */
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserInfoEntity userInfoEntity;

}
