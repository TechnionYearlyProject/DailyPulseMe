package backend.Calendar;


import backend.entity.AppUser;
import backend.entity.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

import static backend.Calendar.GoogleCalendar.getEvents;

public class GoogleCalendarTest {
    @Test
    public void GoogleEventTest(){
        String str="  {\n" +
                "   \"kind\": \"calendar#event\",\n" +
                "   \"etag\": \"\\\"2996154144504000\\\"\",\n" +
                "   \"id\": \"6spo4pvm002qg83csflp7cbbta\",\n" +
                "   \"status\": \"confirmed\",\n" +
                "   \"htmlLink\": \"https://www.google.com/calendar/event?eid=NnNwbzRwdm0wMDJxZzgzY3NmbHA3Y2JidGEgYW5hZGlsaHVzc2VpbkBt\",\n" +
                "   \"created\": \"2017-06-21T20:31:12.000Z\",\n" +
                "   \"updated\": \"2017-06-21T20:31:12.252Z\",\n" +
                "   \"summary\": \"Anadil\",\n" +
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
                "   \"iCalUID\": \"6spo4pvm002qg83csflp7cbbta@google.com\",\n" +
                "   \"sequence\": 0,\n" +
                "   \"reminders\": {\n" +
                "    \"useDefault\": true\n" +
                "   }\n" +
                "  }";
        String str2="  {\n" +
                "   \"kind\": \"calendar#event\",\n" +
                "   \"etag\": \"\\\"2996154144504000\\\"\",\n" +
                "   \"id\": \"6spo4pvm002qg83csflp7cbbta\",\n" +
                "   \"status\": \"confirmed\",\n" +
                "   \"htmlLink\": \"https://www.google.com/calendar/event?eid=NnNwbzRwdm0wMDJxZzgzY3NmbHA3Y2JidGEgYW5hZGlsaHVzc2VpbkBt\",\n" +
                "   \"created\": \"2017-06-21T20:31:12.000Z\",\n" +
                "   \"updated\": \"2017-06-21T20:31:12.252Z\",\n" +
                "   \"summary\": \"Anadil\",\n" +
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
                "   \"iCalUID\": \"6spo4pvm002qg83csflp7cbbta@google.com\",\n" +
                "   \"sequence\": 0,\n" +
                "   \"reminders\": {\n" +
                "    \"useDefault\": true\n" +
                "   }\n" +
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(str);
            Event event=GoogleCalendar.GoogleEvent(root);
            assert(event.getName().equals("Anadil"));
            assert (event.getDescription().equals(""));
            System.out.println(event.getStartTime());
            assert (event.getStartTime().equals("1540332061000"));
            assert (event.getEndTime().equals("1540332061000"));
            System.out.println(event.toString());


            /*****************************************************/
           // 1540328400000
            mapper = new ObjectMapper();
            root = mapper.readTree(str2);
            event=GoogleCalendar.GoogleEvent(root);
            assert(event.getName().equals("Anadil"));
            assert (event.getDescription().equals(""));
            System.out.println(event.getStartTime());
            assert (event.getStartTime().equals("1540328400000"));
            assert (event.getEndTime().equals("1540328400000"));
            System.out.println(event.toString());

        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public  void getEventsTest(){
        AppUser user=new AppUser();
        user.setId("123456");
        user.setName("Anadil");
        user.setUsername("Anadil@hussein");
        user.setEvents(new ArrayList<Event>());
        user.setAccessToken("ya29.GlvBBdBUYGcDZ3y9TWKK3WcTCUboukfAVSajc3SpUuxvqH3nHNGOxquRND_2amxnuvqlK5te3gCDUm04AKIqJzfxbWXS8kK20h5gbfo0dyDfOjO_48LaC2V57vih");
        user.setRefreshToken("1/3qCL9J5Qr7Ou_PidxdOuNAlN0swZ3nr-5C290pgpfMo");
        try {
            ArrayList<Event> tmp=GoogleCalendar.getEvents(user);
            for(Event event:tmp){
                System.out.println(event.getName());
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
}
