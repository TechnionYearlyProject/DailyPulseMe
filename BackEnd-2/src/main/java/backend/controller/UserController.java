package backend.controller;

import backend.entity.AppUser;
import backend.entity.StringDummy;
import backend.fitbit.heartrate;
import backend.repository.UserRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.AuthenticationInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static backend.security.SecurityConstants.SECRET;
import static backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp( AppUser user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
    }
    @PostMapping("/fitbittoken")
    public void fitbitPutToken(Authentication auth, @RequestBody  String accessToken){
        AppUser tmp=appUserRepository.findByUsername(auth.getName());
        tmp.setFitbitToken(accessToken);
        appUserRepository.save(tmp);
    }


    @GetMapping("/getpulse")
    public void getPulse(Authentication auth){
        String js;
        String accessToken=appUserRepository.findByUsername(auth.getName()).getFitbitToken();
        System.out.println(accessToken);
        HttpGet get = new HttpGet("https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json");
        get.addHeader("Authorization" , "Bearer "+accessToken);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            Gson gson = new Gson();
            heartrate name = gson.fromJson(content.toString(),heartrate.class);

        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }


    }


    @GetMapping("/private")
    public String privatee(){
        return "THIS IS PRIVATE!!";
    }
//    @PostMapping("/userPassword")
//    public String getPassword(@RequestBody String username){
//        if(applicationUserRepository.findByUsername(username)==null){
//            return "DOES NOT EXIST";
//        } else return applicationUserRepository.findByUsername(username).getPassword();
//    }
}