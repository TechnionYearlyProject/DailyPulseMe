package backend.controller;
import backend.googleFitApi.googleAuth;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.googleFitApi.heartrate;
import backend.repository.UserRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean signUp(@RequestBody AppUser user) {
        if(appUserRepository.findByUsername(user.getUsername()) == null){
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
        return true;
    }
    @GetMapping("/getToken")
    public void gettoken(Authentication auth){
      AppUser tmp=appUserRepository.findByUsername(auth.getName());
        googleAuth.setAccessToken(tmp);
        appUserRepository.save(tmp);
    }
    @GetMapping("/getPulse")
    public void getPulse(Authentication auth){
        AppUser tmp=appUserRepository.findByUsername(auth.getName());
        googleAuth.getpulse(tmp);

    }



    /*@GetMapping("/getpulse")
    public void getPulse(Authentication auth){
        String js;
        String accessToken=appUserRepository.findByUsername(auth.getName()).getFitbitToken();
        System.out.println(accessToken);
        HttpGet get = new HttpGet("https://api.googleFitApi.com/1/user/-/activities/heart/date/today/1d.json");
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
*/
    @GetMapping("/authenticateToken")
    public Boolean authenticateToken()    {
        //when calling this method with an invalid token, an error will be returned,
        //else, nothing will be returned (another option is to changed it so it returnes boolean and then return true)
        return true;
    }
    @GetMapping("/private")
    public String privatee(){
        return "THIS IS PRIVATE!!";
    }
    @GetMapping("/username")
    public String getUsername(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getName();
    }

    @PostMapping("/addEvent")
    public Boolean addEvent(Authentication auth, @RequestBody Event event){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.addEvent(event);
        appUserRepository.save(user);
        return true;
    }
    //testing method.
    @GetMapping("/getAllEvents")
    public ArrayList<Event> getAllEvents(Authentication auth){
        return appUserRepository.findByUsername(auth.getName()).getEvents();
    }

//    @PostMapping("/userPassword")
//    public String getPassword(@RequestBody String username){
//        if(applicationUserRepository.findByUsername(username)==null){
//            return "DOES NOT EXIST";
//        } else return applicationUserRepository.findByUsername(username).getPassword();
//    }
}