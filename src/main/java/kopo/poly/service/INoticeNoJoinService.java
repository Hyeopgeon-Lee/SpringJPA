package kopo.poly.service;

import kopo.poly.dto.NoticeDTO;

import java.util.List;

public interface INoticeNoJoinService {

    /**
     * 공지사항 전체 가져오기
     */
    List<NoticeDTO> getNoticeList();

}
