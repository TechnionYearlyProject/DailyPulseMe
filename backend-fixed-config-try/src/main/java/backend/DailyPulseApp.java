package backend;

import backend.repository.UserRepository;
import backend.service.MyUserDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@SpringBootApplication
public class DailyPulseApp {

    public static void main(String[] args){
        SpringApplication.run(DailyPulseApp.class,args);
    }
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception {
        builder.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new MyUserDetails(repo.findByEmail(s));
            }
        });
    }
}
