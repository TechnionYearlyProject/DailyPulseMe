package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.RefreshTokenExpiredException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
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

     */
     static public ArrayList<Event> getEvents(AppUser user) throws RefreshTokenExpiredException {


        ArrayList<Event> events=new ArrayList<>(); //events array which will contain outlook Events Lettter !
        String accessToken=user.getOutlookToken();
        System.out.println("access token :" + accessToken+"\n");
        HttpGet get_=new HttpGet("https://graph.microsoft.com/v1.0/me/events?&$select=subject,bodyPreview,Start,End&body-content-type=text");
      //  get_.addHeader("Content-Type","application/json");
        get_.addHeader("Authorization" , "Bearer " +user.getOutlookToken());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get_);
            System.out.println("status "+response.getStatusLine().getStatusCode());
            /*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                 throw  new RefreshTokenExpiredException();
            }

        /*    BufferedReader br = new BufferedReader( //putting the response in buffer
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );*/

            String result= EntityUtils.toString(response.getEntity());
            System.out.println("the http response is :"+ result);
            System.out.println("lets starts extracting the events");
            //now extracting the events from the respon
            /*************************************************************/
            /* The next part is about fetching the events from the response*/
            /************************************************************/
            ObjectMapper mapper=new ObjectMapper();
            //	mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            JsonNode root = mapper.readTree(result);
            JsonNode eventsNode = root.path("value");
            for (JsonNode node : eventsNode) {
                String subject = node.path("subject").asText();
                String bodyPreview=node.path("bodyPreview").asText();
                String start=node.path("start").path("dateTime").asText();
                String end=node.path("end").path("dateTime").asText();
                System.out.println("sunject :"+subject+" bodyPreview :"+bodyPreview+" start :"+start+" end :"+end);
                Event event=new Event();
                event.setStartTime(Long.toString(RFC5545ToLong(start)));
                event.setId(Long.toString(RFC5545ToLong(start)));
                event.setName(subject);
                event.setDescription(bodyPreview);
          //      event.setStartTime(Long.toString(RFC5545ToLong(start)));
                event.setEndTime(Long.toString(RFC5545ToLong(end)));
                events.add(event);

            }
        }
        catch (Exception e){
            System.out.println(e.toString());

        }

        System.out.println(events
                .toString());
        return  events;
    }
}
