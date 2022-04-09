package parse.squarerefri.domain.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import parse.squarerefri.domain.member.service.MemberService;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;

    private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                // 로그인 없이 접근 가능하도록 설정
                .authorizeRequests()
                    .mvcMatchers("/","/css/**","/js/**","/img/**","/font/**")
                    .permitAll()
                    .antMatchers("/FREEZER/**", "/FRIDGE/**")
                    .authenticated()
                    .antMatchers("/admin/**")
                    .hasAuthority("ROLE_ADMIN")
                    .antMatchers(
                            "/"
                            , "/member/register"
                            , "/member/email-auth"
                            , "/member/find-password"
                            , "/member/reset/**"
                            , "/error/**"
                    )
                    .permitAll()
                    .and()

                // 로그인 처리
                .formLogin()
                    .loginPage("/member/login")
                    .defaultSuccessUrl("/")
                    .failureHandler(userAuthenticationFailureHandler)
                    .successHandler(userAuthenticationSuccessHandler)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/error/denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/resources/**");
    }
}
