
package onlineShop;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.context.annotation.Bean;
//Http请求会把这些东西注册到框架内部
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {//

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//csrf is the vonerbility, by default is enable, so we disable it
                .formLogin()//read from login page
                .loginPage("/login");//we use our own login page
        http
                .authorizeRequests()//set the authority   //if there is no authority, it will be redirected back to login page
                .antMatchers("/cart/**").hasAuthority("ROLE_USER")//** represent any character  /a/b needs two * to match
                .antMatchers("/get*/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();//all other requests, dont need any authority, so permit all
        //antMathcher(att: api you wrote in the code).hasAuthority(the authority you need)
        http
                .logout()
                .logoutUrl("/logout");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//登录成功之后，useremail会被存在anthentication这个obj
        auth
                .inMemoryAuthentication().withUser("oes1@gmail.com").password("123").authorities("ROLE_ADMIN");
        //in memory method, I have saved some info locally, so I have to write it in the attribute

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)//query which DB
                .usersByUsernameQuery("SELECT emailId, password, enabled FROM users WHERE emailId=?")//? is place holder//get the username and pw from db
                .authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId=?");//get Authority table and query from database
               // matching is done within the framework, if match, the username and pw will be saved in HttpSession
        //this framework cannot use Hibernate, you can only write SQL
    }

    @SuppressWarnings("deprecation")
    @Bean //this is for framework, we have to override. but we didn't do the encryption
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    //if I want to encode
    /*
    @Bean
    public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();//and go back to the customer table to encrypt the passward(one-way hash)
}
    * */
}


