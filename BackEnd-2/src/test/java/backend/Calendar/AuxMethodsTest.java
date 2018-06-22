package backend.Calendar;

import backend.entity.AppUser;
import org.jboss.arquillian.test.spi.annotation.TestScoped;
import org.junit.Test;

import static backend.Calendar.AuxMethods.RFC5545ToLong;


public class AuxMethodsTest {

    //2018-10-24T01:01:01+03:00
    @Test
    public void RFC5545ToLongTest(){
        assert(Long.toString(AuxMethods.RFC5545ToLong("2018-10-24T01:01:01+03:00")).equals("1540332061000"));
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
}
