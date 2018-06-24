package backend.Calendar;

import backend.entity.AppUser;


import org.junit.Test;
import static backend.Calendar.AuxMethods.isUserConnectedToCalendar;


public class AuxMethodsTest {

    //2018-10-24T01:01:01+03:00
    @Test
    public void RFC5545ToLongTest(){
        assert(Long.toString(AuxMethods.RFC5545ToLong("2018-10-24T01:01:01+03:00")).equals("1540321261000"));
    }

    @Test
    public void IsConnectedToGoogleCalendarTest(){
        AppUser user=new AppUser();
        user.setId("123456");
        user.setName("Anadil");
        user.setUsername("Anadil@hussein");
        user.setAccessToken(null);
        assert (AuxMethods.IsConnectedToOutlookCalendar(user)==false);
        user.setAccessToken("");
        assert (AuxMethods.IsConnectedToOutlookCalendar(user)==false);
    }

    @Test
    public void IsConnectedToOutlookCalendar(){
        AppUser user=new AppUser();
        user.setId("123456");
        user.setName("Anadil");
        user.setUsername("Anadil@hussein");
        user.setOutlookToken(null);
        assert (AuxMethods.IsConnectedToOutlookCalendar(user)==false);
        user.setOutlookToken("");
        assert (AuxMethods.IsConnectedToOutlookCalendar(user)==false);

    }

    @Test
    public  void isUserConnectedToCalendarTest(){
        assert(isUserConnectedToCalendar(null)==false);
        AppUser user=new AppUser();
        user.setId("123456");
        user.setName("Anadil");
        user.setUsername("Anadil@hussein");
        user.setOutlookToken(null);
        user.setAccessToken(null);
        assert (isUserConnectedToCalendar(user)==false);

    }
}
