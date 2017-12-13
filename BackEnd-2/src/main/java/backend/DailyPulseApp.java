package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class DailyPulseApp {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args){
        SpringApplication.run(DailyPulseApp.class,args);
    }
   /* public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
        builder.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new MyUserDetails(repo.findByEmail(s));
            }
        });
    }*/
}
