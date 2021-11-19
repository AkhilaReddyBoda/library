package com.greenapex.ELibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
public class SecutityConfiguration extends WebSecurityConfigurerAdapter {

     
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustUserDetailsService();
    }
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
     
   //@Bean
   //public BCryptPasswordEncoder passwordEncoder() {
   //    return new BCryptPasswordEncoder();
 // }
    
    @Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/addbook-form","/home","/create-book","/updateBook","/requestBook","/bookListforUser","/bookslist","/issuedBooks",
            		"/myBooks","/requestedBookList","/usersList")
            .authenticated()
            .anyRequest().permitAll()
            .and()
            .formLogin()
         
            .loginPage("/login").failureUrl("/login?error=true")
            .permitAll()
            .defaultSuccessUrl("/home")
            .usernameParameter("email")
            .passwordParameter("password")
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .and()
            .exceptionHandling()
            .accessDeniedPage("/access-denied");;
    }
  
}

	
	

