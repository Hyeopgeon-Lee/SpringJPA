package kopo.poly.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info(this.getClass().getName() + ".PasswordEncoder Start!");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info(this.getClass().getName() + ".filterChain Start!");

        // POST 방식 전송을 위해 csrf 막기
        http.csrf().disable();

        http.authorizeHttpRequests(authz -> authz // 페이지 접속 권한 설정
                                // USER 권한
                                .antMatchers("/notice/**").hasAnyAuthority("ROLE_USER")
                                .antMatchers("/user/**").permitAll()
//                                .antMatchers("/user/**", "/notice/**").hasAnyAuthority("ROLE_USER")

                                // 관리자 권한
                                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
//                        .anyRequest().authenticated() // 그외 나머지 url 요청은 인증된 사용자만 가능
                                .anyRequest().permitAll() // 그 외 나머지 url 요청은 인증 받지 않아도 접속 가능함
                )
                .formLogin(login -> login // 로그인 페이지 설정
                        .loginPage("/ss/login")
                        .loginProcessingUrl("/ss/loginProc")
                        .usernameParameter("user_id") // 로그인 ID로 사용할 html의 input객체의 name 값
                        .passwordParameter("password") // 로그인 패스워드로 사용할 html의 input객체의 name 값
                        .successForwardUrl("/ss/loginSuccess") // Web MVC, Controller 사용할 때 적용 / 로그인 성공 URL
                        .failureForwardUrl("/ss/loginFail") // Web MVC, Controller 사용할 때 적용 / 로그인 실패 URL

                )
                .logout(logout -> logout // 로그 아웃 처리
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }
}
