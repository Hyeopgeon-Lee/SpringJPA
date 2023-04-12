package kopo.poly.service.impl;

import kopo.poly.dto.NoticeDTO;
import kopo.poly.repository.NoticeJoinRepository;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.NoticeJoinEntity;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.INoticeNoJoinService;
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
public class NoticeNoJoinService implements INoticeNoJoinService {

    private final NoticeJoinRepository noticeJoinRepository; // 공지사항

    private final UserInfoRepository userInfoRepository; // 회원정보

    @Override
    public List<NoticeDTO> getNoticeList() {

        log.info(this.getClass().getName() + ".getNoticeList Start!");

        // 공지사항 전체 리스트 조회하기
        List<NoticeJoinEntity> rList = noticeJoinRepository.findAllByOrderByNoticeSeqDesc();

        List<NoticeDTO> list = new LinkedList<>();

        for (NoticeJoinEntity rEntity : rList) {

            NoticeDTO rDTO = new NoticeDTO();

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
        }

        log.info(this.getClass().getName() + ".getNoticeList End!");

        return list;
    }
}
