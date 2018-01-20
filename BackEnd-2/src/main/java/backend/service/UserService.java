package backend.service;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.googleFitApi.GoogleCallParser;
import backend.helperClasses.TwoStrings;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private static final String MinInMs = "60000";

    public static boolean addEvent(AppUser user, Event event) {
		
		
        long startTime = Long.parseLong(event.getStartTime());
        long endTime = Long.parseLong(event.getEndTime());
		//check if this event can be added (doesn't interlap) and it's times are legal
        int size = user.getEvents().stream().filter(x ->
                ((Long.parseLong(x.getStartTime()) >=  endTime) && (Long.parseLong(x.getEndTime()) >= endTime))
                || ((Long.parseLong(x.getStartTime()) <=  startTime) && (Long.parseLong(x.getEndTime()) <= startTime))).collect(Collectors.toList()).size();
        if ((user.getEvents().size()-size) != 0) {
            return false;
        }
		//add to user's events  list
        user.addEvent(event);
		//since events doesn't interlap , we give the event a unique id , it's start time (easier to return it to the front when needed)
        event.setId(event.getStartTime());
        return true;
    }

    public static boolean deleteEvent(AppUser user, String eventID) {

        Event event_ = null;
        boolean isExist=false;
		//temp list of the user's events
        List<Event> tmp = user.getEvents();
        for (Event event : user.getEvents()) {
			//event start time is it's id as we agreed with the front , find this event
            if (event.getStartTime().equals(eventID)) {
                event_ = event;
                isExist=true;
                break;
            }
        }

        if(!isExist){
            return  false;
        }
		//delete event from tmp list 
        tmp.remove(event_);
		//update user's list 
        user.setEvents(tmp);
        return true;
    }


    public static List<Event> getEvents(AppUser user, TwoStrings time) {


        List<Event> events = user.getEvents();
        List<Event> filter = new ArrayList<Event>();
        //getting all events within time period
        for (Event event : events) {
            if (event.getStartTime().compareTo(time.getFirst()) >= 0 && event.getEndTime().compareTo(time.getSecond()) <= 0) {
                filter.add(event);
            }
        }
        //filter contains the events in the given time interval
        List<Event> result = new ArrayList<Event>();
        List<Pulse> eventPulses;
		
		//for all the events we should get the pulses
        for (Event event : filter) {
			//if it's pulses list is empty  , we should ask google to give us the pulses 
            if (event.getPulses().size() == 0) {
                try {
					//get the pulses in this specific time 
                    eventPulses = GoogleCallParser.getPulses(user, event.getStartTime(), event.getEndTime(), MinInMs);
                } catch (RefreshTokenExpiredException e) {
                    return null;
                }
				//update the pulses for this event
                event.saveAll(eventPulses);
                event.setAverage();
            }
        }
        System.out.println("Filter size: "+filter.size());
        return filter;
    }
    public static boolean updateTokens(AppUser user, TwoStrings accessTokens){
        //update token fields 
        user.setGoogleFitAccessToken(accessTokens.getFirst());
        user.setGoogleFitRefreshToken(accessTokens.getSecond());
        return true;
    }
}
