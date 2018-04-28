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
     */
     static public ArrayList<Event> getEvents(AppUser user) throws RefreshTokenExpiredException {


         System.out.println("here here");
        ArrayList<Event> events=new ArrayList<>(); //events array which will contain outlook Events Lettter !
        String accessToken=user.getOutlookToken();
        System.out.println("access token :" + accessToken+"\n");
        HttpGet get_=new HttpGet("https://graph.microsoft.com/v1.0/me/events?access_token=Bearer "+accessToken+"&$select=subject,bodyPreview,Start,End&body-content-type=text");
      //  get_.addHeader("Content-Type","application/json");
     //   get_.addHeader("Authorization" , "Bearer "+ accessToken );
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get_);
            System.out.println("status "+response.getStatusLine().getStatusCode());
            /*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                 throw  new RefreshTokenExpiredException();
            }
            System.out.println("Read res "+response.getEntity().getContent().toString());
            BufferedReader br = new BufferedReader( //putting the response in buffer
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            String line;
            boolean flag=false;
            Event tmpEvent=null;
            System.out.println("lets starts extracting the events");
            //now extracting the events from the respond
            while (null != (line = br.readLine())) {
                System.out.println("ok");
                System.out.println(line);
                if(line.matches("([ ]*)(\"value\":)(.*)")){
                    flag=true;
                    continue;
                }
                if(flag) {
                    if(line.matches("([ ]*)(\"subject\":)(.*)")){
                        tmpEvent=new Event();
                        System.out.println("event name :"+retrieveFeidInJson(line));
                        tmpEvent.setName(retrieveFeidInJson(line));
                        while (null != (line = br.readLine())) {
                            if(line.matches("([ ]*)(\"bodyPreview\":)(.*)")){
                                tmpEvent.setDescription(retrieveFeidInJson(line));
                            }
                            if(line.matches("([ ]*)(\"dateTime\":)(.*)")){
                                tmpEvent.setStartTime(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                tmpEvent.setId(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                break;
                            }
                        }
                        while (null != (line = br.readLine())) {
                            if(line.matches("([ ]*)(\"dateTime\":)(.*)")){
                                tmpEvent.setEndTime(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                events.add(tmpEvent);
                                System.out.println(tmpEvent.toString());
                                break;
                            }
                        }

                    }
                }
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
