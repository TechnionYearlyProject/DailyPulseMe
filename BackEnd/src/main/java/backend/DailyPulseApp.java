package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DailyPulseApp {

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
