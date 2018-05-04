package backend.CallParser;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import sun.text.resources.no.CollationData_no;

public class GoogleCallParser extends CallParser{
    /*
    after calling this,re-add user to repo.
     */


	/* verifyAndRefresh verifies that the access token is still valid, if it's not refresh token is used to generate new one
	@param user that his access token will be refreshed
	@return true of the refreshing process passed ok , otherwise false
	 */
	@Override
    public boolean verifyAndRefresh(AppUser user) {
        String refreshed;
        HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + user.getAccessToken());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 400 ) { //if it's not a bad request and the respond isn't OK return false
                System.out.println(response.getStatusLine().getStatusCode());
                System.out.println("-------1---------");
                return false;
            }
        } catch (Exception e) {
            System.out.println("-------2---------");
            return false;
        }
        try {

            BufferedReader br = new BufferedReader(	 //putting the respond of google in string buffer
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }

			/* if the response is token not valid , new one should be generated */
            if(content.toString().compareTo("{\"error\":\"invalid_token\"}") == 0 || content.toString().compareTo("{ \"error_description\": \"Invalid Value\"}") == 0) {

				refreshed = refreshToken(user);  //calling  refresh token, to get new accessToken from the refresh token
                if(refreshed.compareTo("Refresh token expired") == 0) {
                    System.out.println("-------3---------");
                    return false;
                } else {
                    user.setAccessToken(refreshed);  // update the user access token with a new one
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("-------4---------");
            return false;
        }
        return true;
    }

	
    /*this function generates new access token from the refresh token
     @param user which his  access token will be refreshed by his refresh token
     @return new access token
     */
    @Override
    public String refreshToken(AppUser user) {
        int ACCESS_START = 15;
        String refresh = user.getRefreshToken();
        String access = "error";

		/*the post request should be sent to google so they will generate access token */
        HttpPost post = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity str=null;
        try {

			/* building the request based on the way that Google defined it ,using our client id, and the refresh token */
            str = new StringEntity("187665345194-0d324v8gel15pj9jh9fecmqknmk4k59k.apps.googleusercontent.com&" +
                    "client_secret=zdKcoMYRsAcrboIU4FmVRF-q&" +
                    "refresh_token="+refresh+"&" +
                    "grant_type=refresh_token");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        post.setEntity(str);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) { //if the respond isn't OK , that means Refresh token expired

               return "Refresh token expired";
            }


            BufferedReader br = new BufferedReader( //putting the response in string
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
			
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }

			/* now for extract the accessToken from the whole response, REGEX will be used */
            Pattern p = Pattern.compile("\"access_token\".[^\\,]*");
            Matcher m = p.matcher(content.toString());
            m.find();
            access = m.group();
            String[] arr = access.split("\"");
            access = arr[3];
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
			
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return access;
    }



    /*this function returns list of Pulse , between startTime and endTime where
    the interval between Consecutive pulses is bucketTime
     @param user which his  Pulse data will be returned
     @param startTime which is string time of the requested period
     @param endTime which is end time of the  requested period
     @param bucket which the interval between two Consecutive pulses
     @return list of Pulse between startTime and endTime  (getting the data from GOOGLE FIT)
     */
    @Override
    public List<Pulse> getPulses(AppUser user,String startTime,String endTime , String bucket) throws RefreshTokenExpiredException{
        List<Pulse> pulses=new ArrayList<>();
        String accessToken=user.getAccessToken();

        //// check if the access token has expired TODO
		
		/* POST request for getting the pulses from Google Fit  */
        HttpPost post=new HttpPost("https://www.googleapis.com/fitness/v1/users/\"https://www.googleapis.com/fitness/v1/users/me/dataset:aggregat/dataset:aggregate");
        post.addHeader("Content-Type","application/json;encoding=utf-8");
        post.addHeader("Authorization" , "Bearer "+ accessToken );
        StringEntity str=null;
        try {
			/*the interval, startTime, EndTime, should be sent based on the way that Google has defined */
            str = new StringEntity("{\n" +
                    "  \"aggregateBy\": [{\n" +
                    "    \"dataTypeName\": \"com.google.heart_rate.bpm\"\n" +
                    "  }],\n" +
                    "  \"bucketByTime\": { \"durationMillis\": "+ bucket+ " },\n" + //the interval
                    "  \"startTimeMillis\": "+ startTime +",\n" +
                    "  \"endTimeMillis\": "+endTime +"\n" +
                    "}");

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        post.setEntity(str);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(post);

			/*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                accessToken = refreshToken(user);
                if(accessToken.compareTo("Refresh token expired") == 0) {
                    throw new RefreshTokenExpiredException();
                } else {
                    user.setAccessToken(accessToken);
                    System.out.println("update acces token");
                    return this.getPulses( user, startTime, endTime ,  bucket);
                }
            }

            BufferedReader br = new BufferedReader(  //putting the response in string
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            Pattern p=Pattern.compile("([0-9]+)\\.([0-9])"); //using regex we get the pulses and save them
            Matcher m=p.matcher(content.toString());
            int counter=0;
            while(m.find() ) {
                if(counter%3==0){
                    Pulse tmp=new Pulse((int)Double.parseDouble(m.group()));
                    pulses.add(tmp);
                }
                counter++;
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return pulses;
    }

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
    public static ArrayList<Event> ExtractGoogleCalendarEvents(AppUser user) throws RefreshTokenExpiredException{

        System.out.println(user.getAccessToken()+"*******\n"+user.getRefreshToken());
       ArrayList<Event> events=new ArrayList<>();
        String accessToken=user.getAccessToken();


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
                //***Rober: updated this to solve error : calling nonstatic method from static context
                accessToken = (new GoogleCallParser()).refreshToken(user);
                if (accessToken.compareTo("Refresh token expired") == 0) {
                    System.out.println("step66");
                   throw new RefreshTokenExpiredException();
                } else {
                    System.out.println("step555");
                    user.setAccessToken(accessToken);
                    return ExtractGoogleCalendarEvents(user);
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
