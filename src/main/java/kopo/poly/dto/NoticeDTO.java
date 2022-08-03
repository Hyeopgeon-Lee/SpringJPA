package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * JPA를 사용하면, 반드시 Setter 함수는 생성하지 않는다.
 */
@Getter
@Setter
public class NoticeDTO {

    private Long noticeSeq; // 기본키, 순번
    private String title; // 제목
    private String noticeYn; // 공지글 여부
    private String contents; // 글 내용
    private String userId; // 작성자
    private String readCnt; // 조회수
    private String regId; // 등록자 아이디
    private String regDt; // 등록일
    private String chgId; // 수정자 아이디
    private String chgDt; // 수정일

    private String userName; // 등록자명


}
