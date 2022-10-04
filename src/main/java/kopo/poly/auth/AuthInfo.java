package kopo.poly.auth;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.util.CmmUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Getter
@AllArgsConstructor
public class AuthInfo implements UserDetails {

    private UserInfoDTO userInfoDTO;

//    public AuthInfo(UserInfoDTO userInfoDTO) {
//
//        log.info(this.getClass().getName() + " Create! ");
//        this.userInfoDTO = userInfoDTO;
//    }

    /**
     * 로그인한 사용자의 권한 부여하기
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> pSet = new HashSet<>();

        String roles = CmmUtil.nvl(userInfoDTO.getRoles());

        if (roles.length() > 0) { //DB에 저장된 Role이 있는 경우에만 실행
            for (String role : roles.split(",")) {
                pSet.add(new SimpleGrantedAuthority(role));

            }
        }

        return pSet;
    }

    /**
     * 사용자의 id를 반환 (unique한 값)
     */
    @Override
    public String getUsername() {
//        log.info(this.getClass().getName() + " : " + CmmUtil.nvl(userInfoDTO.getUserId()));
        return CmmUtil.nvl(userInfoDTO.getUserId());

    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
//        log.info(this.getClass().getName() + " : " + CmmUtil.nvl(userInfoDTO.getPassword()));

        return CmmUtil.nvl(userInfoDTO.getPassword());
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
