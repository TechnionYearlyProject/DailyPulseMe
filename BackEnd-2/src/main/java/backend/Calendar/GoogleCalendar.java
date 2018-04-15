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

import static backend.googleFitApi.GoogleCallParser.refreshToken;

public class GoogleCalendar implements CalendarI {

    /*
@author :Anadil
@param String with the format "zzzzz" : "xxxxx",
@return value : the field value ,eg "zzzzz" : "xxxx" , it will return xxxxx
 */
    public static String retrieveFeidInJson(String str){
        System.out.println(str);
        Pattern pattern= Pattern.compile("( )*(\")(.*)(\")(.*)(: \")(.*)(\")(.)*");
        Matcher m= pattern.matcher(str);
        String str_="";
        if (m.matches()) {
            str_=m.group(7); //: "zzzzz"
        }
        System.out.println(str_);
        return str_;
    }


    /*
    @author :Anadil
    @param : string of RFC5545 timestamp format // example for the format 2018-04-05T16:00:00+03:00
    @return : the date time in seconds (long)
     */
    public  static  long RFC5545ToLong(String str){
        Pattern pattern= Pattern.compile("(.*)(T)(.*)(\\+|-)(.*)");
        Matcher m= pattern.matcher(str);
        String str_="";
        if (m.matches()) {
            str_=m.group(1)+" "+m.group(3); //
        }
        Timestamp ts = Timestamp.valueOf(str_);
        return ts.getTime();
    }

    /*
    @author :Anadil
    @param : the user who his events will be extract from Google Calender
    @return :getting on the events from the User's google Calendar
    */
    public ArrayList<Event> getEvents(AppUser user) throws RefreshTokenExpiredException {

        System.out.println(user.getGoogleFitAccessToken()+"*******\n"+user.getGoogleFitRefreshToken());
        ArrayList<Event> events=new ArrayList<>();
        String accessToken=user.getGoogleFitAccessToken();


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
            System.out.println("status "+response.getStatusLine().getStatusCode());
            /*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                accessToken = refreshToken(user);
                if (accessToken.compareTo("Refresh token expired") == 0) {
                    System.out.println("step66");
                    throw new RefreshTokenExpiredException();
                } else {
                    System.out.println("step555");
                    user.setGoogleFitAccessToken(accessToken);
                    return getEvents(user);
                }
            }
            System.out.println("Step33");
            BufferedReader br = new BufferedReader( //putting the response in buffer
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            String line;
            boolean flag=false,desc=false;
            Event tmpEvent=null;
            //now extracting the events from the respond
            while (null != (line = br.readLine())) {

                if(line.matches("([ ]*)(\"items\":)(.*)")){
                    flag=true;
                    continue;
                }
                if(flag) {
                    if(line.matches("([ ]*)(\"summary\":)(.*)")){
                        tmpEvent=new Event();
                        System.out.println("event name :"+retrieveFeidInJson(line));
                        tmpEvent.setName(retrieveFeidInJson(line));
                        while (null != (line = br.readLine())) {
                            if(line.matches("([ ]*)(\"description\":)(.*)")){
                                System.out.println("event desc:"+retrieveFeidInJson(line));
                                tmpEvent.setDescription(retrieveFeidInJson(line));
                                desc=true;
                            }
                            if(line.matches("([ ]*)(\"dateTime\":)(.*)")){
                                if(!desc){
                                    tmpEvent.setDescription("");
                                    System.out.println("event desc:"+ "empty descp");
                                }
                                tmpEvent.setStartTime(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                tmpEvent.setId(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                System.out.println("event StartTime:"+retrieveFeidInJson(line));
                                break;
                            }

                        }
                        while (null != (line = br.readLine())) {
                            if(line.matches("([ ]*)(\"dateTime\":)(.*)")){
                                System.out.println("event EndTime:"+retrieveFeidInJson(line));
                                tmpEvent.setEndTime(Long.toString(RFC5545ToLong(retrieveFeidInJson(line))));
                                desc=false;
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
