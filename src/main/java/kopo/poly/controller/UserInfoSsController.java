package kopo.poly.controller;

import kopo.poly.auth.AuthInfo;
import kopo.poly.auth.UserRole;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(value = "/ss")
@RequiredArgsConstructor
@Controller
public class UserInfoSsController {

    // 생성자를 통해 스프링 시작할 때, 메모리에 저장된 객체를 데이터타입에 맞춰 저장하기
    // @Service 어노테이션을 통해 스프링 시작할 때, IUserInfoService 객체가 메모리에 저장됨
//    private final IUserInfoService<UserInfoService> userInfoService;

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
        log.info(this.getClass().getName() + ".user/userRegForm ok!");

        return "/ss/UserRegForm";
    }


    /**
     * 회원가입 로직 처리
     */
    @RequestMapping(value = "insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");

        //회원가입 결과에 대한 메시지를 전달할 변수
        String msg = "";

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

//            // 권한 부여(관리자)
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
            int res = userInfoSsService.insertUserInfo(pDTO);

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
            log.info(this.getClass().getName() + ".insertUserInfo End!");


            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("msg", msg);

            //회원가입 여부 결과 메시지 전달하기
            model.addAttribute("pDTO", pDTO);

            //변수 초기화(메모리 효율화 시키기 위해 사용함)
            pDTO = null;

        }

        return "/user/UserRegSuccess";
    }


    /**
     * 로그인을 위한 입력 화면으로 이동
     */
    @GetMapping(value = "loginForm")
    public String loginForm() {
        log.info(this.getClass().getName() + ".user/loginForm ok!");

        return "/ss/LoginForm";
    }

    @RequestMapping(value = "loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal AuthInfo authInfo, ModelMap model) {

        // Spring Security에 저장된 정보 가져오기
        UserInfoDTO dto = authInfo.getUserInfoDTO();

        String userName = CmmUtil.nvl(dto.getUserName());
        String userId = CmmUtil.nvl(dto.getUserId());

        log.info("userName :" + userName);
        log.info("userId :" + userId);

        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);

        return "/ss/LoginSuccess";

    }


    @RequestMapping(value = "loginFail")
    public String loginFail() {
        return "/ss/LoginFail";

    }


    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // 로그아웃 처리하기
        new SecurityContextLogoutHandler().logout(
                request, response, SecurityContextHolder.getContext().getAuthentication());

        return "/";
    }

    @GetMapping(value = "loginUser")
    public String loginUser(HttpServletRequest request, @AuthenticationPrincipal AuthInfo authInfo) {

        log.info("dto userId : " + authInfo.getUserInfoDTO().getUserId());
        log.info(authInfo.getUsername());
        log.info(authInfo.getPassword());

        return "redirect:/login";
    }
}
