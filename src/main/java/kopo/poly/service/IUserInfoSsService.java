package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserInfoSsService extends UserDetailsService {

    // 회원 가입하기(회원정보 등록하기)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

}
