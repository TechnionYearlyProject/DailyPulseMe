package backend.Calendar;

import backend.entity.AppUser;
import backend.entity.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;



public class OutlookCalendarTest {
    @Test
    public void OutlookEventTest() {
        String str = "  {\n" +
                "   \"kind\": \"calendar#event\",\n" +
                "   \"etag\": \"\\\"2996154144504000\\\"\",\n" +
                "   \"id\": \"6spo4pvm002qg83csflp7cbbta\",\n" +
                "   \"updated\": \"2017-06-21T20:31:12.252Z\",\n" +
                "   \"subject\": \"Anadil\",\n" +
                "   \"creator\": {\n" +
                "    \"email\": \"anadilhussein@gmail.com\",\n" +
                "    \"displayName\": \"anadil hussein\",\n" +
                "    \"self\": true\n" +
                "   },\n" +
                "   \"organizer\": {\n" +
                "    \"email\": \"anadilhussein@gmail.com\",\n" +
                "    \"displayName\": \"anadil hussein\",\n" +
                "    \"self\": true\n" +
                "   },\n" +
                "   \"start\": {\n" +
                "    \"dateTime\": \"2018-10-24T01:01:01+03:00\"\n" +
                "   },\n" +
                "   \"end\": {\n" +
                "    \"dateTime\": \"2018-10-24T01:01:01+03:00\"\n" +
                "   },\n" +
                "   \"sequence\": 0,\n" +
                "   \"reminders\": {\n" +
                "    \"useDefault\": true\n" +
                "   }\n" +
                "  }";
        String str2 = "  {\n" +
                "   \"kind\": \"calendar#event\",\n" +
                "   \"etag\": \"\\\"2996154144504000\\\"\",\n" +
                "   \"id\": \"6spo4pvm002qg83csflp7cbbta\",\n" +
                "   \"status\": \"confirmed\",\n" +
                "   \"updated\": \"2017-06-21T20:31:12.252Z\",\n" +
                "   \"subject\": \"Anadil\",\n" +
                "   \"creator\": {\n" +
                "    \"email\": \"anadilhussein@gmail.com\",\n" +
                "    \"displayName\": \"anadil hussein\",\n" +
                "    \"self\": true\n" +
                "   },\n" +
                "   \"organizer\": {\n" +
                "    \"email\": \"anadilhussein@gmail.com\",\n" +
                "    \"displayName\": \"anadil hussein\",\n" +
                "    \"self\": true\n" +
                "   },\n" +
                "   \"start\": {\n" +
                "    \"date\": \"2018-10-24\"\n" +
                "   },\n" +
                "   \"end\": {\n" +
                "    \"date\": \"2018-10-24\"\n" +
                "   },\n" +
                "   \"sequence\": 0,\n" +
                "   \"reminders\": {\n" +
                "    \"useDefault\": true\n" +
                "   }\n" +
                "  }";
        try

        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(str);
            Event event = OutlookCalendar.OutlookEvent(root);
            assert (event.getDescription().equals(""));
            assert (event.getName().equals("Anadil"));
            System.out.println(event.getStartTime());
            assert (event.getStartTime().equals("1540332061000"));
            assert (event.getEndTime().equals("1540332061000"));
            System.out.println(event.toString());


            /*****************************************************/
           //  1540328400000
            mapper = new ObjectMapper();
            root = mapper.readTree(str2);
            event = OutlookCalendar.OutlookEvent(root);
            assert (event.getName().equals("Anadil"));
            assert (event.getDescription().equals(""));
            System.out.println(event.getStartTime());
            assert (event.getStartTime().equals("1540328400000"));
            assert (event.getEndTime().equals("1540328400000"));
            System.out.println(event.toString());

        } catch (Exception e)

        {
            System.out.println(e.toString());
        }
    }


    @Test
    public  void getEventsTest(){
        AppUser user=new AppUser();
        user.setId("123456");
        user.setUsername("Anadil@hussein");
        user.setName("Anadil");
        user.setEvents(new ArrayList<Event>());
        user.setAccessToken("ya29.GlvBBdBUYGcDZ3y9TWKK3WcTCUboukfAVSajc3SpUuxvqH3nHNGOxquRND_2amxnuvqlK5te3gCDUm04AKIqJzfxbWXS8kK20h5gbfo0dyDfOjO_48LaC2V57vih");
        user.setRefreshToken("1/3qCL9J5Qr7Ou_PidxdOuNAlN0swZ3nr-5C290pgpfMo");
        try {
            ArrayList<Event> tmp=OutlookCalendar.getEvents(user);
        }
        catch (Exception e){
            assert (true);
        }

    }
}
