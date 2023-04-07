package kopo.poly.controller;

import kopo.poly.auth.AuthInfo;
import kopo.poly.auth.UserRole;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoSsService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/ss")
@RequiredArgsConstructor
@Controller
public class UserInfoSsController {

    // 생성자를 통해 스프링 시작할 때, 메모리에 저장된 객체를 데이터타입에 맞춰 저장하기
    // @Service 어노테이션을 통해 스프링 시작할 때, IUserInfoService 객체가 메모리에 저장됨
    private final IUserInfoSsService userInfoSsService;

    // Spring Security에서 제공하는 비밀번호 암호화 객체(해시 함수)
    private final PasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 화면으로 이동
     */
    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm Start!");

        log.info(this.getClass().getName() + ".user/userRegForm End!");

        return "ss/userRegForm";
    }

    /**
     * 회원 가입 전 아이디 중복체크하기(Ajax를 통해 입력한 아이디 정보 받음)
     */
    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        String userId = CmmUtil.nvl(request.getParameter("userId")); // 회원아이디

        log.info("userId : " + userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);

        // 회원아이디를 통해 중복된 아이디인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoSsService.getUserIdExists(pDTO)).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }

    /**
     * 회원가입 로직 처리
     */
    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");

        int res = 0; // 회원가입 결과
        String msg = ""; //회원가입 결과에 대한 메시지를 전달할 변수
        MsgDTO dto = null; // 결과 메시지 구조

        //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {

            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String user_name = CmmUtil.nvl(request.getParameter("user_name")); //이름
            String password = CmmUtil.nvl(request.getParameter("password")); //비밀번호
            String email = CmmUtil.nvl(request.getParameter("email")); //이메일
            String addr1 = CmmUtil.nvl(request.getParameter("addr1")); //주소
            String addr2 = CmmUtil.nvl(request.getParameter("addr2")); //상세주소
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 끝!!
             *
             *    무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장함
             * #######################################################
             */

            /*
             * #######################################################
             * 	 반드시, 값을 받았으면, 꼭 로그를 찍어서 값이 제대로 들어오는지 파악해야함
             * 						반드시 작성할 것
             * #######################################################
             * */
            log.info("user_id : " + user_id);
            log.info("user_name : " + user_name);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);


            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUserId(user_id);
            pDTO.setUserName(user_name);

            //비밀번호는 Spring Security에서 제공하는 해시 암호화 수행
            pDTO.setPassword(bCryptPasswordEncoder.encode(password));

            //민감 정보인 이메일은 AES128-CBC로 암호화함
            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            // 권한 부여(사용자)
            pDTO.setRoles(UserRole.USER.getValue());

            // 권한 부여(관리자)
//            pDTO.setRoles(UserRole.ADMIN.getValue());
            /*
             * #######################################################
             *        웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 끝!!
             *
             *        무조건 웹으로 받은 정보는 DTO에 저장해야 한다고 이해하길 권함
             * #######################################################
             */

            /*
             * 회원가입
             * */
            res = userInfoSsService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";

                //추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복을 체크하길 바람
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";

            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";

            }

        } catch (Exception e) {
            //저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            // 결과 메시지 전달하기
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".insertUserInfo End!");

        }

        return dto;
    }


    /**
     * 로그인을 위한 입력 화면으로 이동
     */
    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login Start!");

        log.info(this.getClass().getName() + ".user/login End!");

        return "ss/login";
    }

    /**
     * Spring Security에서 로그인 성공하면, 호출함
     *
     * @param authInfo 인증정보
     * @param session  로그인 정보 저장을 위한 세션 객체
     */
    @ResponseBody
    @RequestMapping(value = "loginSuccess")
    public MsgDTO loginSuccess(@AuthenticationPrincipal AuthInfo authInfo, HttpSession session) {

        log.info(this.getClass().getName() + ".loginSuccess Start!");

        // Spring Security에 저장된 정보 가져오기
        UserInfoDTO rDTO = authInfo.getUserInfoDTO();

        String userName = CmmUtil.nvl(rDTO.getUserName());
        String userId = CmmUtil.nvl(rDTO.getUserId());

        log.info("userName :" + userName);
        log.info("userId :" + userId);

        MsgDTO dto = new MsgDTO();
        dto.setResult(1);
        dto.setMsg("로그인 되었습니다.");

        session.setAttribute("SS_USER_ID", userId); // 세션에 로그인 아이디 저장하기
        session.setAttribute("SS_USER_NAME", userName); // 세션에 로그인 회원이름 저장하기

        log.info(this.getClass().getName() + ".loginSuccess End!");

        return dto;

    }

    /**
     * Spring Security에서 로그인 실패하면, 호출함
     */
    @ResponseBody
    @RequestMapping(value = "loginFail")
    public MsgDTO loginFail() {

        log.info(this.getClass().getName() + ".loginFail Start!");

        MsgDTO dto = new MsgDTO();
        dto.setResult(0);
        dto.setMsg("로그인 실패하였습니다.");

        log.info(this.getClass().getName() + ".loginFail End!");
        return dto;

    }

    /**
     * 로그아웃 처리하기
     */
    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        log.info(this.getClass().getName() + ".logout Start!");

        // 로그아웃 처리하기
        new SecurityContextLogoutHandler().logout(
                request, response, SecurityContextHolder.getContext().getAuthentication());

        log.info(this.getClass().getName() + ".logout End!");

        return "ss/logout";
    }

    /**
     * 로그인 성공 결과 보여주기 화면 호출
     */
    @GetMapping(value = "loginResult")
    public String loginResult(HttpServletRequest request) {

        log.info(this.getClass().getName() + ".loginResult Start!");

        log.info(this.getClass().getName() + ".loginResult End!");

        return "ss/loginResult";
    }


}
