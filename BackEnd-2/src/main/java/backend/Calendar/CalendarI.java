package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;

import java.util.ArrayList;

/*
@auther :Anadil
        this interface is to enable different calenders , eg googleCalendar and outlookCalendar
 */
public interface CalendarI {
     ArrayList<Event> getEvents(AppUser user) throws  Exception;
}
