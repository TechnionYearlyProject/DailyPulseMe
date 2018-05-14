package backend.controller;

import backend.Calendar.AuxMethods;
import backend.Calendar.GoogleCalendar;
import backend.Calendar.OutlookCalendar;
import backend.DailyPulseApp;
import backend.Outlook.Outlook;
import backend.entity.*;
import backend.googleFitApi.GoogleCallParser;
import backend.googleSignIn.SignUpGoogle;
import backend.helperClasses.TwoStrings;
import backend.repository.EventRepository;
import backend.repository.UserRepository;
import backend.service.UserService;
import org.apache.http.auth.AUTH;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import static backend.Calendar.AuxMethods.IsConnectedToGoogleCalendar;
import static backend.Calendar.AuxMethods.IsConnectedToOutlookCalendar;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository appUserRepository;             //Repository that contains the users who  registered  for websites
    private BCryptPasswordEncoder bCryptPasswordEncoder; //this is used for encoding passwords
    private static final String MinInMs = "60000";        // constant which "stores" one minunte in millis



    /*
    Constructor which gets two params , the appUserRepository and  bCryptPasswordEncoder
     */
    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /*
    singUp method adds new user fot the repository
    @param user which will be added
    @return true if the user doesn't exist in the repo, otherwise false
     */
    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody AppUser user) {
        try {
            if (appUserRepository.findByUsername(user.getUsername()) != null) { //checking if the username already exist
                return false;
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //decoding the password
            user.setEvents(new ArrayList<>()); //initializing the events list for an empty one
            appUserRepository.save(user); //saving the user in the user's repository
            return true;
        }
        catch (Exception e){
            DailyPulseApp.LOGGER.info("error from backend " + e.toString());
            return false;
        }
    }
    @PostMapping("/sign-up-google")
    public boolean signUp(@RequestBody String authToken) {
       AppUser user=SignUpGoogle.getGoogleUser(authToken);
       if(user==null){
           return false;
       }
       return signUp(user);

    }

    /*
    @auother: Anadil
    update outlookToken's access token and refresh token of Microsoft outlook ,
    @param auth which by it the user will be retrieved
    @param accessToken which contains the new access token and refresh token
    @return true
     */
    @PostMapping("/getOutlookToken")
    public boolean getOutLookToken(Authentication auth, TwoStrings accessTokens) {
        try {
            AppUser user = appUserRepository.findByUsername(auth.getName());
            if(user == null){
                return  false;
            }
            UserService.updateOutLookTokens(user,accessTokens); //calling for Service function
            appUserRepository.save(user);
            return true;
        }
        catch (Exception e){
            DailyPulseApp.LOGGER.info("error from backend " + e.toString());
            return false;
        }
    }


    /*@auother: Anadil
       this function return true if the user sign in to one calender at least
    */
    @GetMapping("/isThereOneCalendar")
    public boolean getOutLookToken(Authentication auth) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        if(user == null){
            return  false;
        }
        return IsConnectedToGoogleCalendar(user)||IsConnectedToOutlookCalendar(user);
    }
    /*
    updateGoogleFitToken updates the access token and refresh token of Google Fit ,
    @param auth which by it the user will be retrieved
    @param accessToken which contains the new access token and refresh token
    @return true
     */
    @PostMapping("/updateGoogleFitToken")
    public boolean updateGoogleFitToken(Authentication auth, TwoStrings accessTokens) {
        try {
            AppUser user = appUserRepository.findByUsername(auth.getName());
            if(user == null){
                return  false;
            }
            UserService.updateTokens(user,accessTokens); //calling for Service function
            appUserRepository.save(user);
            return true;
        }
        catch (Exception e){
            DailyPulseApp.LOGGER.info("error from backend " + e.toString());
            return false;
        }
    }


    /*(Frontend Team requested this)
      when calling this method with an invalid token, an error will be returned,
      else, nothing will be returned
     */
    @GetMapping("/authenticateToken")
    public Boolean authenticateToken() {

        return true;
    }

    @GetMapping("/private")
    public String privatee() {
        return "THIS IS PRIVATE!!";
    }


    /*Aux Testing Method
     @param auth , which by it the user will be retrieved
     @return user name
     */
    @GetMapping("/username")
    public String getUsername(Authentication auth) {
        String str = appUserRepository.findByUsername(auth.getName()).getName();
        return JSONObject.quote(str);
    }

    /*
    addEvent adds new event to list user of specific user
    @param auth , which by it the user will be retrieved
    @param event which will be added
    @return true if the adding process passed OK ,otherwise false
     */
    @PostMapping("/addEvent")
    public boolean addEvent(Authentication auth, @RequestBody Event event) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        if(!UserService.addEvent(user,event)){
            return false;
        }
        appUserRepository.save(user);
        return true;
    }


    /*Aux Testing Method. @testing
    @param auth , which by it the user will be retrieved
    @return list of events of specific user
    */
    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(Authentication auth) {
        return appUserRepository.findByUsername(auth.getName()).getEvents();
    }

    /*
    deleteEvent deletes an event which exist in list of event of specific user
    @param auth , which by it the user will be retrieved
    @param evenId which is the id of the event to be deleted
    @return true if the deleting event process passed OK ,otherwise false
     */
    @PostMapping("/deleteEvent")
    public Boolean deleteEvent(Authentication auth, String eventId){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        UserService.deleteEvent(user,eventId);
        appUserRepository.save(user);
        return true;
    }


    /*
    getEvents return Events which were taken place between time1 until time2
    ,and each event which will be returned through the list , will includes it's pulses
     @param auth , which by it the user will be retrieved
     @param time which contains the startTiming and endTiming
     @return list of events which were taken place between time1 until time2
     */
    @PostMapping("/getEvents")
    public List<Event> getEvents(Authentication auth, TwoStrings time) {

        getCalendarsEvents(auth); //TODO :Refresh (we dont need to bring what is Already Exist)
        AppUser user = appUserRepository.findByUsername(auth.getName());
        List<Event> filter = UserService.getEvents(user,time);
        appUserRepository.save(user);
        if(filter == null){
            return null;
        }
        return filter.stream().sorted(new Comparator<Event>() {
            @Override
            public int compare(Event r1, Event r2) {
                return (r1.getId().compareTo(r2.getId()));
            }
        }).collect(Collectors.toList());
    }




    @RequestMapping("/GetCalendarsEvents")
    public ArrayList<Event> getCalendarsEvents(Authentication auth)  {

        ArrayList<Event> tmp_=null;
        ArrayList<Event> tmp=new ArrayList<Event>();
        AppUser user = appUserRepository.findByUsername(auth.getName());
        try{
            if(IsConnectedToGoogleCalendar(user)){
                tmp_= GoogleCalendar.getEvents(user);
            }
            if(IsConnectedToOutlookCalendar(user)) {
                tmp_.addAll( OutlookCalendar.getEvents(user));
            }

            System.out.println("now the refreshing part");
            //Refreshing
            boolean isNewEvent=true;
            for(Event event : tmp_){

                for (Event userEvent : user.getEvents()){
                    if(userEvent.getId()== event.getId()  && userEvent.getEndTime()==event.getEndTime()){
                        isNewEvent=false;
                        break;
                    }
                }
                if(isNewEvent){
                    System.out.println("Ohhh we have new event ");
                    tmp.add(event);
                }
                isNewEvent=true;
            }
            System.out.println("number of new events :"+tmp.size());
            user.addEvents(tmp);
            user.setEvents(tmp);
            appUserRepository.save(user);
            System.out.println("done");
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return tmp;
    }



    /*
    getEvent return an Event,
    @param auth , which by it the user will be retrieved
    @param id of the event which will be returned
    @return an event from the user's list of event which it's id is same to the given id
    */
    @PostMapping("/getEvent")
    public Event getEvent(Authentication auth, String id) {
        return appUserRepository.findByUsername(auth.getName()).getEvent(id);
    }


    @GetMapping("/verifyAccessToken")
    public boolean verifyAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        boolean response = GoogleCallParser.verifyAndRefresh(user);
        appUserRepository.save(user);
        return response;
    }


    @GetMapping("/refreshAccessToken")
    public boolean refreshAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        GoogleCallParser.refreshToken(user);
        return true;
    }
}