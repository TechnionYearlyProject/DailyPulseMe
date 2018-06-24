package backend.Calendar;

import backend.CallParser.GoogleCallParser;
import backend.NLP.NLP;
import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import backend.helperClasses.KindOfEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static backend.Calendar.AuxMethods.RFC5545ToLong;
import static backend.helperClasses.KindOfEvent.GOOGLE_EVENT;

public class GoogleCalendar {
    static  int count=0;
    /*
    @author :Anadil
   this methods get JsonGoogleFormat and extract from it the event
    */
    public static Event GoogleEvent(JsonNode GoogleEventJson){
        Event event = new Event();
        String subject = GoogleEventJson.path("summary").asText();
        String bodyPreview=GoogleEventJson.path("description").asText();
        String start=GoogleEventJson.path("start").path("dateTime").asText();
        if(start==""){
            start=GoogleEventJson.path("start").path("date").asText()+"T00:00:00+03:00";
        }
        String end=GoogleEventJson.path("end").path("dateTime").asText();
        if(end == ""){
            end=GoogleEventJson.path("end").path("date").asText()+"T00:00:00+03:00";
        }
        // System.out.println("subject :"+subject+" bodyPreview :"+bodyPreview+" start :"+start+" end :"+end);
        event.setStartTime(Long.toString(RFC5545ToLong(start)));
        event.setId(Long.toString(RFC5545ToLong(start)));
        event.setName(subject);
        event.setDescription(bodyPreview);
        //  event.setStartTime(Long.toString(RFC5545ToLong(start)));
        event.setEndTime(Long.toString(RFC5545ToLong(end)));
        event.setPulses(new ArrayList<Pulse>());
        event.setKindOfEvent(GOOGLE_EVENT);

        return event;
    }


    /*
   @author :Anadil
   @param : the user who his events will be extract from Google Calender
   @return :getting on the events from the User's google Calendar
   */
    static public ArrayList<Event> getEvents(AppUser user) throws RefreshTokenExpiredException {

       // System.out.println(user.getGoogleFitAccessToken()+"*******\n"+user.getGoogleFitRefreshToken());
        ArrayList<Event> events=new ArrayList<>();
        String accessToken=user.getAccessToken();
        System.out.println("access: "+ accessToken);
        user.getCallParser().verifyAndRefresh(user);
        System.out.println("Now DailyPulsMe will bring your Google Calendar Events (Google API)");
        /* GET Request Getting Events From Google Calendar
          the primary calendar of the currently logged in user
          */
        /*
        URL example !
        https://www.googleapis.com/calendar/v3/calendars/primary/events?timeMax=2018-06-03T10%3A00%3A00Z
        &timeMin=2018-03-03T10%3A00%3A00Z&key={YOUR_API_KEY}
         */
        HttpGet get_=new HttpGet("https://www.googleapis.com/calendar/v3/calendars/primary/events");
        get_.addHeader("Content-Type","application/json;encoding=utf-8");
        get_.addHeader("Authorization" , "Bearer "+ accessToken );
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get_);

            /*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                GoogleCallParser GCP=new GoogleCallParser();
                   accessToken = GCP.refreshToken(user);
                if (accessToken.compareTo("Refresh token expired") == 0) {
                    System.out.println("step66");
                    throw new RefreshTokenExpiredException();
                } else {
                    if(count>1){
                        return null;
                    }
                    count++;
                    System.out.println("step555");
                    user.setAccessToken(accessToken);
                    return getEvents(user);
                }
            }
            String result= EntityUtils.toString(response.getEntity());

            //now extracting the events from the respon
            /*************************************************************/
            System.out.println(result.toString());
            ObjectMapper mapper=new ObjectMapper();
            //	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            JsonNode root = mapper.readTree(result);
            JsonNode eventsNode = root.path("items");
            for (JsonNode node : eventsNode) {
                System.out.println(node.asText());
                events.add(GoogleEvent(node));
            }

        }
        catch (Exception e){
            System.out.println(e.toString());

        }

        System.out.println(events
                .toString());
        System.out.println("now we have google Calendar events ");
        return  events;
    }

}
