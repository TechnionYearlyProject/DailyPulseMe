package backend.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubscriptionTest {
    @Test
    public void simpleTest(){
        Subscription tmp = new Subscription("123@123","111");
        assertEquals(tmp.getEmail(),"123@123");
        assertEquals(tmp.getName(),"111");
        tmp.setEmail("123");
        tmp.setName("123");
    }
}
