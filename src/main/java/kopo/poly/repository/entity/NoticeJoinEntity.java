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
     *
     * @OneToOne => 1:1 조인으로 inner 조인과 유사함
     * name => NoticeJoinEntity 조인할 컬럼명 (즉, Notice 테이블의 user_id 컬럼과 User_info 테이블 조인)
     * UserInfoEntity, NoticeJoinEntity 둘다 동일한 이름의 user_id 사용하고 있어
     * 둘 중 하나는 데이터 값의 변화를 주지 않기 위해 insertable = false, updatable = false 설정함
     * insertable = false 컬럼에 등록 막기
     * updatable = false 컬럼에 수정 막기
     * <p>
     * 변수 타입에 UserInfoEntity 정의하면, 자동으로 User_info 테이블이 조인 대상이 됨
     * 외래키는 PK 값과 연결되는 구조이기에 user_info 테이블의 PK 컬럼인 user_id가 조인 대상이 됨
     * 즉, user_info 테이블의 user_id 컬럼과 notice 테이블의 user_id 컬럼이 조인됨
     * <p>
     * 만약, user_info 테이블의 PK 컬럼이 아닌 user_name과 같이 다른 컬럼 사용하고 싶은 경우
     * referencedColumnName = "user_name" 사용하고, UserInfoEntity는 implements Serializable 구현함
     */
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserInfoEntity userInfoEntity;

}
