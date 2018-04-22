package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
@auther :Anadil
        this interface is to enable different calenders , eg googleCalendar and outlookCalendar
 */
public interface CalendarI {
     ArrayList<Event> getEvents(AppUser user) throws  Exception;
}
