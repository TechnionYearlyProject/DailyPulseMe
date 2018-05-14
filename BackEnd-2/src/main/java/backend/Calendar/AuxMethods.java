package backend.Calendar;

import backend.entity.AppUser;

import java.sql.Timestamp;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuxMethods {

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
        System.out.println(str);
        Pattern pattern= Pattern.compile("(.*)(T)(.*)(\\+|-|\\.)(.*)");
        Matcher m= pattern.matcher(str);
        String str_="";
        if (m.matches()) {
            str_=m.group(1)+" "+m.group(3)+".0"; //
        }
        System.out.println(str_);
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
        if((user.getAccessToken()== null || user.getAccessToken()== "") &&
                (user.getOutlookToken()== null || user.getOutlookToken()== "") ){
            return false;
        }
        return  true;
    }


    /*
    @author : Anadil
    this method check if the user has connected to googleCalendar
     */
    public static boolean IsConnectedToGoogleCalendar(AppUser usr){

        return !(usr.getAccessToken()==null || usr.getAccessToken()=="");
    }

    /*
    @author : Anadil
    this method check if the user has connected to googleCalendar
    */
    public static boolean IsConnectedToOutlookCalendar(AppUser usr){

        return !(usr.getOutlookToken()==null || usr.getOutlookToken()=="");
    }



}
