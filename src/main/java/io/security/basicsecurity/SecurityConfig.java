package io.security.basicsecurity;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // Web 보안 활성화, web관련 class를 import해줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //TODO overide말고 bean으로 등록하는 방식으로 변경됨


    // configure 를 override해서 인증/인가 방식에 대한 것을 커스텀 할 수 있다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();

        /**
         * formlogin()->  form 로그인방식 , 인증이 되지않으면 기본 로그인페이지로 리다이렉팅 해줌
         */
        http
                .formLogin() // formlogin을 안하고 api로 하면될듯
//                .loginPage("/loginPage") // loginpage커스텀, default는 /login
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .usernameParameter("userId")
                .passwordParameter("passwd")
                .loginProcessingUrl("/login_proc") // login form action url
                .successHandler(new AuthenticationSuccessHandler() {
                    // 익명클래스 정의, 여기선 간단하게 정의해서 사용해보자
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println("Authentication : " + authentication.getName());
                        response.sendRedirect("/");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println("exception : " + exception.getMessage());
                        response.sendRedirect("/login");
                    }
                })
                .permitAll() // /login 접근권한
        ;
    }
}
