package by.puesosi.schedule.config;

import by.puesosi.schedule.dao.UserRepository;
import by.puesosi.schedule.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Service service;

    private OAuth2ClientContext oAuth2ClientContext;

    private UserRepository userRepository;

    /**
     * Password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Instantiates a new Security config.
     *
     * @param service             the service
     * @param oAuth2ClientContext the o auth 2 client context
     * @param userRepository      the user repository
     */
    @Autowired
    public SecurityConfig(Service service, OAuth2ClientContext oAuth2ClientContext,
                          UserRepository userRepository) {
        this.service = service;
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.userRepository = userRepository;
    }

    /**
     * O auth 2 client filter registration filter registration bean.
     *
     * @param oAuth2ClientContextFilter the o auth 2 client context filter
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oAuth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(oAuth2ClientContextFilter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        CustomUserInfoTokenServices tokenServices = new CustomUserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);
        tokenServices.setUserRepository(userRepository);
        return googleFilter;
    }

    /**
     * Google authorization code resource details.
     *
     * @return the authorization code resource details
     */
    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    /**
     * Google resource resource server properties.
     *
     * @return the resource server properties
     */
    @Bean
    @ConfigurationProperties("security.oauth2.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login**", "/registration").not().authenticated()
                .antMatchers("/schedule/admin/**").hasRole("ADMIN")
                .antMatchers("/schedule/user/**").hasRole("USER")
                .antMatchers("/schedule/**", "/css/**",
                        "/img/**", "/js/**", "/fonts/**", "/vendor/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .usernameParameter("username")
                .usernameParameter("mail")
                .loginPage("/login")
                .defaultSuccessUrl("/schedule/")
                .permitAll()
                .and()
                .rememberMe()
                .alwaysRemember(true)
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/schedule/error");

    }
}
