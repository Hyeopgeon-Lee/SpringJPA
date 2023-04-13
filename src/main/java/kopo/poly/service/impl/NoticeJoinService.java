package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.repository.NoticeJoinRepository;
import kopo.poly.repository.NoticeRepository;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.NoticeEntity;
import kopo.poly.repository.entity.NoticeJoinEntity;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.INoticeJoinService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeJoinService implements INoticeJoinService {

    private final NoticeRepository noticeRepository; // NativeQuery 사용을 위한 Repository

    private final NoticeJoinRepository noticeJoinRepository; // @JoinColumn 적용된 공지사항

    private final UserInfoRepository userInfoRepository; // 회원정보

    @Override
    public List<NoticeDTO> getNoticeListUsingJoinColumn() {
        log.info(this.getClass().getName() + ".getNoticeListUsingJoinColumn Start!");

        // 공지사항 전체 리스트 조회하기
        List<NoticeJoinEntity> rList = noticeJoinRepository.findAllByOrderByNoticeSeqDesc();

        List<NoticeDTO> list = new LinkedList<>(); // 조회 결과를 List<NoticeDTO> 변환하기 위해 사용

        rList.forEach(rEntity -> { // 람다식 사용

            NoticeDTO rDTO = new NoticeDTO(); // DTO에 저장하기 위해 메모리 올리기

            // Entity 결과를 DTO 저장하기위해 결과 변수를 담기
            long noticeSeq = rEntity.getNoticeSeq(); // 공지사항 순번 PK
            String noticeYn = CmmUtil.nvl(rEntity.getNoticeYn()); // 공지글 여부
            String title = CmmUtil.nvl(rEntity.getTitle()); // 공지사항 제목
            long readCnt = rEntity.getReadCnt(); // 공지사항 조회수
            String userName = CmmUtil.nvl(rEntity.getUserInfoEntity().getUserName()); // 공지사항 작성자(JoinColumn) 활용
            String regDt = CmmUtil.nvl(rEntity.getRegDt()); // 공지사항 작성일시

            // 로그 출력
            log.info("noticeSeq : " + noticeSeq);
            log.info("noticeYn : " + noticeYn);
            log.info("title : " + title);
            log.info("readCnt : " + readCnt);
            log.info("userName : " + userName);
            log.info("regDt : " + regDt);
            log.info("----------------------------");

            // Entity 결과를 DTO 저장하기
            rDTO.setNoticeSeq(noticeSeq); // 공지사항 순번
            rDTO.setNoticeYn(noticeYn); // 공지사항 여부
            rDTO.setTitle(title); // 제목
            rDTO.setReadCnt(readCnt); // 조회수
            rDTO.setUserName(userName); // 조인을 통해 가져온 작성자 이름
            rDTO.setRegDt(regDt); // 작성일

            list.add(rDTO); // 레코드마다 추가하기

            rDTO = null;

        });

        log.info(this.getClass().getName() + ".getNoticeListUsingJoinColumn End!");

        return list;
    }

    @Override
    public List<NoticeDTO> getNoticeListUsingEntity() {
        log.info(this.getClass().getName() + ".getNoticeListUsingEntity Start!");

        // 공지사항 전체 리스트 조회하기
        List<NoticeJoinEntity> rList = noticeJoinRepository.findAllByOrderByNoticeSeqDesc();

        List<NoticeDTO> list = new LinkedList<>();

        rList.forEach(rEntity -> { // 람다식 사용

            NoticeDTO rDTO = new NoticeDTO(); // DTO에 저장하기 위해 메모리 올리기

            // Entity 결과를 DTO 저장하기위해 결과 변수를 담기
            long noticeSeq = rEntity.getNoticeSeq(); // 공지사항 순번 PK
            String noticeYn = CmmUtil.nvl(rEntity.getNoticeYn()); // 공지글 여부
            String title = CmmUtil.nvl(rEntity.getTitle()); // 공지사항 제목
            long readCnt = rEntity.getReadCnt(); // 공지사항 조회수
            String userId = CmmUtil.nvl(rEntity.getUserId()); // 사용자 아이디
            String regDt = CmmUtil.nvl(rEntity.getRegDt()); // 공지사항 작성일시

            // 로그 출력
            log.info("noticeSeq : " + noticeSeq);
            log.info("noticeYn : " + noticeYn);
            log.info("title : " + title);
            log.info("readCnt : " + readCnt);
            log.info("userId : " + userId);
            log.info("regDt : " + regDt);
            log.info("----------------------------");

            // Entity 결과를 DTO 저장하기
            rDTO.setNoticeSeq(noticeSeq); // 공지사항 순번
            rDTO.setNoticeYn(noticeYn); // 공지사항 여부
            rDTO.setTitle(title); // 제목
            rDTO.setReadCnt(readCnt); // 조회수
            rDTO.setRegDt(regDt); // 작성일

            // 회원 이름 조회하기
            Optional<UserInfoEntity> userEntity = userInfoRepository.findByUserId(userId);

            if (userEntity.isPresent()) { // 회원 데이터가 존재한다면...
                rDTO.setUserName(CmmUtil.nvl(userEntity.get().getUserName()));

            }

            list.add(rDTO);

            rDTO = null;

        });

        log.info(this.getClass().getName() + ".getNoticeListUsingEntity End!");

        return list;
    }

    @Override
    public List<NoticeDTO> getNoticeListUsingNativeQuery() {
        log.info(this.getClass().getName() + ".getNoticeListUsingNativeQuery Start!");

        // 공지사항 전체 리스트 조회하기
        List<NoticeEntity> rList = noticeRepository.getNoticeListUsingSQL();

        // 엔티티의 값들을 DTO에 맞게 넣어주기
        List<NoticeDTO> nList = new ObjectMapper().convertValue(rList,
                new TypeReference<List<NoticeDTO>>() {
                });

        log.info(this.getClass().getName() + ".getNoticeListUsingNativeQuery End!");

        return nList;
    }

}
