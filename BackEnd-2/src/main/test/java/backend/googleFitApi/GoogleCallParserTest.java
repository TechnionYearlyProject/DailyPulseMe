package backend.googleFitApi;

import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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
        user.setGoogleFitAccessToken("ya29.GltIBbZhSXf5M-7tSN3QExwz6_zJBlttaVkemwQobLxVdmAsyPAZAmQwCybiu7PrJImDsVP2H6CNist7nx3RIDKkFpDaIld45JSbG1a72rMxQD6xDf9c-saqOOCY");
        user.setGoogleFitRefreshToken("1/8IIZcys9xQyBBx5jratzpvl7BLfLrYyAHPPmrVLSXdU");

        user2.setId("111");
        user2.setName("anadil1");
        user2.setUsername("anadil@anadil");
        user2.setEvents(new ArrayList<Event>());
        user2.setGoogleFitAccessToken("nothing");
        user2.setGoogleFitRefreshToken("nothing");

        user2.setId("1111");
        user2.setName("anadil2");
        user2.setUsername("anadil2@anadil");
        user2.setEvents(new ArrayList<Event>());
    }
    @Test
    public void verifyAndRefresh() throws Exception {
        assertTrue(GoogleCallParser.verifyAndRefresh(user));
        assertTrue(!GoogleCallParser.verifyAndRefresh(user2));
        assertTrue(!GoogleCallParser.verifyAndRefresh(user3));

    }

    @Test
    public void refreshToken() throws Exception {
        String accessToken=GoogleCallParser.refreshToken(user);
        assertTrue(!accessToken.equalsIgnoreCase("nn"));
        user.setGoogleFitAccessToken(accessToken);
        GoogleCallParser.refreshToken(user2);
        GoogleCallParser.refreshToken(user3);

    }

    @Test
    public void getPulses() throws Exception {
        try{
            List<Pulse> pulses=GoogleCallParser.getPulses(user,"0","100000000","60000");
            assertTrue(true);

            GoogleCallParser.getPulses(user2,"0","100000000","60000");

        }
        catch (Throwable t) {
            try {
                GoogleCallParser.getPulses(user2,null,null,null);
            }
            catch (Throwable t2){

            }
            t.printStackTrace();
        }

    }

}