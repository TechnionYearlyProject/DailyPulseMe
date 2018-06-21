package backend.entity;

import backend.entity.AppUser;
import backend.entity.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AppUserTest {

    AppUser user = new AppUser();

    @Before
    public void setUp() throws Exception {
        user.setId("DailyPulseUser");
        user.setName("DailyPulseMe@gmail.com");
        user.setUsername("AnadilMuhammedMuhammedNageebRoberPelegYotam");
        user.setEvents(new ArrayList<Event>());

    }

    @Test
    public void getId() throws Exception {
        assertTrue(user.getId().equalsIgnoreCase("DailyPulseUser"));
    }

    @Test
    public void setId() throws Exception {
        user.setId("fake");
        assertTrue(user.getId().equalsIgnoreCase("fake"));
        user.setId("DailyPulseUser");
    }

    @Test
    public void getName() throws Exception {
        assertTrue(user.getName().equalsIgnoreCase("DailyPulseMe@gmail.com"));
    }

    @Test
    public void setName() throws Exception {
        user.setName("fake");
        assertTrue(user.getName().equalsIgnoreCase("fake"));
        user.setName("DailyPulseMe@gmail.com");
    }


    @Test
    public void getUsername() throws Exception {
        assertTrue(user.getUsername().equalsIgnoreCase("AnadilMuhammedMuhammedNageebRoberPelegYotam"));
    }

    @Test
    public void setUsername() throws Exception {

        user.setUsername("fake");
        assertTrue(user.getUsername().equalsIgnoreCase("fake"));
        user.setUsername("AnadilMuhammedMuhammedNageebRoberPelegYotam");
    }
    @Test
    public  void  setPassword(){
        user.setPassword("hhh");
        assertTrue(user.getPassword().equals("hhh"));
    }

    @Test
    public  void test2(){

        ArrayList<Event> events=new ArrayList<Event>();
        Event event=new Event();
        event.setId("1");
        event.setStartTime("1");
        events.add(event);
        user.saveAll(events);
        user.getEvents();
        user.getEvent("1");
        user.deleteEvent("1");
        user.addEvents(events);

    }
    @Test
    public  void test3(){

        ArrayList<Event> events=new ArrayList<Event>();
        Event event=new Event();
        event.setId("1");
        event.setStartTime("1");
        user.saveAll(events);
        user.getEvents();
        user.getEvent("1");
        user.deleteEvent("1");


    }
    @Test
    public void getOutlookTokenTest(){
        user.setOutlookToken("123");
        assertEquals("123",user.getOutlookToken());
    }

}

