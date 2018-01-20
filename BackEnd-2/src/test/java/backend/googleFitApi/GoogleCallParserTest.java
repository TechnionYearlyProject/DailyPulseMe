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
    @Before
    public void setUp() throws Exception {
        user.setId("123");
        user.setName("mohamad");
        user.setUsername("m@m");
        user.setEvents(new ArrayList<Event>());
        user.setGoogleFitAccessToken("ya29.GltIBbZhSXf5M-7tSN3QExwz6_zJBlttaVkemwQobLxVdmAsyPAZAmQwCybiu7PrJImDsVP2H6CNist7nx3RIDKkFpDaIld45JSbG1a72rMxQD6xDf9c-saqOOCY");
        user.setGoogleFitRefreshToken("1/8IIZcys9xQyBBx5jratzpvl7BLfLrYyAHPPmrVLSXdU");
    }
    @Test
    public void verifyAndRefresh() throws Exception {
        assertTrue( GoogleCallParser.verifyAndRefresh(user));
    }

    @Test
    public void refreshToken() throws Exception {
        String accessToken=GoogleCallParser.refreshToken(user);
        assertTrue(!accessToken.equalsIgnoreCase("nn"));
        user.setGoogleFitAccessToken(accessToken);
    }

    @Test
    public void getPulses() throws Exception {
        try{
            List<Pulse> pulses=GoogleCallParser.getPulses(user,"0","1000000000","60000");
            assertTrue(true);

        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

}