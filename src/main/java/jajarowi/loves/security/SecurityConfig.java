package jajarowi.loves.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static jajarowi.loves.security.Role.ROLE_ASSIGNEE;
import static jajarowi.loves.security.Role.ROLE_SCIENTIST;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public SecurityConfig(boolean disableDefaults, UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(disableDefaults);
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/refresh/**", "/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/projects/{id}/**")
                .access("@projectService.hasOwner(authentication,#id)");
        http.authorizeRequests().antMatchers("/assignments/{id}/**")
                .access("@assignmentService.hasAssignee(authentication,#id)");
        http.authorizeRequests().antMatchers("/users/{login}/**")
                .access("@userService.hasUserLogin(authentication,#login)");
        http.authorizeRequests().antMatchers("/assignments/**").hasAnyAuthority(ROLE_ASSIGNEE.name());
        http.authorizeRequests().antMatchers("/assignees/**").hasAnyAuthority(ROLE_SCIENTIST.name());
        http.authorizeRequests().antMatchers("/projects/**").hasAnyAuthority(ROLE_SCIENTIST.name());
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
        http.addFilter(new AuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
