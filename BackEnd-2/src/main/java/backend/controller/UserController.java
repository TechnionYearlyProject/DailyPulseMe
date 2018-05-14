package backend.controller;

import backend.CallParser.FitBitCallParser;
import backend.CallParser.GoogleCallParser;
import backend.DailyPulseApp;
import backend.emailSend.EmailSender;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Subscription;
import backend.helperClasses.BandType;
import backend.Calendar.AuxMethods;
import backend.Calendar.GoogleCalendar;
import backend.Calendar.OutlookCalendar;
import backend.DailyPulseApp;
import backend.Outlook.Outlook;
import backend.entity.*;
import backend.googleSignIn.SignUpGoogle;
import backend.helperClasses.TwoStrings;
import backend.repository.SubscribedRepository;
import backend.repository.UserRepository;
import backend.service.UserService;

import org.apache.http.auth.AUTH;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static backend.helperClasses.BandType.FITBIT_BAND;
import static backend.helperClasses.BandType.GOOGLEFIT_BAND;
import static backend.Calendar.AuxMethods.IsConnectedToGoogleCalendar;
import static backend.Calendar.AuxMethods.IsConnectedToOutlookCalendar;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository appUserRepository;             //Repository that contains the users who  registered  for websites
    private BCryptPasswordEncoder bCryptPasswordEncoder; //this is used for encoding passwords
    private static final String MinInMs = "60000";        // constant which "stores" one minunte in millis
    private SubscribedRepository subscribedUserRepository; //Repository that contains weekly-mail subscribed users

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
            user.setAccessToken(" ");
            user.setRefreshToken(" ");

            //default is googleFit
            user.setActiveBandType(BandType.GOOGLEFIT_BAND);
            user.setCallParser(new GoogleCallParser());

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
<<<<<<< HEAD
    updateTokens updates the access token and refresh token of the band ,
=======
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
>>>>>>> calendars_done
    @param auth which by it the user will be retrieved
    @param accessToken which contains the new access token and refresh token
    @return true
     */
    @PostMapping("/updateTokens")
    public boolean updateTokens(Authentication auth, @RequestBody TwoStrings tokens) {

        AppUser user = appUserRepository.findByUsername(auth.getName());
        if(user == null){
            return  false;
        }
        UserService.updateTokens(user,tokens); //calling for Service function
        appUserRepository.save(user);
        return true;
    }


    /*(Frontend Team requested this)
      when calling this method with an invalid token, an error will be returned,
      else, nothing will be returned
     */
    @GetMapping("/authenticateToken")
    public Boolean authenticateToken() {
        //authenticates JWT token.
        return true;
    }
    //testing
    @GetMapping("/private")
    public String privatee() {
        return "THIS IS PRIVATE!!";
    }


    /*Aux Testing Method
     @param auth , which by it the user will be retrieved
     @return user name
     */
    //TODO: Delete this?
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
    public List<Event> getEvents(Authentication auth,@RequestBody TwoStrings time) {

        getCalendarsEvents(auth); //TODO :Refresh (we dont need to bring what is Already Exist)
        AppUser user = appUserRepository.findByUsername(auth.getName());
        List<Event> filter = UserService.getEvents(user,time);
        appUserRepository.save(user);
        if(filter == null){
            return null;
        }
        List<Event> toreturn = filter.stream().sorted(new Comparator<Event>() {
            @Override
            public int compare(Event r1, Event r2) {
                return (r1.getId().compareTo(r2.getId()));
            }
        }).collect(Collectors.toList());
        System.out.println(toreturn.size()+" xxxxxx");
        return toreturn;
    }

    @RequestMapping("/GoogleCalendarEvents")
    public ArrayList<Event> getEventsGoogleCalendar(Authentication auth)  {
        System.out.println("GoogleCalendarEvents\n");
        ArrayList<Event> tmp=null;
        AppUser user = appUserRepository.findByUsername(auth.getName());
        try{
            tmp=GoogleCalendar.getEvents(user);
            user.addEvents(tmp);
            appUserRepository.save(user);
        }catch (Exception e){
          System.out.println("line 188 user cON");
        }
        return tmp;
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
        boolean response = UserService.verifyAndRefresh(user);
        appUserRepository.save(user);
        return response;
    }

    @GetMapping("/refreshAccessToken")
    public boolean refreshAccessToken(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        UserService.refreshToken(user);
        return true;
    }
    //sets the FitBit band as the active band
    @PostMapping("/setFitBitBand")
    public void updateToFitBit(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.setCallParser(new FitBitCallParser());
        user.setActiveBandType(FITBIT_BAND);
    }
    //sets the GoogleFit as the active band (this is the default)
    @PostMapping("/setGoogleFitBand")
    public void updateToGoogleFit(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        user.setCallParser(new GoogleCallParser());
        user.setActiveBandType(GOOGLEFIT_BAND);
    }

    /*returns the number associated with the band type
    0 - GoogleFit
    1 - FitBit
    */
    @GetMapping("/getActiveBand")
    public int getActiveBand(Authentication auth) {
        AppUser user = appUserRepository.findByUsername(auth.getName());
        return user.getActiveBandType().getBandTypeAsInt();
    }

    //adds the user to the subscribedUsersRepo
    @PostMapping("/subscribe")
    public boolean subscribeUser(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        if(user == null || subscribedUserRepository.findByEmail(user.getUsername()) != null){
            //user already subscribed
            return false;
        }
        subscribedUserRepository.save(new Subscription(user.getUsername(),user.getName()));
        return true;
    }

    //removes the user from the subscribedUsersRepo
    @PostMapping("unsubscribe")
    public boolean unsubscribeUser(Authentication auth){
        AppUser user = appUserRepository.findByUsername(auth.getName());
        if(user == null || subscribedUserRepository.findByEmail(user.getUsername()) == null){
            //user already unsubscribed
            return false;
        }
        subscribedUserRepository.deleteByEmail(user.getUsername());
        return true;
    }
    //returns all the subscribed users
    //TODO: find a way to make this safe (added to public calls)
    @GetMapping("/getSubscribedUsers")
    public List<Subscription> getSubscribedUsers(){
        return subscribedUserRepository.findAll();
    }

    @PostMapping("/sendMails")
    public void sendMail() {
        ArrayList<Subscription> subcribers = new ArrayList<>();
        subcribers.addAll(subscribedUserRepository.findAll());
        EmailSender.sendMail(subcribers);
    }
}