package pnu.pnurestaurant.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity //스프링 시큐리티 활성화
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(cs -> cs.disable());

        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyRole("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll()
        );
        http.formLogin(f ->
                f.loginPage("/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
        );


        return http.build();
    }
}