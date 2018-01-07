package backend.controller;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.TwoStrings;
import backend.repository.EventRepository;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String MinInMs = "60000";
    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEvents(new ArrayList<>());
        appUserRepository.save(user);
        return true;
    }

    @PostMapping("/updateGoogleFitToken")
    public void updateGoogleFitToken(Authentication auth, @RequestBody TwoStrings accessTokens) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.setGoogleFitAccessToken(accessTokens.getFirst());
         user.setGoogleFitRefreshToken(accessTokens.getSecond());
        appUserRepository.save(user);
    }

    @GetMapping("/getPulse")
    public List<Pulse> getPulse(Authentication auth, @RequestBody TwoStrings time) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        return GoogleCallParser.parseCall(user, time.getFirst(), time.getSecond(), MinInMs);
    }


    @GetMapping("/authenticateToken")
    public Boolean authenticateToken() {
        //when calling this method with an invalid token, an error will be returned,
        //else, nothing will be returned (another option is to changed it so it returnes boolean and then return true)
        return true;
    }

    @GetMapping("/private")
    public String privatee() {
        return "THIS IS PRIVATE!!";
    }

    @GetMapping("/username")
    public String getUsername(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getName();
    }

    @PostMapping("/addEvent")
    public Boolean addEvent(Authentication auth, @RequestBody Event event) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.addEvent(event);
        appUserRepository.save(user);
        return true;
    }

    //testing method.
    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getEvents();
    }

    @PostMapping("/getEvents")
    public List<Event> getEvents(Authentication auth,@RequestBody TwoStrings time) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        List<Event> events = user.getEvents();
        ArrayList<Event> filter = new ArrayList<Event>();

        for (Event event : events) {
            if (event.getStartTime().compareTo(time.getFirst()) >= 0 && event.getEndTime().compareTo(time.getSecond()) <= 0) {
                filter.add(event);
            }
        }
        System.out.println(filter.size());

        for(Event event:filter){
            if(event.getPulses().size()==0) {
                System.out.println("wohooo");
                List<Pulse> eventPulses = GoogleCallParser.parseCall(user, event.getStartTime(), event.getEndTime(), MinInMs);
                event.saveAll(eventPulses);
            }

        }
          user.saveAll(filter);
        return filter;
    }

    @GetMapping("/getEvent")
    public Event getEvent(Authentication auth,@RequestBody String id) {
        return appUserRepository.findByUsername(auth.getName()).getEvent(id);
    }
}