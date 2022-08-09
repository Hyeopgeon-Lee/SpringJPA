package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.UserInfoEntity;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {

    // RequiredArgsConstructor 어노테이션으로 생성자를 자동 생성함
    // userInfoRepository 변수에 이미 메모리에 올라간 UserInfoRepository 객체를 넣어줌
    // 예전에는 autowired 어노테이션를 통해 설정했었지만, 이젠 생성자를 통해 객체 주입함
    private final UserInfoRepository userInfoRepository;

    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        // 회원가입 성공 : 1, 아이디 중복으로인한 가입 취소 : 2, 기타 에러 발생 : 0
        int res = 0;

        String userId = CmmUtil.nvl(pDTO.getUserId()); // 아이디
        String userName = CmmUtil.nvl(pDTO.getUserName()); // 이름
        String password = CmmUtil.nvl(pDTO.getPassword()); // 비밀번호
        String email = CmmUtil.nvl(pDTO.getEmail()); // 이메일
        String addr1 = CmmUtil.nvl(pDTO.getAddr1()); // 주소
        String addr2 = CmmUtil.nvl(pDTO.getAddr2()); // 상세주소

        log.info("userId : " + userId);
        log.info("userName : " + userName);
        log.info("password : " + password);
        log.info("email : " + email);
        log.info("addr1 : " + addr1);
        log.info("addr2 : " + addr2);

        // 회원 가입 중복 방지를 위해 DB에서 데이터 조회
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        // 값이 존재한다면... (이미 회원가입된 아이디)
        if (rEntity.isPresent()) {
            res = 2;

        } else {

            // 회원가입을 위한 Entity 생성
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId).userName(userName).password(password).email(email)
                    .addr1(addr1).addr2(addr2)
                    .regId(userId).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .chgId(userId).chgDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .build();

            // 회원정보 DB에 저장
            userInfoRepository.save(pEntity);

            // JPA의 save함수는 데이터 값에 따라 등록, 수정을 수행함
            // 물론 잘 저장되겠지만, 내가 실행한 save 함수가 DB에 등록이 잘 수행되었는지 100% 확신이 불가능함
            // 회원 가입후, 혹시 저장 안될 수 있기에 조회 수행함
            // 회원 가입 중복 방지를 위해 DB에서 데이터 조회
            rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) { // 값이 존재한다면... (회원가입 성공)
                res = 1;

            } else { // 값이 없다면... (회원가입 실패)
                res = 0;

            }

        }

        return res;
    }

    /**
     * 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
     *
     * @param pDTO 로그인을 위한 회원아이디, 비밀번호
     * @return 로그인된 회원아이디 정보
     */
    @Override
    public int getUserLoginCheck(UserInfoDTO pDTO) throws Exception {

        // 로그인 성공 : 1, 실패 : 0
        int res = 0;

        String userId = CmmUtil.nvl(pDTO.getUserId()); // 아이디
        String password = CmmUtil.nvl(pDTO.getPassword()); // 비밀번호

        log.info("userId : " + userId);
        log.info("password : " + password);

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 mapper 호출하기
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;
        }

        return res;
    }
}
