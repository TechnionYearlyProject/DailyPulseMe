package backend.controller;

import backend.entity.*;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.TwoStrings;
import backend.repository.EventRepository;
import backend.repository.UserRepository;
import org.apache.http.auth.AUTH;
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
    public boolean updateGoogleFitToken(Authentication auth, @RequestBody TwoStrings accessTokens) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.setGoogleFitAccessToken(accessTokens.getFirst());
        user.setGoogleFitRefreshToken(accessTokens.getSecond());
        appUserRepository.save(user);
        //System.out.println(user.getGoogleFitAccessToken());
        return true;
    }


    // Frontend Team requested this
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


    //Aux Testing Method
    @GetMapping("/username")
    public String getUsername(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getName();
    }


    @PostMapping("/addEvent")
    public Boolean addEvent(Authentication auth, @RequestBody Event event) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        /** checking whether the event interval intersects with existed event in the EventList that **/
        long startTime=Long.parseLong(event.getStartTime());
        long endTime=Long.parseLong(event.getEndTime());
        int size= user.getEvents().stream().filter(x->((Long.parseLong(x.getStartTime())>=

                startTime) && Long.parseLong(x.getStartTime())<=endTime )
                || ((Long.parseLong(x.getStartTime())<=startTime) && Long.parseLong(x.getEndTime())>startTime )
                || ((Long.parseLong(x.getStartTime())>=startTime) && Long.parseLong(x.getStartTime())<endTime )
                || ((Long.parseLong(x.getStartTime())<startTime) && Long.parseLong(x.getEndTime())>startTime)
                || ((Long.parseLong(x.getStartTime())>startTime) && Long.parseLong(x.getEndTime())<startTime)).collect(Collectors.toList()).size();
        if(size>0){
            return  false;
        }
        /***/
        user.addEvent(event);
        event.setId(event.getStartTime());
        appUserRepository.save(user);
        return true;
    }

    //Aux Testing Method.
    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getEvents();
    }


    @PostMapping("/deleteEvent")
    public  Boolean deleteEvent(Authentication auth,@RequestBody StringDummy eventId){

        AppUser user = appUserRepository.findByUsername(auth.getName());
        Event event_=null;
        List<Event> tmp=user.getEvents();
        for(Event event : user.getEvents()){
            if(event.getStartTime().equals(eventId.getStr())){
                event_=event;
                break;
            }
        }
        tmp.remove(event_);
        user.setEvents(tmp);
        appUserRepository.save(user);
        return true;
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
        //System.out.println(filter.size());

        ArrayList<Event> result = new ArrayList<Event>();
        List<Pulse> eventPulses;
        for (Event event : filter) {
            if (event.getPulses().size() != 0) {
                try {
                    eventPulses = GoogleCallParser.getPulses(user, event.getStartTime(), event.getEndTime(), MinInMs);
                } catch (RefreshTokenExpiredException e){
                    return null;
                }
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

    @PostMapping("/getEvent")
    public Event getEvent(Authentication auth,@RequestBody String id) {
        return appUserRepository.findByUsername(auth.getName()).getEvent(id);
    }
    @GetMapping("/verifyAccessToken")
    public boolean verifyAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        boolean response = GoogleCallParser.verifyAndRefresh(user);
        appUserRepository.save(user);
        return response;
    }
    //testing method
    @GetMapping("/refreshAccessToken")
    public boolean refreshAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        GoogleCallParser.refreshToken(user);

        return true;
    }
}