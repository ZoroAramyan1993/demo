package com.example.demo.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.DetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DetailsService detailService;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(detailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    DetailsService detailsService() {
        return new DetailsService();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.
//                authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/save").permitAll()
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/user/**").hasAuthority("USER").anyRequest()
//                .authenticated().and().csrf().disable().formLogin()
//                .loginPage("/login").failureUrl("/login?error=true")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
//                                                        HttpServletResponse httpServletResponse,
//                                                        Authentication authentication) throws IOException, ServletException {
//                        boolean isAdmin = false;
//                        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
//                            if ("MANAGER".equals(grantedAuthority.getAuthority())) {
//                                isAdmin = true;
//                                break;
//                            }
//                        }
//                        if (isAdmin) {
//                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/user/home");
//                        } else {
//                            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/user/home");
//                        }
//                    }
//                })
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/").and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
//    }


    //    @Autowired(required = true)
//    JwtAuthenticationEntryPoint unauthorizedHandler;
//
//    @Bean
//    JwtAuthenticationFilter jwtAuthenticationFilter(){
//        return new JwtAuthenticationFilter();
//    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/authenticate")
                .permitAll()
                .and()
                .logout()
                .permitAll();

//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/",
//                        "/favicon.ico",
//                        "/**/*.png",
//                        "/**/*.gif",
//                        "/**/*.svg",
//                        "/**/*.jpg",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js")
//                .permitAll()
//                .antMatchers( "/user/**")
//                .permitAll()
//                .antMatchers("/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
//                .permitAll()
//                .antMatchers(HttpMethod.GET, "/user**")
//                .permitAll()
//                .antMatchers(HttpMethod.POST, "/user**")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
//

    }


}
