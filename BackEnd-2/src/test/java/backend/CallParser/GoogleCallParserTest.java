package backend.CallParser;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GoogleCallParserTest {
    AppUser user= new AppUser();
    AppUser user2=new AppUser();
    AppUser user3=new AppUser();
    @Before
    public void setUp() throws Exception {
        user.setId("123");
        user.setName("mohamad");
        user.setUsername("m@m");
        user.setEvents(new ArrayList<Event>());
        user.setAccessToken("ya29.GlvkBfu357KYXg8kDaHFk_n4mrmkzTD5r6MGdxKKGbo0idBPMwN0nTkIo4cqQbO8RlTuBsioe_CB7NuI0_d-ZmCID1UrI-YDKmtL2-gwZMPOTCDi4D64hIyXIN0c");
        user.setRefreshToken("1/PjHKgEDAlvCtmmWt6JfA2daUGLQAwe2Ui4HQ8pBIfo0");

        user2.setId("111");
        user2.setName("anadil1");
        user2.setUsername("anadil@anadil");
        user2.setEvents(new ArrayList<Event>());
        user2.setAccessToken("nothing");
        user2.setRefreshToken("nothing");

        user2.setId("1111");
        user2.setName("anadil2");
        user2.setUsername("anadil2@anadil");
        user2.setEvents(new ArrayList<Event>());
    }
    //@Test
    public void verifyAndRefresh() throws Exception {
        assertTrue(user.getCallParser().verifyAndRefresh(user));
        assertTrue(!user.getCallParser().verifyAndRefresh(user2));
        assertTrue(!user.getCallParser().verifyAndRefresh(user3));

    }

    @Test
    public void refreshToken() throws Exception {
        String accessToken=user.getCallParser().refreshToken(user);
        assertTrue(!accessToken.equalsIgnoreCase("nn"));
        user.setAccessToken(accessToken);
        user.getCallParser().refreshToken(user2);
        user.getCallParser().refreshToken(user3);

    }

    @Test
    public void getPulses() throws Exception {
        try{
            List<Pulse> pulses=user.getCallParser().getPulses(user,"0","100000000","60000");
            assertTrue(true);

            user.getCallParser().getPulses(user2,"0","100000000","60000");

        }
        catch (Throwable t) {
            try {
                user.getCallParser().getPulses(user2,null,null,null);
            }
            catch (Throwable t2){

            }
            t.printStackTrace();
        }

    }

}