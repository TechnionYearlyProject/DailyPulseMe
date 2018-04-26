package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.RefreshTokenExpiredException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static backend.Calendar.AuxMethods.RFC5545ToLong;
import static backend.Calendar.AuxMethods.retrieveFeidInJson;
import static backend.googleFitApi.GoogleCallParser.refreshToken;


/*
@Aouther : Anadil
Extracting events from outlook Calendar
 */

public class OutlookCalendar {


    /*
    @aouther : Anadil
    GET https://outlook.office.com/api/v2.0/me/calendarview?startDateTime={start_datetime}&endDateTime={end_datetime}
     */
    public ArrayList<Event> getEvents(AppUser user) throws RefreshTokenExpiredException {


        return 0;
    }
}
