package BackEnd.Service;

import BackEnd.Repository.UserRepository;
import BackEnd.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public HttpStatus validateToken(String token) throws IOException, JSONException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token);
        CloseableHttpResponse response = client.execute(httpGet);

        System.out.println(response);
        System.out.println(response.getStatusLine());
        String result = response.getStatusLine().toString();

        if (result.equalsIgnoreCase("HTTP/1.1 200 OK")) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
    public void addUser(User user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(String email){
        userRepository.deleteByEmail(email);
    }
}
