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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
        event.setId(event.getStartTime());
        if(user.getEvents().contains(event)){
            return false;
        }
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

        //getting all events within time period
        for (Event event : events) {
            if (event.getStartTime().compareTo(time.getFirst()) >= 0 && event.getEndTime().compareTo(time.getSecond()) <= 0) {
                filter.add(event);
            }
        }
        //filter contains the events in the given time
        System.out.println(filter.size());

        ArrayList<Event> result = new ArrayList<Event>();
        for (Event event : filter) {
            if (event.getPulses().size() == 0) {
                List<Pulse> eventPulses = GoogleCallParser.parseCall(user, event.getStartTime(), event.getEndTime(), MinInMs);

                event.saveAll(eventPulses);

                event.setAverage();
            }

        }
        appUserRepository.save(user);
        return filter.stream().sorted(new Comparator<Event>() {
            @Override
            public int compare(Event r1, Event r2) {
                return (r1.getId().compareTo(r2.getId()));
            }
        }).collect(Collectors.toList());
    }

    @GetMapping("/getEvent")
    public Event getEvent(Authentication auth,@RequestBody String id) {
        return appUserRepository.findByUsername(auth.getName()).getEvent(id);
    }
}