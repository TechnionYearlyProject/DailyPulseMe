package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.json.Json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static backend.helperClasses.KindOfEvent.GOOGLE_EVENT;
import static backend.helperClasses.KindOfEvent.OUTLOOK_EVENT;

public class AuxMethods {


    /*
    @author :Anadil
    @param : string of RFC5545 timestamp format // example for the format 2018-04-05T16:00:00+03:00
    @return : the date time in seconds (long)
     */
    public  static  long RFC5545ToLong(String str){
       // System.out.println(str);
        Pattern pattern= Pattern.compile("(.*)(T)(.*)(\\+|-|\\.)(.*)");
        Matcher m= pattern.matcher(str);
        String str_="";
        if (m.matches()) {
            str_=m.group(1)+" "+m.group(3)+".0"; //
        }
       // System.out.println(str_);
        Timestamp ts = Timestamp.valueOf(str_);
        return ts.getTime();
    }

    /*
    @author :Anadil
    this methods is written for usage of the FronEnd
     */
    public static boolean  isUserConnectedToCalendar(AppUser user){
        if(user == null){
            return  false;
        }
        return  IsConnectedToGoogleCalendar(user)|| IsConnectedToOutlookCalendar(user);

    }


    /*
    @author : Anadil
    this method check if the user has connected to googleCalendar
     */
    public static boolean IsConnectedToGoogleCalendar(AppUser usr){

        return !(usr.getAccessToken()==null || usr.getAccessToken().compareTo("")==0
                || usr.getAccessToken().compareTo(" ")==0);
    }

    /*
    @author : Anadil
    this method check if the user has connected to googleCalendar
    */
    public static boolean IsConnectedToOutlookCalendar(AppUser usr){

        return !(usr.getOutlookToken()==null || usr.getOutlookToken().compareTo("")==0
                || usr.getOutlookToken().compareTo(" ")==0);
    }



}
